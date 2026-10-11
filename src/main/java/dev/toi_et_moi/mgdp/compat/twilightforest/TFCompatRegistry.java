package dev.toi_et_moi.mgdp.compat.twilightforest;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.init.registrate.GolemModifiers;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class TFCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<PenguinModifier> PENGUIN;
    public static final RegistryEntry<LastLineModifier> LAST_LINE;

    public static final ItemEntry<SimpleUpgradeItem> UP_IRONWOOD,UP_STEELEAF,UP_FIERY,UP_KNIGHTMETAL,UP_CARMINITE,UP_PENGUIN,UP_LAST_LINE;

    static{
        PENGUIN = reg("penguin", PenguinModifier::new,
                "Penguin",
                "Golem spawns a penguin at the target death location. Penguin! Penguin!");
        LAST_LINE = reg("last_line", LastLineModifier::new,
                "Last Line of Defence",
                "In the Final Plateau, reduces damage from non-golem, non-Twilight-Forest entities by 99%%.");
        UP_IRONWOOD = regModUpgrade("ironwood",() ->
                dev.xkmc.modulargolems.compat.materials.twilightforest.TFCompatRegistry.TF_HEALING,
                5,true,TFDispatch.MODID).register();
        UP_STEELEAF = regModUpgrade("steeleaf",() ->
                        dev.xkmc.modulargolems.compat.materials.twilightforest.TFCompatRegistry.TF_DAMAGE,
                5,true,TFDispatch.MODID).register();
        UP_FIERY = regModUpgrade("fiery",() ->
                        dev.xkmc.modulargolems.compat.materials.twilightforest.TFCompatRegistry.FIERY,
                5,true,TFDispatch.MODID).register();
        UP_KNIGHTMETAL = regModUpgrade("knightmetal",() -> GolemModifiers.THORN, 5,true,TFDispatch.MODID).register();
        UP_CARMINITE = regModUpgrade("carminite",() ->
                        dev.xkmc.modulargolems.compat.materials.twilightforest.TFCompatRegistry.CARMINITE,
                5,true,TFDispatch.MODID).register();
        UP_PENGUIN = regModUpgrade("penguin",() -> PENGUIN,TFDispatch.MODID).register();
        UP_LAST_LINE = regModUpgrade("last_line", () -> LAST_LINE, TFDispatch.MODID).register();
    }

}
