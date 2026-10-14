package dev.toi_et_moi.mgdp.content.modifier.base;

import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.l2library.util.math.MathHelper;
import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

/**
 * 用于承载比 {@link AttributeGolemModifier}
 * 更灵活的 vanilla 属性修改（可显式指定 {@link AttributeModifier.Operation}，
 * 即能区分 MULTIPLY_BASE 与 MULTIPLY_TOTAL）。
 *
 * <p>本体的 {@code AttributeGolemModifier} 只能表达 BASE/ADD/PERCENT 三种语义，
 * 无法区分 MULTIPLY_BASE 与 MULTIPLY_TOTAL，也无法对护甲/韧性做百分比乘法。
 * 因此引入本类：在 {@link #onAiStep} 中幂等地维护 {@link AttributeModifier}
 * 的添加，并在 {@link #remove} 中显式移除。</p>
 *
 * <p>因为 {@link #onAiStep} 只会在「傀儡确已持有该 modifier」时被本体触发
 * （{@code AbstractGolemEntity#aiStep} 遍历 {@code getModifiersExtended()}），
 * 当升级被取下后本方法不再被调用，残留的 {@link AttributeModifier} 必须由外部清理。
 * 清理点是 {@code updateAttributes} 的 mixin 注入：
 * <pre>{@code
 * @Inject(method = "updateAttributes", at = @At("TAIL"), remap = false)
 * private void mgdp$cleanupAttributeOps(CallbackInfo ci) {
 *     AttributeOperationModifier.removeAll((AbstractGolemEntity<?, ?>) (Object) this);
 * }
 * }</pre>
 * 注入后残留 modifier 会被统一清除，仍持有本 modifier 的傀儡会在下一次
 * {@code onAiStep} 重新补回正确值。</p>
 */
public class AttributeOperationModifier extends GolemModifier {

    /**
     * 全部已实例化的 {@link AttributeOperationModifier}，供 {@link #removeAll} 统一清理。
     * modifier 由 Registrate 注册为单例，因此生命周期安全；列表仅在类加载期增长。
     */
    public static final List<AttributeOperationModifier> LIST = new ArrayList<>();

    /**
     * 单条 vanilla 属性修改：作用于哪个 attribute、名称、操作类型与数值。
     * 数值在 apply 时按 {@code value * level} 缩放（与本体
     * {@code AttributeGolemModifier.AttrEntry#getValue(int)} 一致）。
     */
    public record AttrEntry(Supplier<Attribute> attribute, String name,
                            AttributeModifier.Operation operation, double value) {

        /** 由名称派生稳定 UUID，保证同一条目跨实体、跨 reload 一致，且与其它 modifier 不冲突。 */
        private UUID id() {
            return MathHelper.getUUIDFromString(MGDP.MODID + ":" + name);
        }

        private AttributeModifier create(int level) {
            return new AttributeModifier(id(), name, value * level, operation);
        }

        /**生成tooltip 倣本體GolemStatType#getAdderTooltip */
        private MutableComponent getTooltip(){
            String key = "";
            double result = value;
            switch (operation){
                case ADDITION -> {
                    // 此路徑原則上不應產生、當代以AttributeGolemModifier
                    key = "attribute.modifier." + (value < 0 ? "take." : "plus.") + "0";
                }
                case MULTIPLY_BASE -> {
                    key = "attribute.modifier." + (value < 0 ? "take." : "plus.") + "1";
                    result = value * 100;
                }
                case MULTIPLY_TOTAL -> {
                    key = "attribute.modifier.equals.1";
                    result = (1 + value) * 100;
                }
            }
            return Component.translatable(key,
                    ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(result)),
                    Component.translatable(attribute.get().getDescriptionId())).withStyle(ChatFormatting.BLUE);
        }
    }

    private final AttrEntry[] entries;

    public AttributeOperationModifier(StatFilterType type, int maxLevel, AttrEntry... entries) {
        super(type, maxLevel);
        this.entries = entries;
        LIST.add(this);
    }

    public AttributeOperationModifier(AttrEntry... entries) {
        this(StatFilterType.ATTACK, 1, entries);
    }

    @Override
    public void onAiStep(@NotNull AbstractGolemEntity<?, ?> golem, int level) {
        for (AttrEntry entry : entries) {
            AttributeInstance inst = golem.getAttribute(entry.attribute().get());
            if (inst == null) continue;

            UUID id = entry.id();
            AttributeModifier existing = inst.getModifier(id);
            double desired = entry.value() * level;

            if (existing == null) {
                // 数值为零则跳过，避免挂一个无意义的 0 修饰符
                if (desired != 0) inst.addPermanentModifier(entry.create(level));
            } else if (existing.getAmount() != desired || existing.getOperation() != entry.operation()) {
                // 等级/数值变化时先摘旧再补新（1.20.1 同 UUID 重复 addPermanentModifier 会抛异常）
                inst.removeModifier(id);
                if (desired != 0) inst.addPermanentModifier(entry.create(level));
            }
        }
    }

    /**
     * 移除本 modifier 施加到该傀儡上的全部 {@link AttributeModifier}。
     * 供升级被取下时的清理逻辑调用（见类 javadoc 中的 mixin 注入点）。
     */
    public void remove(@NotNull AbstractGolemEntity<?, ?> golem) {
        for (AttrEntry entry : entries) {
            AttributeInstance inst = golem.getAttribute(entry.attribute().get());
            if (inst != null) inst.removeModifier(entry.id());
        }
    }

    /**
     * 移除所有 {@link AttributeOperationModifier} 施加到该傀儡上的 modifier。
     * 计划由 {@code updateAttributes} 的 mixin 在尾部调用，作为取下升级后的兜底清理。
     */
    public static void removeAll(@NotNull AbstractGolemEntity<?, ?> golem) {
        for (AttributeOperationModifier modifier : LIST) {
            modifier.remove(golem);
        }
    }

    @Override
    public @NotNull List<MutableComponent> getDetail(int v) {
        List<MutableComponent> ans = new ArrayList<>();
        for (AttrEntry entry: entries){
            ans.add(entry.getTooltip());
        }
        ans.addAll(super.getDetail(v));
        return ans;
    }
}