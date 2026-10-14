package src.toi_et_moi.mgdp;

import com.mojang.logging.LogUtils;
import dev.toi_et_moi.mgdp.init.MGDP;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import src.toi_et_moi.mgdp.init.IronCurtainItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import dev.toi_et_moi.mgdp.init.registrate.MGDPItems;
import src.toi_et_moi.mgdp.init.MGDPKeyMappings;
import dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers;
import src.toi_et_moi.mgdp.modifier.defense.ChargedShieldModifier;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import dev.xkmc.modulargolems.content.entity.common.GolemFlags;
import net.minecraft.world.damagesource.DamageTypes;

//@Mod(Mgdp.MODID)
public class Mgdp {

	public static final String MODID = MGDP.MODID;
	public static final Logger LOGGER = LogUtils.getLogger();

	public static SimpleChannel PACKET_HANDLER;

	public Mgdp() {
		MinecraftForge.EVENT_BUS.register(this);
//		MGDPModifiers.register();
		MGDPItems.register();
		src.toi_et_moi.mgdp.network.MGDPNetwork.register();
			PACKET_HANDLER = src.toi_et_moi.mgdp.network.MGDPNetwork.CHANNEL;
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MGDPConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MGDPConfig.CLIENT_SPEC);
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent evt) {
		if (IronCurtainItem.isProtected(evt.getEntity())) {
			evt.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void mgdp$lightningShieldRecharge(LivingAttackEvent evt) {
		if (evt.getSource().is(DamageTypes.LIGHTNING_BOLT)
				&& evt.getEntity() instanceof AbstractGolemEntity<?, ?> golem
				&& golem.hasFlag(GolemFlags.THUNDER_IMMUNE)
				&& golem.getModifiers().containsKey(MGDPModifiers.CHARGED_SHIELD.get())) {
			int lv = golem.getModifiers().get(MGDPModifiers.CHARGED_SHIELD.get());
			ChargedShieldModifier.recharge(golem, lv);
		}
	}


	@SubscribeEvent
    public void mgdp$startTracking(PlayerEvent.StartTracking evt) {
        if (!(evt.getTarget() instanceof src.toi_et_moi.mgdp.jukebox.JukeboxGolem jb)) return;
        if (!jb.mgdp$isPlaying()) return;
        if (!(evt.getEntity() instanceof net.minecraft.server.level.ServerPlayer sp)) return;
        var disc = jb.mgdp$getDisc();
        if (disc.isEmpty()) return;
        if (src.toi_et_moi.mgdp.jukebox.NetMusicCompat.isNetMusicDisc(disc)) {
            src.toi_et_moi.mgdp.jukebox.NetMusicCompat.playForPlayer(
                    sp, evt.getTarget().getId(), disc);
            return;
        }
        if (disc.getItem() instanceof net.minecraft.world.item.RecordItem ri) {
            src.toi_et_moi.mgdp.jukebox.JukeboxPacket.playRecordForPlayer(
                    sp, ri.getSound().getLocation(), evt.getTarget().getId());
        }
    }
    public void mgdp$onEntityJoin(net.minecraftforge.event.entity.EntityJoinLevelEvent evt) {
        if (!(evt.getEntity() instanceof src.toi_et_moi.mgdp.jukebox.JukeboxGolem jb)) return;
        if (evt.getLevel().isClientSide()) return;
        if (!jb.mgdp$isPlaying()) return;
        var disc = jb.mgdp$getDisc();
        if (disc.isEmpty()) return;
        if (evt.getEntity() instanceof dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity golem) {
            if (src.toi_et_moi.mgdp.jukebox.NetMusicCompat.isNetMusicDisc(disc)) {
                if (golem.getOwnerUUID() != null && golem.getServer() != null) {
                    var owner = golem.getServer().getPlayerList().getPlayer(golem.getOwnerUUID());
                    if (owner != null) {
                        src.toi_et_moi.mgdp.jukebox.NetMusicCompat.playForPlayer(
                                owner, golem.getId(), disc);
                    }
                }
                return;
            }
            if (disc.getItem() instanceof net.minecraft.world.item.RecordItem ri
                    && golem.getOwnerUUID() != null && golem.getServer() != null) {
                var owner = golem.getServer().getPlayerList().getPlayer(golem.getOwnerUUID());
                if (owner != null) {
                    src.toi_et_moi.mgdp.jukebox.JukeboxPacket.playRecordForPlayer(
                            owner, ri.getSound().getLocation(), golem.getId());
                }
            }
        }
    }


	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ClientTickHandler {
		static java.util.List<? extends AbstractGolemEntity<?,?>> cachedLowHpGolems = java.util.Collections.emptyList();
		static long lastScanTick = -1;
		@SubscribeEvent
		public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
			if (MGDPKeyMappings.SWAP.consumeClick()) {
				Mgdp.PACKET_HANDLER.sendToServer(new src.toi_et_moi.mgdp.modifier.SwapPacket());
			}
        }
        @SubscribeEvent
        public static void onRenderLivingPost(net.minecraftforge.client.event.RenderLivingEvent.Post<?, ?> event) {
            if (!(event.getEntity() instanceof dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity)) return;
            if (!net.minecraftforge.fml.ModList.get().isLoaded("netmusic")) return;
            int eid = event.getEntity().getId();
            if (eid != src.toi_et_moi.mgdp.jukebox.packet.GolemNetMusicSound.activeEntityId) return;
            String lyric = src.toi_et_moi.mgdp.jukebox.packet.GolemNetMusicSound.currentLyricLine;
            String trans = src.toi_et_moi.mgdp.jukebox.packet.GolemNetMusicSound.currentTransLyric;
            if ((lyric == null || lyric.isEmpty()) && (trans == null || trans.isEmpty())) return;
            var pose = event.getPoseStack();
            var camera = net.minecraft.client.Minecraft.getInstance().getBlockEntityRenderDispatcher().camera;
            var font = net.minecraft.client.Minecraft.getInstance().font;
            int bgColor = (int)(net.minecraft.client.Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255) << 24;
            var buf = event.getMultiBufferSource();
            pose.pushPose();
            double yOff = event.getEntity().getBbHeight() + 0.75;
            if (trans != null && !trans.isEmpty()) yOff += 0.3;
            pose.translate(0, yOff, 0);
            pose.mulPose(camera.rotation());
            pose.scale(-0.025F, -0.025F, 0.025F);
            if (lyric != null && !lyric.isEmpty()) {
                float lw = (float) (-font.width(lyric) / 2);
                font.drawInBatch(lyric, lw, 0, 0xFFFFFF, false, pose.last().pose(), buf,
                        net.minecraft.client.gui.Font.DisplayMode.NORMAL, bgColor, 0xF000F0);
            }
            if (trans != null && !trans.isEmpty()) {
                float tw = (float) (-font.width(trans) / 2);
                int tY = (lyric != null && !lyric.isEmpty()) ? 12 : 0;
                font.drawInBatch(trans, tw, tY, 0xFFFFAA, false, pose.last().pose(), buf,
                        net.minecraft.client.gui.Font.DisplayMode.NORMAL, bgColor, 0xF000F0);
            }
            pose.popPose();
        }

        /**
         * 拾荒箱头顶状态公告板渲染（与歌词同款）：服务器经 ScavStatusPacket 推送状态，
         * 这里画在傀儡头顶。不占用 customName，与玩家自定义名称互不干扰。
         */
        @SubscribeEvent
        public static void onRenderScavCountdown(net.minecraftforge.client.event.RenderLivingEvent.Post<?, ?> event) {
            if (!(event.getEntity() instanceof AbstractGolemEntity<?,?> golem)) return;
            if (!golem.getModifiers().containsKey(MGDPModifiers.SCAV_BOX.get())) return;
            long[] st = src.toi_et_moi.mgdp.network.ScavStatusPacket.CLIENT_STATUS.get(event.getEntity().getId());
            if (st == null) return;
            var level = net.minecraft.client.Minecraft.getInstance().level;
            if (level == null) return;
            if (level.getGameTime() - st[2] > 60) {
                src.toi_et_moi.mgdp.network.ScavStatusPacket.CLIENT_STATUS.remove(event.getEntity().getId());
                return;
            }
            String text;
            byte state = (byte) st[0];
            if (state == src.toi_et_moi.mgdp.network.ScavStatusPacket.STATE_COUNTING) {
                long sec = Math.max(0, st[1]);
                text = net.minecraft.network.chat.Component
                        .translatable("mgdp.scav.countdown", String.format("%d:%02d", sec / 60, sec % 60))
                        .getString();
            } else if (state == src.toi_et_moi.mgdp.network.ScavStatusPacket.STATE_COMBAT) {
                text = net.minecraft.network.chat.Component
                        .translatable("mgdp.scav.waiting", golem.getDisplayName()).getString();
            } else if (state == src.toi_et_moi.mgdp.network.ScavStatusPacket.STATE_NO_MODE) {
                text = net.minecraft.network.chat.Component
                        .translatable("mgdp.scav.mode", golem.getDisplayName()).getString();
            } else {
                return;
            }
            if (text.isEmpty()) return;
            var pose = event.getPoseStack();
            var camera = net.minecraft.client.Minecraft.getInstance().getBlockEntityRenderDispatcher().camera;
            var font = net.minecraft.client.Minecraft.getInstance().font;
            int bgColor = (int)(net.minecraft.client.Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255) << 24;
            var buf = event.getMultiBufferSource();
            pose.pushPose();
            pose.translate(0, event.getEntity().getBbHeight() + 0.5, 0);
            pose.mulPose(camera.rotation());
            pose.scale(-0.025F, -0.025F, 0.025F);
            float w = (float) (-font.width(text) / 2);
            font.drawInBatch(text, w, 0, 0xFFFFFF, false, pose.last().pose(), buf,
                    net.minecraft.client.gui.Font.DisplayMode.NORMAL, bgColor, 0xF000F0);
            pose.popPose();
        }
        
        @SubscribeEvent
        public static void onRenderLivingPre(net.minecraftforge.client.event.RenderLivingEvent.Pre<?, ?> event) {
            if (!(event.getEntity() instanceof AbstractGolemEntity<?,?> golem)) return;
            int progress = ((src.toi_et_moi.mgdp.init.IFlipData) golem).mgdp$getFlipProgress();
            if (progress <= 0) return;
            float angle = (float) java.lang.Math.toRadians(progress * 0.9f);
            float yaw = (float) java.lang.Math.toRadians(-golem.yBodyRot);
            var stack = event.getPoseStack();
            stack.mulPose(new org.joml.Quaternionf().rotationY(yaw));
            stack.mulPose(new org.joml.Quaternionf().rotationX(-angle));
            stack.mulPose(new org.joml.Quaternionf().rotationY(-yaw));
        }

        @SubscribeEvent
        public static void onRenderPre(net.minecraftforge.client.event.RenderLivingEvent.Pre<?, ?> event) {
            if (!(event.getEntity() instanceof AbstractGolemEntity<?,?> golem)) return;
            float windmill = ((src.toi_et_moi.mgdp.init.IFlipData) golem).mgdp$getWindmill();
            if (windmill <= 0) return;
            event.getPoseStack().mulPose(new org.joml.Quaternionf().rotationY((float) java.lang.Math.toRadians(windmill)));
        }

        @SubscribeEvent
        public static void onRenderOverlay(net.minecraftforge.client.event.RenderGuiOverlayEvent event) {
            if (event.getOverlay() != net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.SUBTITLES.type()) return;
            if (!MGDPConfig.golemHealthWarning) return;
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;
            // Cache entity scan to every 10 ticks for performance
            long gameTime = mc.level.getGameTime();
            if (gameTime - Mgdp.ClientTickHandler.lastScanTick >= 10 || Mgdp.ClientTickHandler.lastScanTick < 0) {
                Mgdp.ClientTickHandler.lastScanTick = gameTime;
                // 只统计玩家自己的傀儡：无主傀儡（如阵形召唤）和其他玩家的傀儡不告警
                var ownerId = mc.player.getUUID();
//                Mgdp.ClientTickHandler.cachedLowHpGolems = mc.level.getEntitiesOfClass(
//                    dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity.class,
//                    mc.player.getBoundingBox().inflate(64),
//                    g -> g.isAlive() && ownerId.equals(g.getOwnerUUID()) && g.getHealth() / g.getMaxHealth() < 0.25f);
            }
            var cache = Mgdp.ClientTickHandler.cachedLowHpGolems;
            java.util.List<net.minecraft.network.chat.MutableComponent> lines = new java.util.ArrayList<>();
            for (var g : cache) {
                lines.add(net.minecraft.network.chat.Component.literal("! ")
                    .append(g.getDisplayName())
                    .append(net.minecraft.network.chat.Component.literal(" HP: " + (int)g.getHealth() + "/" + (int)g.getMaxHealth())));
            }

            if (lines.isEmpty()) return;
            int warned = 0;
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();
            var font = mc.font;
            var startY = h - 65 - 12 * (lines.size() - 1);
            for (var text : lines) {
                int tw = font.width(text);
                event.getGuiGraphics().drawString(font, text, (w - tw) / 2, startY + 12 * warned, 0xFF4444);
                warned++;
            }
        }
		}
}
