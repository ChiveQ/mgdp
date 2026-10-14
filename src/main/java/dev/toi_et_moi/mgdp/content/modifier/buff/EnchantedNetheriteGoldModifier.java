package dev.toi_et_moi.mgdp.content.modifier.buff;

import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

public class EnchantedNetheriteGoldModifier extends NetheriteGoldModifier {

    @Override
    public void onHurt(@NotNull AbstractGolemEntity<?, ?> golem, LivingHurtEvent event, int level) {
        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                event.setAmount(event.getAmount() * 0.6F);
            }else {
                event.setAmount(event.getAmount() * 0.8F);
            }
        }
    }

}
