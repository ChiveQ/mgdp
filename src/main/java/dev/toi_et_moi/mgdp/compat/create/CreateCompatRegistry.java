package dev.toi_et_moi.mgdp.compat.create;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import net.minecraft.world.item.Item;
import src.toi_et_moi.mgdp.Mgdp;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;

public class CreateCompatRegistry {

    public static void register(){}

    public static final ItemEntry<SimpleUpgradeItem> COATING,MECHANICAL_ENGINE,MECHANICAL_FORCE,MECHANICAL_MOBILITY;

    static{
        COATING = regModUpgrade("coating", () -> dev.xkmc.modulargolems.compat.materials.create.CreateCompatRegistry.COATING,
                5,true,CreateDispatch.MODID).register();
        MECHANICAL_ENGINE = regModUpgrade("mechanical_engine",
                () -> dev.xkmc.modulargolems.compat.materials.create.CreateCompatRegistry.BODY, CreateDispatch.MODID).register();
        MECHANICAL_FORCE = regModUpgrade("mechanical_force",
                () -> dev.xkmc.modulargolems.compat.materials.create.CreateCompatRegistry.FORCE, CreateDispatch.MODID).register();
        MECHANICAL_MOBILITY = regModUpgrade("mechanical_mobility",
                () -> dev.xkmc.modulargolems.compat.materials.create.CreateCompatRegistry.MOBILE, CreateDispatch.MODID).register();
    }

}
