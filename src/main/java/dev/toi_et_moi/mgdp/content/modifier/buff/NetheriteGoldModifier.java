package dev.toi_et_moi.mgdp.content.modifier.buff;

import dev.xkmc.modulargolems.content.entity.common.GolemFlags;
import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import dev.xkmc.modulargolems.init.registrate.GolemTypes;

import java.util.function.Consumer;

public class NetheriteGoldModifier extends AttributeGolemModifier {

    public NetheriteGoldModifier() {
        super(1, new AttrEntry(GolemTypes.STAT_REGEN, () -> 5.0));
    }

    @Override
    public void onRegisterFlag(Consumer<GolemFlags> addFlag) {
        addFlag.accept(GolemFlags.FIRE_IMMUNE);
    }

}
