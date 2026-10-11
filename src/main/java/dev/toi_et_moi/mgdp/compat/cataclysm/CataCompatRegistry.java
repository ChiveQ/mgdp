package dev.toi_et_moi.mgdp.compat.cataclysm;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GDCompatRegistry;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GolemSummonItem;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;

public class CataCompatRegistry {

    public static void register(){}

    public static final ItemEntry<SimpleUpgradeItem> HARBINGER_BEAM,HARBINGER_MISSILE,IGNIS_ATTACK,IGNIS_FIREBALL,IGNIS_JUMP;

    static{
        // 升级
        HARBINGER_BEAM = regModUpgrade("harbinger_beam",
                () -> dev.xkmc.modulargolems.compat.materials.cataclysm.CataCompatRegistry.HARBINGER_BEAM,
                1,false,Rarity.EPIC,CataDispatch.MODID).register();
        HARBINGER_MISSILE = regModUpgrade("harbinger_missile",
                () -> dev.xkmc.modulargolems.compat.materials.cataclysm.CataCompatRegistry.HARBINGER_MISSILE,
                1,false, Rarity.EPIC,CataDispatch.MODID).register();
        IGNIS_ATTACK= regModUpgrade("ignis_attack",
                () -> dev.xkmc.modulargolems.compat.materials.cataclysm.CataCompatRegistry.IGNIS_ATTACK,
                1,false, Rarity.EPIC,CataDispatch.MODID).register();
        IGNIS_FIREBALL = regModUpgrade("ignis_fireball",
                () -> dev.xkmc.modulargolems.compat.materials.cataclysm.CataCompatRegistry.IGNIS_FIREBALL,
                1,false, Rarity.EPIC,CataDispatch.MODID).register();
        IGNIS_JUMP = regModUpgrade("ignis_jump",
                () -> dev.xkmc.modulargolems.compat.materials.cataclysm.CataCompatRegistry.IGNIS_JUMP,
                1,false, Rarity.EPIC,CataDispatch.MODID).register();
    }

}
