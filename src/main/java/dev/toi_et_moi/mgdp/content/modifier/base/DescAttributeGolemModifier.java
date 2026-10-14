package dev.toi_et_moi.mgdp.content.modifier.base;

import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 頪AttributeGolemModifier而存綠字描述
 * */
public class DescAttributeGolemModifier extends AttributeGolemModifier {

    public DescAttributeGolemModifier(int max, AttrEntry... entries) {
        this(max, true,entries);
    }

    public DescAttributeGolemModifier(int max, boolean showAttrs, AttrEntry... entries){
        super(max,entries);
        this.showAttrs = showAttrs;
    }

    private boolean showAttrs;

    /**
     * 此処用兼父·祖父頪之實現
     * */
    public @NotNull List<MutableComponent> getDetail(int v) {
        List<MutableComponent> ans = new ArrayList<>();
        if(showAttrs) ans.addAll(super.getDetail(v));
        ans.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GREEN));
        return ans;
    }
}
