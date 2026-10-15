package dev.toi_et_moi.mgdp.events;

import dev.toi_et_moi.mgdp.init.MGDP;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.toi_et_moi.mgdp.util.EventUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MGDP.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CoronaListener {

    @SubscribeEvent
    public static void onEffectApplicable(MobEffectEvent.Applicable event){
        if(!EventUtil.hasGolemModifier(event,MGDPModifiers.CORONA)) return;
        if(event.getEffectInstance().getEffect().getCategory() == MobEffectCategory.HARMFUL){
            if(event.getEntity().level().isDay()) event.setResult(Event.Result.DENY);
        }
    }

}
