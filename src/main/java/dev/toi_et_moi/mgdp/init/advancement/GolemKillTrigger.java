package dev.toi_et_moi.mgdp.init.advancement;

import com.google.gson.JsonObject;
import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.l2library.serial.advancements.BaseCriterion;
import dev.xkmc.l2library.serial.advancements.BaseCriterionInstance;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;
import src.toi_et_moi.mgdp.init.MGDPStats;

import java.util.function.BiFunction;

//public class GolemKillTrigger extends SimpleCriterionTrigger<GolemKillTrigger.Instance> {
public class GolemKillTrigger extends BaseCriterion<GolemKillTrigger.Ins,GolemKillTrigger> {

	public GolemKillTrigger(ResourceLocation id){
		super(id,Ins::new,Ins.class);
	}

	public static Ins ins(){
		return new Ins(MGDPTriggers.GOLEM_KILL.getId(),ContextAwarePredicate.ANY);
	}

	@Override
	protected @NotNull Ins createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext ctx) {
		int threshold = json.get("threshold").getAsInt();
		return new Ins(threshold);
	}

	public void trigger(ServerPlayer player) {
		super.trigger(player, ins -> ins.matches(player));
	}

	@SerialClass
	public static class Ins extends BaseCriterionInstance<Ins,GolemKillTrigger> {

		@SerialClass.SerialField
		private int threshold;

		public Ins(int threshold) {
			super(MGDPTriggers.GOLEM_KILL.getId(), ContextAwarePredicate.ANY);
			this.threshold = threshold;
		}

		public Ins(ResourceLocation id, ContextAwarePredicate player) {
			super(id, player);
		}

		public boolean matches(ServerPlayer player) {
			int count = player.getStats().getValue(Stats.CUSTOM.get(MGDPStats.GOLEM_KILLS));
			return count >= threshold;
		}

//		@Override
//		public @NotNull JsonObject serializeToJson(SerializationContext ctx) {
//			JsonObject json = super.serializeToJson(ctx);
//			json.addProperty("threshold", threshold);
//			return json;
//		}
	}
}
