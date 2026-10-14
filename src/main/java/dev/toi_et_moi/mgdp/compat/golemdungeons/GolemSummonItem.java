package dev.toi_et_moi.mgdp.compat.golemdungeons;

import dev.xkmc.golemdungeons.init.GolemDungeons;
import dev.xkmc.golemdungeons.init.reg.GDItems;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import src.toi_et_moi.mgdp.MGDPConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO 未深察、但去反射耳
public class GolemSummonItem extends Item {

    private final ResourceLocation spawnConfigId;

    public GolemSummonItem(Properties props, ResourceLocation spawnConfigId) {
        super(props);
        this.spawnConfigId = spawnConfigId;
    }

    /** 铁砧修复材料：golemdungeons 的试炼勋章 */
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(GDItems.TRIAL_MEDAL.asItem());
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var player = ctx.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (!(ctx.getLevel() instanceof ServerLevel sl)) return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown()) {
            return summonFormation(sl, player, ctx);
        }

        LivingEntity golem = summonFactionGolem(spawnConfigId, sl, player.getUUID());
        if (golem == null) return InteractionResult.PASS;

        golem.setPos(ctx.getClickLocation());
        recursiveAdd(sl, golem);

        if (!player.getAbilities().instabuild) {
            ctx.getItemInHand().hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(ctx.getHand()));
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * 潜行使用：一次消耗全部剩余耐久，召唤对应数量的无主傀儡，
     * 并以点击位置为中心按方形网格平铺在周围地面上（摆阵形用）。
     */
    private InteractionResult summonFormation(ServerLevel level, Player player, UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        int count = stack.isDamageableItem() ? stack.getMaxDamage() - stack.getDamageValue() : 1;
        if (count <= 0) return InteractionResult.PASS;

        List<LivingEntity> golems = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            LivingEntity golem = summonFactionGolem(spawnConfigId, level, null);
            if (golem != null) golems.add(golem);
        }
        if (golems.isEmpty()) return InteractionResult.PASS;

        int step = MGDPConfig.summonSpacing;
        BlockPos center = BlockPos.containing(ctx.getClickLocation());
        List<BlockPos> offsets = formationOffsets(count, step);
        for (int i = 0; i < golems.size(); i++) {
            BlockPos off = offsets.get(i);
            int x = center.getX() + off.getX();
            int z = center.getZ() + off.getZ();
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            LivingEntity golem = golems.get(i);
            golem.setPos(x + 0.5, y, z + 0.5);
            recursiveAdd(level, golem);
        }

        if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak(stack.getMaxDamage() - stack.getDamageValue(), player, (e) -> e.broadcastBreakEvent(ctx.getHand()));
        }
        return InteractionResult.SUCCESS;
    }

    /** 从中心向外逐圈铺开的方形网格偏移，首项为中心 (0,0)。 */
    private static List<BlockPos> formationOffsets(int count, int step) {
        List<BlockPos> list = new ArrayList<>(count);
        list.add(BlockPos.ZERO);
        for (int r = 1; list.size() < count; r++) {
            int d = r * step;
            for (int i = -r; i <= r && list.size() < count; i++) list.add(new BlockPos(i * step, 0, -d));
            for (int j = -r + 1; j <= r && list.size() < count; j++) list.add(new BlockPos(d, 0, j * step));
            for (int i = r - 1; i >= -r && list.size() < count; i--) list.add(new BlockPos(i * step, 0, d));
            for (int j = r - 1; j > -r && list.size() < count; j--) list.add(new BlockPos(-d, 0, j * step));
        }
        return list;
    }

    private static void recursiveAdd(ServerLevel level, Entity e) {
        level.addFreshEntity(e);
        for (var x : e.getPassengers()) {
            x.setPos(e.position());
            recursiveAdd(level, x);
        }
    }

    /**
     * Summon a golem from a GolemDungeons faction SpawnConfig.
     * <p>SpawnConfig 创建傀儡时会把地牢阵营的 UUID 传成 owner（因此生成即敌对），
     * 所以 owner 传 null 时必须显式清空，傀儡才是真正无主且不敌对。</p>
     *
     * @param configId the SpawnConfig ResourceLocation
     * @param level    the server level
     * @param owner    owner UUID to set; null clears the faction owner (ownerless, non-hostile)
     * @return the summoned golem, or null if failed
     */
    @Nullable
    public static LivingEntity summonFactionGolem(ResourceLocation configId, ServerLevel level, @Nullable UUID owner) {
        try {
            LivingEntity summoned = GolemDungeons.SPAWN.getEntry(configId).summon(level);
            // If the returned entity is a hostile non-golem mount (e.g. Ravager),
            // discard it and return the golem rider instead. Passive mounts (Horse, etc.) are kept.
            if (summoned instanceof Enemy && !summoned.getPassengers().isEmpty()) {
                var firstRider = summoned.getPassengers().get(0);
                if (firstRider instanceof AbstractGolemEntity<?, ?> age) {
                    age.setOwnerUUID(owner);
                    firstRider.stopRiding();
                    summoned.discard();
                    return age;
                }
            }
            // Normal case: returned entity is the golem itself or a golem mount
            setOwnerRecursive(summoned, owner);
            return summoned;
        } catch (Exception e) {
            return null;
        }
    }

    private static void setOwnerRecursive(Entity entity, java.util.UUID owner) {
        if (entity instanceof AbstractGolemEntity<?, ?> age) {
            age.setOwnerUUID(owner);
        }
        for (var passenger : entity.getPassengers()) {
            setOwnerRecursive(passenger, owner);
        }
    }
}
