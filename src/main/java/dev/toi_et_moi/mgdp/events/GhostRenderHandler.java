package dev.toi_et_moi.mgdp.events;

import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import dev.toi_et_moi.mgdp.util.EventUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import src.toi_et_moi.mgdp.Mgdp;

/**
 * 幽灵升级的客户端渲染抑制。
 *
 * <p>取消 {@link RenderLivingEvent.Pre} 即可让 {@code LivingEntityRenderer#render} 在
 * <b>第一条指令处</b>直接返回（Forge 的补丁形式为
 * {@code if (EVENT_BUS.post(new RenderLivingEvent.Pre<>(...))) return;}），
 * 从而一次性覆盖：模型、所有 {@code RenderLayer}（含本模组的能量护盾层等）、
 * 以及末尾经 {@code super.render} 绘制的名牌。</p>
 *
 * <p><b>影子不在这里处理</b>：影子是 {@code EntityRenderDispatcher#render} 在
 * {@code renderer.render(...)} 返回<b>之后</b>调用私有静态方法 {@code renderShadow} 绘制的，
 * 取消 Pre 覆盖不到它。影子的抑制在 {@code EntityRendererShadowMixin} 中，
 * 与"拾荒箱没有影子"的彩蛋共用同一处注入点。</p>
 *
 * <p><b>顺带覆盖的两个场景</b>（无需额外代码）：</p>
 * <ul>
 *   <li>人形傀儡的玩家皮肤：{@code PlayerSkinRenderer} 继承 {@code HumanoidGolemRenderer}
 *       并在其 {@code render} 里转调 {@code renderImpl}，最终仍进入
 *       {@code LivingEntityRenderer#render}，所以 Pre 事件同样触发。</li>
 *   <li>傀儡的物品形态：{@code GolemBEWLR#renderEntity} 用
 *       {@code Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(golem).render(...)}
 *       渲染显示实体，而该实体经 {@code onCreate(materials, upgrades, null)} 已带上
 *       GHOST modifier，因此同样被 Pre 事件拦下。</li>
 * </ul>
 *
 * <p>服务端不参与本流程：{@code value = Dist.CLIENT} 让 Forge 在专用服务器上跳过本类，
 * 既避免加载客户端事件类型，也保证模组的 AI/索敌逻辑与是否装有该升级无关。</p>
 */
@Mod.EventBusSubscriber(modid = Mgdp.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GhostRenderHandler {

	@SubscribeEvent
	public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
		if(EventUtil.hasGolemModifier(event, MGDPModifiers.GHOST)) event.setCanceled(true);
	}
}
