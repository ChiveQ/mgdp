package dev.toi_et_moi.mgdp.compat.ironsspellbooks;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;

public class ISSSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> PYRIUM_TEMPLATE;

    static{
        PYRIUM_TEMPLATE = MGDPItems.regAddSlotTemplate("pyrium_expansion_template",
                () -> dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.PYRIUM_ADD).register();
    }

}
