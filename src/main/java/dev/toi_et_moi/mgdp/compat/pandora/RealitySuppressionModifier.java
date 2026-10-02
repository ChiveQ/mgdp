package dev.toi_et_moi.mgdp.compat.pandora;

import dev.xkmc.curseofpandora.init.registrate.CoPAttrs;
import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import dev.xkmc.modulargolems.init.registrate.GolemTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import src.toi_et_moi.mgdp.Mgdp;

public class RealitySuppressionModifier extends GolemModifier {

	public RealitySuppressionModifier() {
		super(StatFilterType.HEALTH, 7);
	}

	@Override
	public void onAiStep(AbstractGolemEntity<?, ?> golem, int level) {
		if (golem.level().isClientSide()) return;
		var ins = golem.getAttribute(CoPAttrs.REALITY.get());
		if (ins == null) return;
		ins.setBaseValue(level);
	}

	@Mod.EventBusSubscriber(modid = Mgdp.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class AttrHandler {

		@SuppressWarnings("unchecked")
		@SubscribeEvent
		public static void onAttributeModification(EntityAttributeModificationEvent event) {
			for (var entry : new com.tterrag.registrate.util.entry.EntityEntry<?>[]{
				GolemTypes.ENTITY_HUMANOID, GolemTypes.ENTITY_DOG, GolemTypes.ENTITY_GOLEM}) {
				var livingType = (EntityType<? extends LivingEntity>) entry.get();
				if (!event.has(livingType, CoPAttrs.REALITY.get())) {
					event.add(livingType, CoPAttrs.REALITY.get());
				}
			}
		}
	}
}
