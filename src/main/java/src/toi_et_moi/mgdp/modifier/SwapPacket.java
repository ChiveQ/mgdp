package src.toi_et_moi.mgdp.modifier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import src.toi_et_moi.mgdp.modifier.special.SwapModifier;

import java.util.function.Supplier;

public class SwapPacket {

    public SwapPacket() {
    }

    public static void encode(SwapPacket packet, FriendlyByteBuf buf) {
    }

    public static SwapPacket decode(FriendlyByteBuf buf) {
        return new SwapPacket();
    }

    public static void handle(SwapPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            SwapModifier.trySwapByPlayer(player);
        });
        context.setPacketHandled(true);
    }
}
