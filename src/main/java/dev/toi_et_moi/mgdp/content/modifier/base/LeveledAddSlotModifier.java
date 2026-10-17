package dev.toi_et_moi.mgdp.content.modifier.base;

import dev.xkmc.modulargolems.content.item.upgrade.IUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeveledAddSlotModifier extends AddSlotModifier {

    private final int slots;

    public LeveledAddSlotModifier(int maxLevel, int slots) {
        super(maxLevel);
        this.slots = slots;
    }

    @Override
    public int addSlot(@NotNull List<IUpgradeItem> list, int lv) {
        return slots * lv;
    }

    @Override
    public @NotNull List<MutableComponent> getDetail(int v) {
        return List.of(Component.translatable(getDescriptionId() + ".desc", slots * v)
                .withStyle(net.minecraft.ChatFormatting.GREEN));
    }
}
