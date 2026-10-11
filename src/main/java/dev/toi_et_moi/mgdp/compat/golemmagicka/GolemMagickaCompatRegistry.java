package dev.toi_et_moi.mgdp.compat.golemmagicka;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.golemmagicka.init.reg.GMTypes;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import net.minecraft.world.item.Item;
import src.toi_et_moi.mgdp.Mgdp;
import src.toi_et_moi.mgdp.init.MGDPModifiers;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.xkmc.modulargolems.init.registrate.GolemModifiers.reg;

public class GolemMagickaCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<AttributeGolemModifier> MANA_OVERLOAD;
    public static final RegistryEntry<SimpleUpgradeItem> UP_MANA_OVERLOAD;

    static {
        MANA_OVERLOAD = reg("mana_overload", () -> new AttributeGolemModifier(1,
                        new AttributeGolemModifier.AttrEntry(GMTypes.STAT_MANA_REGEN, () -> 9.99)),
                "Mana Overload",
                "Massively boosts mana regen rate. Level 1 = 50x, Level 2 = 100x.");
        UP_MANA_OVERLOAD = regModUpgrade("mana_overload",() -> MANA_OVERLOAD,GolemMagickaDispatch.MODID).register();
    }



}
