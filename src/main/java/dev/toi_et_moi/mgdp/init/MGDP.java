package dev.toi_et_moi.mgdp.init;

import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MGDP.MODID)
@Mod.EventBusSubscriber(modid = MGDP.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MGDP {

    public static final String MODID = "mgdp"; // Modular Golems: Decisive Plan
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * L2注册器
     * */
    public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

    public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
            ResourceLocation.fromNamespaceAndPath(MODID,"main"),5,null);

    public MGDP(){
        registerRegistrates();
    }

    private static void registerRegistrates(){
        MGDPItems.register();
        MGDPModifiers.register();
    }

}
