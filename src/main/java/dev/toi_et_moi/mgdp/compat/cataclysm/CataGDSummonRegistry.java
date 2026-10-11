package dev.toi_et_moi.mgdp.compat.cataclysm;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GDCompatRegistry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GolemSummonItem;
import net.minecraft.resources.ResourceLocation;

public class CataGDSummonRegistry {

    public static void register(){}

    public static final ItemEntry<GolemSummonItem> HARBINGER_GOLEM,MONSTROSITY_GOLEM,ENDER_GUARDIAN_GOLEM,IGNIS_GOLEM,SCYLLA_GOLEM;

    static{
        // 召唤器
        HARBINGER_GOLEM = GDCompatRegistry.regGolemSummonItem("harbinger_golem",
                ResourceLocation.fromNamespaceAndPath(CataDispatch.MODID,"harbingers_revenge"),8).register();
        MONSTROSITY_GOLEM = GDCompatRegistry.regGolemSummonItem("monstrosity_golem",
                ResourceLocation.fromNamespaceAndPath(CataDispatch.MODID,"monstrosity_expanded"),8).register();
        ENDER_GUARDIAN_GOLEM = GDCompatRegistry.regGolemSummonItem("ender_guardian_golem",
                ResourceLocation.fromNamespaceAndPath(CataDispatch.MODID,"meknight_of_the_end"),8).register();
        IGNIS_GOLEM = GDCompatRegistry.regGolemSummonItem("ignis_golem",
                ResourceLocation.fromNamespaceAndPath(CataDispatch.MODID,"resurgent_flame"),8).register();
        SCYLLA_GOLEM = GDCompatRegistry.regGolemSummonItem("scylla_golem",
                ResourceLocation.fromNamespaceAndPath(CataDispatch.MODID,"heavenly_storm"),8).register();
    }

}
