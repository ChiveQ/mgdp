package src.toi_et_moi.mgdp.modifier.farming;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import dev.xkmc.modulargolems.content.modifier.special.PickupModifier;
import dev.xkmc.modulargolems.init.data.MGConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import src.toi_et_moi.mgdp.compat.L2Compat;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.ArrayDeque;

/**
 * 伐木升级（蓝色升级）——从收获作物升级中独立出来的砍树功能
 * <p>
 * 主手持斧时，自动砍伐拾取范围（每级拾取升级 × 6 格）内的树木；需要拾取升级才工作。
 * 会一并清理树叶；砍掉自然树根（下方不是原木的原木）时免费补种对应树苗
 * （模仿收获升级的紫颂花补种），便于持续采伐；双持双斧时不补种。
 * 玩家建造的原木（无树叶、非树结构）受保护不砍；双持双斧可跳过保护判定。
 * 掉落按手持斧计算（自动冶炼附魔可出木炭），原木每块消耗 1 点耐久。
 * 滚动列扫描模式与其他挂机升级一致。
 */
public class LumberjackModifier extends GolemModifier {

	private static final int CYCLE = 100;            // 全周期目标长度：约 5 秒扫完整个范围
	private static final int Y_MIN = -1;
	private static final int Y_MAX = 25;

	/** 名字替换覆盖不到的原木 → 树苗特例 */
	private static final Map<String, String> SAPLING_SPECIAL = Map.of(
			"minecraft:mangrove_log", "minecraft:mangrove_propagule",
			"minecraft:crimson_stem", "minecraft:crimson_fungus",
			"minecraft:warped_stem", "minecraft:warped_fungus");

	private static final Map<UUID, Integer> COLUMN_CURSORS = new WeakHashMap<>();

	public LumberjackModifier() {
		super(StatFilterType.MASS, 1);
	}

	@Override
	public List<MutableComponent> getDetail(int v) {
		int range = MGConfig.COMMON.basePickupRange.get();
		return List.of(Component.translatable(getDescriptionId() + ".desc", range).withStyle(ChatFormatting.GREEN));
	}

	@Override
	public void onAiStep(AbstractGolemEntity<?, ?> golem, int level) {
		if (golem.level().isClientSide()) return;
		if (level <= 0) return;
		// 安全阀：战斗停伐
		if (golem.getTarget() != null) return;
		// 工具门槛：主手必须持斧
		if (!(golem.getMainHandItem().getItem() instanceof AxeItem)) return;
		// 前置：需要拾取升级；作用范围与拾取范围一致（每级拾取 × basePickupRange）
		int pickupLevel = 0;
		for (var entry : golem.getModifiers().entrySet()) {
			if (entry.getKey() instanceof PickupModifier) {
				pickupLevel = entry.getValue();
				break;
			}
		}
		if (pickupLevel <= 0) return;
		if (!(golem.level() instanceof ServerLevel sl)) return;

		int range = pickupLevel * MGConfig.COMMON.basePickupRange.get();
		int side = range * 2 + 1;
		int totalColumns = side * side;
		int columnsPerTick = Math.max(1, (totalColumns + CYCLE - 1) / CYCLE);

		Level levelW = golem.level();
		BlockPos center = golem.blockPosition();

		UUID id = golem.getUUID();
		int cursor = COLUMN_CURSORS.getOrDefault(id, 0);
		for (int i = 0; i < columnsPerTick; i++) {
			scanColumn(golem, sl, levelW, center, range, (cursor + i) % totalColumns);
		}
		COLUMN_CURSORS.put(id, (cursor + columnsPerTick) % totalColumns);
	}

	private void scanColumn(AbstractGolemEntity<?, ?> golem, ServerLevel sl, Level level,
			BlockPos center, int range, int col) {
		int side = range * 2 + 1;
		int dx = col % side - range;
		int dz = col / side - range;
		for (int dy = Y_MIN; dy <= Y_MAX; dy++) {
			BlockPos pos = center.offset(dx, dy, dz);
			if (!level.isLoaded(pos)) continue;
			BlockState state = level.getBlockState(pos);
			if (state.isAir()) continue;
			ItemStack tool = golem.getMainHandItem();
			if (!(tool.getItem() instanceof AxeItem)) return; // 斧子脱手，本列停工
			if (!(state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES))) continue;

			boolean dualWield = golem.getOffhandItem().getItem() instanceof AxeItem;
			if (state.is(BlockTags.LOGS) && !isTreeLog(level, pos, dualWield)) {
				continue; // 玩家建造的非树原木受保护
			}
			if (state.is(BlockTags.LEAVES) && !dualWield) {
				boolean adjProtected = false;
				for (Direction dir : Direction.values()) {
					BlockPos n = pos.relative(dir);
					if (level.getBlockState(n).is(BlockTags.LOGS) && !isTreeLog(level, n, false)) {
						adjProtected = true;
						break;
					}
				}
				if (adjProtected) continue;
			}

			// 掉落 + 自动冶炼（原木→木炭），原木每块消耗 1 点耐久
			List<ItemStack> drops = Block.getDrops(state, sl, pos, level.getBlockEntity(pos), golem, tool);
			L2Compat.tryAutoSmelt(sl, tool, drops);
			for (ItemStack drop : drops) {
				Block.popResource(level, pos, drop);
			}
			level.levelEvent(2001, pos, Block.getId(state));
			level.removeBlock(pos, false);
			if (state.is(BlockTags.LOGS)) {
				// 补种：砍掉自然树根（下方不是原木）时，在原位免费补一棵对应树苗；
				// 双持双斧视为暴力采伐，不补种（同收获升级的紫颂花补种逻辑）
				if (!dualWield && !level.getBlockState(pos.below()).is(BlockTags.LOGS)) {
					BlockState sapling = saplingFor(state);
					if (sapling != null) {
						level.setBlock(pos, sapling, Block.UPDATE_CLIENTS);
					}
				}
				tool.hurtAndBreak(1, golem, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
			}
		}
	}

	/**
	 * 原木 → 对应树苗方块：特例表优先（红树、菌柄），其次按 xxx_log → xxx_sapling 名字替换，
	 * 找不到对应树苗（如模组方块命名不一致）则返回 null，不补种。
	 */
	@Nullable
	private static BlockState saplingFor(BlockState log) {
		ResourceLocation id = ForgeRegistries.BLOCKS.getKey(log.getBlock());
		if (id == null) return null;
		Block block = null;
		String special = SAPLING_SPECIAL.get(id.toString());
		if (special != null) {
			block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(special));
		} else if (id.getPath().endsWith("_log")) {
			String path = id.getPath().substring(0, id.getPath().length() - 4) + "_sapling";
			block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id.getNamespace(), path));
		}
		return block == null || block == Blocks.AIR ? null : block.defaultBlockState();
	}

	/**
	 * 树结构判定（与收获作物原逻辑一致）：连接的原木 BFS，需含树叶且横向扩展不超过 10 格。
	 */
	private static boolean isTreeLog(Level level, BlockPos pos, boolean dualWield) {
		if (dualWield) return true; // 双持双斧跳过所有保护判定
		Set<BlockPos> visited = new HashSet<>();
		Queue<BlockPos> queue = new ArrayDeque<>();
		queue.add(pos);
		visited.add(pos);
		int horizontalCount = 0;
		boolean hasLeaves = false;
		while (!queue.isEmpty()) {
			BlockPos current = queue.poll();
			for (Direction dir : Direction.values()) {
				BlockPos neighbor = current.relative(dir);
				if (visited.contains(neighbor)) continue;
				if (!level.isLoaded(neighbor)) continue;
				BlockState state = level.getBlockState(neighbor);
				if (state.is(BlockTags.LEAVES)) hasLeaves = true;
				if (state.is(BlockTags.LOGS) && visited.size() < 64) {
					visited.add(neighbor);
					queue.add(neighbor);
					if (dir.getAxis() != Direction.Axis.Y) horizontalCount++;
				}
			}
		}
		return hasLeaves && horizontalCount <= 10;
	}
}
