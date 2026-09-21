package src.toi_et_moi.mgdp.compat;

import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * l2complements（莱特兰扩充）软依赖兼容。
 * <p><b>本类不得直接 import / 引用 l2complements 模组的任何类</b>——它是可选依赖，
 * 直接引用会让 JVM 在类加载校验时抛 {@code NoClassDefFoundError}，未安装该模组的
 * 整合包开游戏即崩（曾真实发生：ReprintModifier 直接引用 LCEnchantments）。
 * 所有交互一律走反射；模组类名比较也用字符串。</p>
 */
public class L2Compat {

    private static final String MOD_ID = "l2complements";
    private static final String ENCH_CLASS = "dev.xkmc.l2complements.init.registrate.LCEnchantments";

    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    /** 反射获取 LCEnchantments 中的附魔注册项；模组缺失或字段不存在时返回 null */
    @Nullable
    public static Enchantment getEnchantment(String field) {
        if (!isLoaded()) return null;
        try {
            Class<?> cls = Class.forName(ENCH_CLASS);
            Object entry = cls.getField(field).get(null);
            Object ench = entry.getClass().getMethod("get").invoke(entry);
            return ench instanceof Enchantment e ? e : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static MobEffectInstance tryGetEffect(GolemModifier mod, int level) {
        if (!isLoaded()) return null;
        Enchantment enchant;
        // 用类名字符串比较：直接 instanceof 基础模组的兼容类会强制加载它们，
        // 而那些类本身引用了 l2complements，同样有崩溃风险
        String cls = mod.getClass().getName();
        if (cls.equals("dev.xkmc.modulargolems.compat.materials.l2complements.SoulFlameModifier")) {
            enchant = getEnchantment("FLAME_BLADE");
        } else if (cls.equals("dev.xkmc.modulargolems.compat.materials.l2complements.FreezingModifier")) {
            enchant = getEnchantment("ICE_BLADE");
        } else {
            return null;
        }
        if (enchant == null) return null;
        try {
            Method m = enchant.getClass().getMethod("getEffect", int.class);
            m.setAccessible(true);
            return (MobEffectInstance) m.invoke(enchant, level);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * 自动冶炼兼容（l2complements:smelt）：
     * 莱特兰扩充的自动冶炼走的是"玩家破块"链路（SpecialEquipmentEvents 用
     * ThreadLocal 记录破块玩家），傀儡不是玩家所以永远不会触发。这里在掉落
     * 计算后自行按熔炉配方转换：铁矿石→铁锭、原木→木炭等。
     * 时运/精准采集已在 getDrops 阶段生效；精准采集出的原矿方块没有熔炉
     * 配方，天然不会被转换。
     */
    public static void tryAutoSmelt(Level level, ItemStack tool, List<ItemStack> drops) {
        if (!isLoaded() || tool.isEmpty() || drops.isEmpty()) return;
        Enchantment smelt = getEnchantment("SMELT");
        if (smelt == null) return;
        try {
            if (EnchantmentHelper.getItemEnchantmentLevel(smelt, tool) <= 0) return;
            for (int i = 0; i < drops.size(); i++) {
                ItemStack stack = drops.get(i);
                if (stack.isEmpty()) continue;
                Optional<SmeltingRecipe> recipe = level.getRecipeManager()
                        .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), level);
                if (recipe.isPresent()) {
                    ItemStack result = recipe.get().getResultItem(level.registryAccess());
                    if (!result.isEmpty()) {
                        result = result.copy();
                        result.setCount(Math.min(result.getMaxStackSize(), result.getCount() * stack.getCount()));
                        drops.set(i, result);
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
