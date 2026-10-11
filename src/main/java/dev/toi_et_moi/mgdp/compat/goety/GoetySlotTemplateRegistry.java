package dev.toi_et_moi.mgdp.compat.goety;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;

public class GoetySlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> DARK_TEMPLATE;

    static{
        // 模板
        DARK_TEMPLATE = MGDPItems.regAddSlotTemplate("dark_expansion_template", () ->
                dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.DARK_ADD).register();
    }

}
