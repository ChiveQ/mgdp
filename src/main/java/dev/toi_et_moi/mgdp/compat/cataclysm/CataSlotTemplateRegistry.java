package dev.toi_et_moi.mgdp.compat.cataclysm;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;

public class CataSlotTemplateRegistry {

    public static void register(){}

    public static final ItemEntry<AddSlotTemplate> CATACLYSMFARMER_TEMPLATE;

    static{
        // 扩充模板
        CATACLYSMFARMER_TEMPLATE = MGDPItems.regAddSlotTemplate("cataclysmfarer_expansion_template",
                () -> dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.CATACLYSMFARMER_ADD).register();
    }

}
