package dev.toi_et_moi.mgdp.compat.l2complements;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;

public class LCSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> SCULKIUM_TEMPLATE;

    static{
        SCULKIUM_TEMPLATE = MGDPItems.regAddSlotTemplate("sculkium_expansion_template",
                () -> dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.SCULKIUM_ADD).register();
    }

}
