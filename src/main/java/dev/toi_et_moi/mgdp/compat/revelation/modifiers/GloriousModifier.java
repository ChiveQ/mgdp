package dev.toi_et_moi.mgdp.compat.revelation.modifiers;

import dev.toi_et_moi.mgdp.compat.goety.GoetyDispatch;
import dev.toi_et_moi.mgdp.compat.revelation.RevelationCompatRegistry;
import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class GloriousModifier extends GolemModifier {

	private static final int DURATION = 200;

	public GloriousModifier() {
		super(StatFilterType.HEALTH, 1);
	}

	@Override
	public void onAiStep(AbstractGolemEntity<?, ?> golem, int level) {

		if (!net.minecraftforge.fml.ModList.get().isLoaded("goety")) return;
		if (golem.tickCount % 20 != 0) return;

		// 舍己为人 II (IV with 万众归一): damage taken heals allies in 60 blocks by the same amount
		var altruistic = ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.fromNamespaceAndPath(GoetyDispatch.MODID, "altruistic"));
		if (altruistic != null) {
			boolean all = golem.getModifiers().containsKey(RevelationCompatRegistry.THE_GENESIS.get());
			golem.addEffect(new MobEffectInstance(altruistic, DURATION, all ? 3 : 1));
		}
	}

	@Override
	public void onHurtTarget(AbstractGolemEntity<?, ?> golem, LivingHurtEvent event, int level) {
		if (golem.level().isClientSide()) return;
		var target = event.getEntity();
		if (!golem.canAttack(target)) return;
		if (target == golem || target == golem.getOwner()) return;

		// +300% damage vs bosses
		if (target.getType().is(Tags.EntityTypes.BOSSES)) {
			event.setAmount(event.getAmount() * 4.0F);
		}
	}
}
