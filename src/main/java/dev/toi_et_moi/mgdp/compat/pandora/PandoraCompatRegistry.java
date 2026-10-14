package dev.toi_et_moi.mgdp.compat.pandora;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;


public class PandoraCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<RealitySuppressionModifier> REALITY_SUPPRESSION;
    public static final RegistryEntry<SimpleUpgradeItem> UPGRADE_REALITY_SUPPRESION;

    static {
        REALITY_SUPPRESSION = reg("reality_suppression", RealitySuppressionModifier::new,
                "Reality Suppression",
                "Each level provides 1 point of Reality Index. Max level 7.");
        UPGRADE_REALITY_SUPPRESION = regModUpgrade("reality_suppression",() -> REALITY_SUPPRESSION,PandoraDispatch.MODID).register();
//                () -> new SimpleUpgradeItem(new Item.Properties().rarity(net.minecraft.world.item.Rarity.EPIC), () -> MGDPModifiers.REALITY_SUPPRESSION.get(), 1, false));
    }

}
