package dev.toi_et_moi.mgdp.init.registrate;

import dev.toi_et_moi.mgdp.init.MGDP;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class MGDPStats {

    public static void register(){}

//    public static final RegistryEntry<ResourceLocation> GOLEM_KILLS;

    static{
        String name = "golem_kills";
        ResourceLocation statId = MGDP.loc(name);
//        GOLEM_KILLS = MGDP.REGISTRATE.generic(Registri,name,() -> statId);
        Stats.CUSTOM.get(statId, StatFormatter.DEFAULT);
    }

}
