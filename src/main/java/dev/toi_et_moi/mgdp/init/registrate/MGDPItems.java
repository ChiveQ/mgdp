package dev.toi_et_moi.mgdp.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.init.registrate.GolemItems;
import net.minecraft.world.item.CreativeModeTab;

import static dev.xkmc.modulargolems.init.ModularGolems.REGISTRATE;

public class MGDPItems {

    // 空方法 仅用于唤起本类加载
    public static void register() {}

    public static final RegistryEntry<CreativeModeTab> MGDP_TAB =REGISTRATE.buildL2CreativeTab("mgdp_tab", "MG: Decisive Plan - Main" , b -> b
            .icon(GolemItems.GOLEM_TEMPLATE::asStack)); // 待改为MGDPItems.FLIGHT

}
