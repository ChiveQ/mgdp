package src.toi_et_moi.mgdp.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public interface EntityAccessor {

//    @Accessor("noPhysics")
//    void setNoPhysics(boolean value);
//
//    @Accessor("DATA_SHARED_FLAGS_ID")
//    EntityDataAccessor<Byte> getDataSharedFlagsId();
}
