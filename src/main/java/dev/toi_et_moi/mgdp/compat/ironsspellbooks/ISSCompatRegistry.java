package dev.toi_et_moi.mgdp.compat.ironsspellbooks;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.PotionDefenseModifier;
import net.minecraft.world.item.Item;
import src.toi_et_moi.mgdp.Mgdp;
import src.toi_et_moi.mgdp.init.MGDPModifiers;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class ISSCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<PotionDefenseModifier> TRUE_INVISIBILITY;
    public static final ItemEntry<SimpleUpgradeItem> UP_TRUE_INVISIBILITY;

    static{
        TRUE_INVISIBILITY = reg("true_invisibility", () -> new PotionDefenseModifier(1, src.toi_et_moi.mgdp.modifier.CompatUtil::getTrueInvisibility),
                "True Invisibility",
                "Golem becomes truly invisible (Iron's Spells 'n Spellbooks).");
        UP_TRUE_INVISIBILITY = regModUpgrade("true_invisibility",() -> TRUE_INVISIBILITY,ISSDispatch.MODID).register();
    }
}
