package src.toi_et_moi.mgdp.modifier.common;

import dev.toi_et_moi.mgdp.init.MGDP;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Deprecated
@Mod.EventBusSubscriber(modid = MGDP.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RiptideHandler {

	@SubscribeEvent
	public static void onGolemTick(LivingEvent.LivingTickEvent event) {
		if (!(event.getEntity() instanceof AbstractGolemEntity<?, ?> golem)) return;
		if (golem.level().isClientSide) return;
		if (golem.getModifiers().containsKey(MGDPModifiers.RIPTIDE.get())) return; // 已有之于升级

		ItemStack weapon = golem.getMainHandItem();
		int riptideLevel = weapon.getEnchantmentLevel(Enchantments.RIPTIDE);
		// Natural riptide trident works in water/rain unconditionally
//		if (riptideLevel <= 0) {
//			// No enchantment — Riptide upgrade works anywhere
////			if (!golem.getModifiers().containsKey(MGDPModifiers.RIPTIDE.get())) return;
////			riptideLevel = 1;
//			return;
//		} else if (!golem.isInWaterOrRain()) {
//			// Has enchantment but not in water — only with upgrade
//			if (!golem.getModifiers().containsKey(MGDPModifiers.RIPTIDE.get())) return;
//		}
		if(riptideLevel <= 0 || !golem.isInWaterOrRain()) return;

		LivingEntity target = golem.getTarget();
		if (target == null) return;
		if (golem.tickCount % 30 != 0) return;

		Vec3 dir = target.position().add(0, target.getBbHeight() * 0.5, 0)
				.subtract(golem.position()).normalize();
		double speed = 1.5 + 0.5 * riptideLevel;
		golem.setDeltaMovement(dir.x * speed, dir.y * speed + 0.5, dir.z * speed);
		golem.hasImpulse = true;

	}
}
