package src.toi_et_moi.mgdp.modifier.defense;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;

public class UnbreakableModifier extends GolemModifier {

	public UnbreakableModifier() {
		super(StatFilterType.HEALTH, 1);
	}

}
