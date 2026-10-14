package dev.toi_et_moi.mgdp.compat.ironsspellbooks;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import src.toi_et_moi.mgdp.modifier.MGDPAddSlotModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class ISSSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> PYRIUM_TEMPLATE;
    public static final RegistryEntry<AddSlotModifier> PYRIUM_ADD;

    static{
        PYRIUM_ADD = reg("add_slot_pyrium", () -> new MGDPAddSlotModifier(1, 2),
                "Pyrium Expansion",
                "Add 1 upgrade slots.");
        PYRIUM_TEMPLATE = MGDPItems.regAddSlotTemplate("pyrium_expansion_template", () -> PYRIUM_ADD).register();
    }

}
