package dev.toi_et_moi.mgdp.content.modifier.movement;

import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import org.jetbrains.annotations.NotNull;

public class SpiritModifier extends GolemModifier {

    public SpiritModifier() {
        super(StatFilterType.MOVEMENT, 1);
    }

    @Override
    public void onAiStep(@NotNull AbstractGolemEntity<?, ?> golem, int level) {
        if(!golem.noPhysics) golem.noPhysics = true;
    }

    public static void resetPhysics(@NotNull AbstractGolemEntity<?, ?> golem) {
        golem.noPhysics = false; // 移除後恢復
    }
}
