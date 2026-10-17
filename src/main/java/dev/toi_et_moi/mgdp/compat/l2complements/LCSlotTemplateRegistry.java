package dev.toi_et_moi.mgdp.compat.l2complements;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import dev.toi_et_moi.mgdp.content.modifier.base.LeveledAddSlotModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class LCSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> SCULKIUM_TEMPLATE;
    public static final RegistryEntry<AddSlotModifier> SCULKIUM_ADD;

    static{
        SCULKIUM_ADD = reg("add_slot_sculkium", () -> new LeveledAddSlotModifier(1, 1),
                "Sculkium Expansion",
                "Add 1 upgrade slots.");
        SCULKIUM_TEMPLATE = MGDPItems.regAddSlotTemplate("sculkium_expansion_template",
                () -> SCULKIUM_ADD).register();
    }

}
