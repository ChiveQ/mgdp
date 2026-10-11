package dev.toi_et_moi.mgdp.compat.twilightforest;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GDCompatRegistry;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GolemSummonItem;
import net.minecraft.resources.ResourceLocation;

public class TFGDSummonRegistry {

    public static void register(){}

    public static final ItemEntry<GolemSummonItem> TWILIGHT_GOLEM;

    static{
        TWILIGHT_GOLEM = GDCompatRegistry.regGolemSummonItem("twilight_golem",
                ResourceLocation.fromNamespaceAndPath(TFDispatch.MODID,"twilight_invasion")).register();
    }

}
