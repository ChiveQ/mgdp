package dev.toi_et_moi.mgdp.init;

import dev.toi_et_moi.mgdp.init.registrate.MGDPMiscEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import src.toi_et_moi.mgdp.Mgdp;
import src.toi_et_moi.mgdp.init.MGDPKeyMappings;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MGDP.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MGDPClient {


    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        // TODO 此处直接移植、未修改
        event.enqueueWork(() -> {
            src.toi_et_moi.mgdp.jukebox.JukeboxClientRegister.register();
            net.minecraft.client.gui.screens.MenuScreens.register(
                    src.toi_et_moi.mgdp.init.MgdpMenus.JUKEBOX.get(),
                    src.toi_et_moi.mgdp.jukebox.JukeboxScreen::new);
            net.minecraft.client.renderer.entity.EntityRenderers.register(
                    MGDPMiscEntities.MOURNING_BEAM.get(),
                    src.toi_et_moi.mgdp.entity.MourningBeamRenderer::new);
            net.minecraft.client.renderer.entity.EntityRenderers.register(
                    MGDPMiscEntities.GUARDIAN_LASER_TARGET.get(),
                    src.toi_et_moi.mgdp.entity.GuardianLaserTargetRenderer::new);
        });
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        // TODO 此处直接移植、未修改
        event.register(MGDPKeyMappings.FLIGHT_DESCEND);
        event.register(MGDPKeyMappings.FLIGHT_SPRINT);
        event.register(MGDPKeyMappings.SWAP);
    }


}
