package dev.toi_et_moi.mgdp.compat.jerotes;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GDCompatRegistry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GolemSummonItem;
import net.minecraft.resources.ResourceLocation;

public class JerotesGDSummonRegistry {

    public static void register(){}

    public static final ItemEntry<GolemSummonItem> CARVED_GOLEM,ENHANCED_CARVED_GOLEM,QOAIKU_GOLEM,MEROR_GOLEM,REFINE_MEROR_GOLEM;

    static{
        // 召唤器
        CARVED_GOLEM = GDCompatRegistry.regGolemSummonItem("carved_golem",
                ResourceLocation.fromNamespaceAndPath(JerotesDispatch.MODID,"villager_metal")).register();
        ENHANCED_CARVED_GOLEM = GDCompatRegistry.regGolemSummonItem("enhanced_carved_golem",
                ResourceLocation.fromNamespaceAndPath(JerotesDispatch.MODID,"enhanced_villager_metal")).register();
        QOAIKU_GOLEM = GDCompatRegistry.regGolemSummonItem("qoaiku_golem",
                ResourceLocation.fromNamespaceAndPath(JerotesDispatch.MODID,"qoaiku")).register();
        MEROR_GOLEM = GDCompatRegistry.regGolemSummonItem("meror_golem",
                ResourceLocation.fromNamespaceAndPath(JerotesDispatch.MODID,"meror")).register();
        REFINE_MEROR_GOLEM = GDCompatRegistry.regGolemSummonItem("refine_meror_golem",
                ResourceLocation.fromNamespaceAndPath(JerotesDispatch.MODID,"refine_meror")).register();
    }

}
