package dev.toi_et_moi.mgdp.content.modifier.combat;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class RiptideModifier extends GolemModifier {

    public RiptideModifier() {
        super(StatFilterType.MASS, 1);
    }

    @Override
    public void onAiStep(@NotNull AbstractGolemEntity<?, ?> golem, int level) {
        if (golem.tickCount % 30 != 0) return;
        ItemStack weapon = golem.getMainHandItem();
        int riptideLevel = weapon.getEnchantmentLevel(Enchantments.RIPTIDE);
        LivingEntity target = golem.getTarget();
        if (target == null) return;
        Vec3 dir = target.position().add(0, target.getBbHeight() * 0.5, 0)
                .subtract(golem.position()).normalize();
        double speed = 1.5 + 0.5 * (riptideLevel > 0 ? riptideLevel : 1);
        golem.setDeltaMovement(dir.x * speed, dir.y * speed + 0.5, dir.z * speed);
        golem.hasImpulse = true;
    }
}
