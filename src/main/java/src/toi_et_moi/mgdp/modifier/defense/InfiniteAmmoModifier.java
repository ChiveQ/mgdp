package src.toi_et_moi.mgdp.modifier.defense;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class InfiniteAmmoModifier extends GolemModifier {

	public InfiniteAmmoModifier() {
		super(StatFilterType.MASS, 1);
	}

	@Override
	public void onAiStep(AbstractGolemEntity<?, ?> golem, int level) {

		if (!ModList.get().isLoaded("tacz")) return;
		// Once per second is enough
		if (golem.tickCount % 20 != 0) return;

		ItemStack stack = golem.getMainHandItem();
		if (stack.isEmpty()) return;
		if (!stack.getItem().getClass().getName().startsWith("com.tacz.guns")) return;

		var tag = stack.getOrCreateTag();
		tag.putInt("GunCurrentAmmoCount", 9999);
		tag.putInt("DummyAmmo", 9999);
	}

}
