package src.toi_et_moi.mgdp.mixin;

import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.xkmc.mob_weapon_api.example.behavior.ThrowableBehavior;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowableBehavior.class)
public abstract class TridentHoldMixin {

	@Inject(method = "holdTime", at = @At("HEAD"), cancellable = true, remap = false)
	private void mgdp$instantTrident(LivingEntity user, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (user instanceof AbstractGolemEntity<?, ?> golem) {
			if (golem.getModifiers().containsKey(MGDPModifiers.QUICK_STRIKE.get())) {
				cir.setReturnValue(0);
			}
		}
	}
}
