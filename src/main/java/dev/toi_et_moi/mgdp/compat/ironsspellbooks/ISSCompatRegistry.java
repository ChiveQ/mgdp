package dev.toi_et_moi.mgdp.compat.ironsspellbooks;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.PotionDefenseModifier;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class ISSCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<PotionDefenseModifier> TRUE_INVISIBILITY;
    public static final ItemEntry<SimpleUpgradeItem> UP_TRUE_INVISIBILITY;

    static{
        TRUE_INVISIBILITY = reg("true_invisibility", () -> new PotionDefenseModifier(1, MobEffectRegistry.TRUE_INVISIBILITY),
                "True Invisibility",
                "Golem becomes truly invisible (Iron's Spells 'n Spellbooks).");
        UP_TRUE_INVISIBILITY = regModUpgrade("true_invisibility",() -> TRUE_INVISIBILITY,ISSDispatch.MODID).register();
    }
}
