package src.toi_et_moi.mgdp.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.toi_et_moi.mgdp.util.EventUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 傀儡影子抑制。
 *
 * <p>影子<b>不是</b>在 {@code renderer.render(...)} 里画的：影子由
 * {@code EntityRenderDispatcher#render} 在 {@code renderer.render(...)} 返回之后，
 * 经私有静态方法 {@code renderShadow(...)} 单独绘制，其前置条件是
 * {@code shouldRenderShadow && !entity.isInvisible()}。
 * 因此"取消 {@code RenderLivingEvent.Pre}"只能隐藏模型与名牌，隐藏不了影子；
 * 而另一条路——把实体的 {@code isInvisible()} 置真——会连带改变
 * {@code TargetingConditions} 的索敌判定（幽灵升级明确要求不影响索敌），不可取。</p>
 *
 * <p>本注入点包住那次 {@code renderShadow} 调用并直接跳过它，
 * 于是既不依赖 {@code isInvisible()}，也不需要临时改写渲染器的
 * {@code shadowRadius} 字段（早期实现正是那样做的，代价是共享渲染器实例被写坏、
 * 且恢复逻辑挂在永不触发的 {@code RenderLivingEvent.Post} 上）。</p>
 *
 * <p>目前服务两个用途：</p>
 * <ul>
 *   <li>{@code ghost}——功能：幽灵傀儡连影子一起消失。</li>
 *   <li>{@code scav_box}——彩蛋：叼烟哥没有影子，影子被吓跑了。</li>
 * </ul>
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRendererShadowMixin {

    @WrapOperation(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;FFLnet/minecraft/world/level/LevelReader;F)V"))
    private void mgdp$skipGolemShadow(PoseStack poseStack, MultiBufferSource buffer, Entity entity,
                                      float size, float partialTick, LevelReader level, float shadowRadius,
                                      Operation<Void> original) {
        if(EventUtil.hasGolemModifier(entity,MGDPModifiers.GHOST) ||
                EventUtil.hasGolemModifier(entity,MGDPModifiers.SCAV_BOX)) return;
        original.call(poseStack, buffer, entity, size, partialTick, level, shadowRadius);
    }
}
