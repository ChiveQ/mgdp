package dev.toi_et_moi.mgdp.compat.golemdungeons;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.compat.cataclysm.CataDispatch;
import dev.toi_et_moi.mgdp.compat.cataclysm.CataGDSummonRegistry;
import dev.toi_et_moi.mgdp.compat.jerotes.JerotesDispatch;
import dev.toi_et_moi.mgdp.compat.jerotes.JerotesGDSummonRegistry;
import dev.toi_et_moi.mgdp.compat.twilightforest.TFDispatch;
import dev.toi_et_moi.mgdp.compat.twilightforest.TFGDSummonRegistry;
import dev.toi_et_moi.mgdp.init.MGDP;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fml.ModList;

public class GDCompatRegistry {

    // 特殊：GD+其他模组
    public static void register(){
        if(ModList.get().isLoaded(JerotesDispatch.MODID)) JerotesGDSummonRegistry.register();
        if(ModList.get().isLoaded(TFDispatch.MODID)) TFGDSummonRegistry.register();
        if(ModList.get().isLoaded(CataDispatch.MODID)) CataGDSummonRegistry.register();
    }

    public static final ItemEntry<GolemSummonItem> REMNANT_GOLEM,ILLAGER_GOLEM,PIGLIN_GOLEM,SCULK_GOLEM;

    static{
        REMNANT_GOLEM = regGolemSummonItem("remnant_golem",
                ResourceLocation.fromNamespaceAndPath(GDDispatch.MODID,"factory_remnant")).register();
        ILLAGER_GOLEM = regGolemSummonItem("illager_golem",
                ResourceLocation.fromNamespaceAndPath(GDDispatch.MODID,"illagers_creation")).register();
        PIGLIN_GOLEM = regGolemSummonItem("piglin_golem",
                ResourceLocation.fromNamespaceAndPath(GDDispatch.MODID,"piglin_legacy")).register();
        SCULK_GOLEM = regGolemSummonItem("sculk_golem",
                ResourceLocation.fromNamespaceAndPath(GDDispatch.MODID,"sculk_infestation")).register();
    }

    public static ItemBuilder<GolemSummonItem, L2Registrate> regGolemSummonItem(String id, ResourceLocation trial,int durability){
        return MGDP.REGISTRATE.item(id, p ->
                new GolemSummonItem(p.stacksTo(1).durability(durability).rarity(Rarity.EPIC),trial))
                .model((ctx,pvd) -> {pvd.generated(ctx,pvd.modLoc("item/allies/"+id));})
                .tab(MGDPItems.MGDP_TAB.getKey());
    }

    public static ItemBuilder<GolemSummonItem, L2Registrate> regGolemSummonItem(String id, ResourceLocation trial) {
        return regGolemSummonItem(id,trial,40);
    }

}
