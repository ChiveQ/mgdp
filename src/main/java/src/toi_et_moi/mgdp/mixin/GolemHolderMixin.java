package src.toi_et_moi.mgdp.mixin;

import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.modulargolems.content.item.golem.GolemHolder;
import dev.xkmc.modulargolems.content.item.upgrade.IUpgradeItem;
import dev.xkmc.modulargolems.content.item.upgrade.UpgradeItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(GolemHolder.class)
public class GolemHolderMixin {

	@Unique
	private static final TagKey<Item> FREE_TAG = ItemTags.create(MGDP.loc( "free_upgrades"));

	@Inject(method = "getRemaining", at = @At("RETURN"), remap = false, cancellable = true)
	private void mgdp$freeUpgrades(ArrayList mats, ArrayList<IUpgradeItem> upgrades, CallbackInfoReturnable<Integer> cir) {
		int val = cir.getReturnValue();
		for (var up : upgrades) {
			if (up instanceof UpgradeItem item && item.builtInRegistryHolder().is(FREE_TAG)) {
				val++;
			}
		}
		cir.setReturnValue(val);
	}
}
