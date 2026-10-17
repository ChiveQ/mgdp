package dev.toi_et_moi.mgdp.compat.jerotes;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.toi_et_moi.mgdp.content.modifier.base.LeveledAddSlotModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class JerotesSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> MEROR_TEMPLATE,REFINE_MEROR_TEMPLATE;
    public static final RegistryEntry<LeveledAddSlotModifier> MEROR_ADD, REFINE_MEROR_ADD;

    static{
        MEROR_ADD = reg("add_slot_meror", () -> new LeveledAddSlotModifier(1, 1),
                "Meror Expansion",
                "Add 1 upgrade slot.");

        REFINE_MEROR_ADD = reg("add_slot_refine_meror", () -> new LeveledAddSlotModifier(1, 2),
                "Refined Meror Expansion",
                "Add 1 upgrade slot.");
        // 模板
        MEROR_TEMPLATE = MGDPItems.regAddSlotTemplate("meror_expansion_template",
                () -> MEROR_ADD).register();
        REFINE_MEROR_TEMPLATE = MGDPItems.regAddSlotTemplate("refine_meror_expansion_template",
                () -> REFINE_MEROR_ADD).register();
    }

}
