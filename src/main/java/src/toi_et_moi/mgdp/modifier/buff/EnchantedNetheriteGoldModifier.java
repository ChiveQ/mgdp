package src.toi_et_moi.mgdp.modifier.buff;

import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.entity.common.GolemFlags;
import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import dev.xkmc.modulargolems.init.data.MGConfig;
import dev.xkmc.modulargolems.init.registrate.GolemTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class EnchantedNetheriteGoldModifier extends AttributeGolemModifier {

    private static final AttrEntry REGEN_ENTRY = new AttrEntry(GolemTypes.STAT_REGEN, () -> 5.0);

    public EnchantedNetheriteGoldModifier() {
        super(1, REGEN_ENTRY);
    }

    @Override
    public void onRegisterFlag(Consumer<GolemFlags> addFlag) {
        addFlag.accept(GolemFlags.FIRE_IMMUNE);
    }

    @Override
    public void onHurt(@NotNull AbstractGolemEntity<?, ?> golem, LivingHurtEvent event, int level) {
        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            event.setAmount(event.getAmount() * 0.8F);
            if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                event.setAmount(event.getAmount() * 0.6F);
            }
        }
    }

}
