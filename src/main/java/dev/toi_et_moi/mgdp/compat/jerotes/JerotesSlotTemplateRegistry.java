package dev.toi_et_moi.mgdp.compat.jerotes;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;

public class JerotesSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> MEROR_TEMPLATE,REFINE_MEROR_TEMPLATE;

    static{
        // 模板
        MEROR_TEMPLATE = MGDPItems.regAddSlotTemplate("meror_expansion_template",
                () -> MGDPModifiers.MEROR_ADD).register();
        REFINE_MEROR_TEMPLATE = MGDPItems.regAddSlotTemplate("refine_meror_expansion_template",
                () -> MGDPModifiers.REFINE_MEROR_ADD).register();
    }

}
