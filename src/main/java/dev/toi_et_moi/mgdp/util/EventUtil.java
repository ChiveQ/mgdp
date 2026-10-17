package dev.toi_et_moi.mgdp.util;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EventUtil {

    public static AbstractGolemEntity<?,?> getGolem(LivingEvent event){
        LivingEntity entity = event.getEntity();
        if(entity.level().isClientSide()) return null;
        if(entity instanceof AbstractGolemEntity<?, ?> golem) return golem;
        return null;
    }

    public static AbstractGolemEntity<?,?> getGolem(RenderLivingEvent<?,?> event){
        LivingEntity entity = event.getEntity();
        if(entity instanceof AbstractGolemEntity<?, ?> golem) return golem;
        return null;
    }

    public static boolean hasGolemModifier(AbstractGolemEntity<?,?> golem, RegistryEntry<? extends GolemModifier> modifier){
        if(golem == null) return false;
        return golem.getModifiers().containsKey(modifier.get());
    }

    public static boolean hasGolemModifier(Entity entity,RegistryEntry<? extends GolemModifier> modifier){
        if(entity instanceof AbstractGolemEntity<?,?> golem) return hasGolemModifier(golem,modifier);
        return false;
    }

    public static boolean hasGolemModifier(LivingEvent event, RegistryEntry<? extends GolemModifier> modifier){
        return hasGolemModifier(getGolem(event),modifier);
    }


    public static boolean hasGolemModifier(RenderLivingEvent<?,?> event, RegistryEntry<? extends GolemModifier> modifier){
        return hasGolemModifier(getGolem(event),modifier);
    }
}
