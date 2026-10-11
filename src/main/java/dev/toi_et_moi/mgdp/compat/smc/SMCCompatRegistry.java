package dev.toi_et_moi.mgdp.compat.smc;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import net.minecraft.world.item.Rarity;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class SMCCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<FrostBurstModifier> FROST_BURST;
    public static final ItemEntry<SimpleUpgradeItem> UP_FROST_BURST;

    static{
        FROST_BURST = reg("frost_burst", FrostBurstModifier::new,
                "Frost Burst",
                "Stack frost on attack. Burst deals AoE damage after 10s.");
        UP_FROST_BURST = regModUpgrade("frost_burst",() -> FROST_BURST, Rarity.RARE,SMCDispatch.MODID).register();
    }

}
