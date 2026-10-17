package dev.toi_et_moi.mgdp.content.modifier.base;

import dev.xkmc.modulargolems.content.item.upgrade.IUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.common.AddSlotModifier;
import net.minecraft.ChatFormatting;
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
    public @NotNull Component getTooltip(int v) {
        return Component.translatable(getDescriptionId(),slots * v).withStyle(ChatFormatting.LIGHT_PURPLE);
    }

}
