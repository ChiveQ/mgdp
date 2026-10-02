package dev.toi_et_moi.mgdp.init.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import dev.toi_et_moi.mgdp.init.MGDP;
import net.minecraft.world.entity.MobCategory;
import src.toi_et_moi.mgdp.entity.GuardianLaserTargetEntity;
import src.toi_et_moi.mgdp.entity.MourningBeamEntity;

public class MGDPMiscEntities {
    public static void register() {}

    public static final EntityEntry<MourningBeamEntity> MOURNING_BEAM = MGDP.REGISTRATE
            .<MourningBeamEntity>entity("mourning_beam",MourningBeamEntity::new, MobCategory.MISC)
            .properties(p -> p.fireImmune().noSave().noSummon().sized(0,0))
            .renderer(null) // TODO 渲染器未传入 似乎不必传入于此？
            .register();

    public static final EntityEntry<GuardianLaserTargetEntity> GUARDIAN_LASER_TARGET = MGDP.REGISTRATE
            .<GuardianLaserTargetEntity>entity("guardian_laser_target",GuardianLaserTargetEntity::new,MobCategory.MISC)
            .properties(p -> p.fireImmune().noSave().noSummon().sized(0,0)).register();

}
