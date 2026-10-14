package dev.toi_et_moi.mgdp.compat.goety;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import src.toi_et_moi.mgdp.modifier.MGDPAddSlotModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class GoetySlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> DARK_TEMPLATE;
    public static final RegistryEntry<AddSlotModifier> DARK_ADD;

    static{
        DARK_ADD = reg("add_slot_dark", () -> new MGDPAddSlotModifier(1, 1),
                "Dark Expansion",
                "Add 1 upgrade slots.");
        // 模板
        DARK_TEMPLATE = MGDPItems.regAddSlotTemplate("dark_expansion_template", () -> DARK_ADD).register();
    }

}
