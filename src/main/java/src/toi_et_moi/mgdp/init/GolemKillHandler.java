package src.toi_et_moi.mgdp.init;

import dev.toi_et_moi.mgdp.init.MGDP;
import dev.toi_et_moi.mgdp.init.advancement.MGDPTriggers;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MGDP.MODID)
public class GolemKillHandler {

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof AbstractGolemEntity<?, ?> golem)) return;
		if (golem.level().isClientSide()) return;
		if (MGDPStats.GOLEM_KILLS == null) return;
		Player owner = golem.getOwner();
		if (owner instanceof ServerPlayer sp) {
			sp.awardStat(MGDPStats.GOLEM_KILLS);
            MGDPTriggers.GOLEM_KILL.trigger(sp);
        }
	}
}
