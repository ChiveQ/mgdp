package dev.toi_et_moi.mgdp.compat.cataclysm;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import dev.toi_et_moi.mgdp.content.modifier.base.LeveledAddSlotModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class CataSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> CATACLYSMFARMER_TEMPLATE;
    public static final RegistryEntry<AddSlotModifier> CATACLYSMFARMER_ADD;

    static{
        CATACLYSMFARMER_ADD = reg("add_slot_cataclysmfarer", () -> new LeveledAddSlotModifier(1, 4),
                "Cataclysmfarer Expansion",
                "Add 3 upgrade slots.");
        // 扩充模板
        CATACLYSMFARMER_TEMPLATE = MGDPItems.regAddSlotTemplate("cataclysmfarer_expansion_template",
                () -> CATACLYSMFARMER_ADD).register();
    }

}
