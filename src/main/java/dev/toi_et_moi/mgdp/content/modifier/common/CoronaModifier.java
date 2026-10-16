package dev.toi_et_moi.mgdp.content.modifier.common;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CoronaModifier extends GolemModifier {

	public CoronaModifier() {
		super(StatFilterType.HEALTH, 1);
	}

	/**
	 * 已經CoronaListener避免附著新負面效果、此処儘用於入晝旹去舊效果
	 * */
	@Override
	public void onAiStep(AbstractGolemEntity<?, ?> golem, int level) {
		if (!golem.level().isDay()) return;
		if (golem.tickCount % 60 != 0) return;
		var effects = new ArrayList<>(golem.getActiveEffects());
		effects.forEach(effect -> {
			if (effect.getEffect().getCategory() == MobEffectCategory.HARMFUL) golem.removeEffect(effect.getEffect());
		});
	}

	@Override
	public boolean isImmuneTo(@NotNull AbstractGolemEntity<?, ?> golem, @NotNull MobEffectInstance ins, int level) {
		return golem.level().isDay() && ins.getEffect().getCategory() == MobEffectCategory.HARMFUL;
	}
}
