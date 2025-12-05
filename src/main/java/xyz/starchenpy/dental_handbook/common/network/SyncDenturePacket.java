package xyz.starchenpy.dental_handbook.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.starchenpy.dental_handbook.client.network.ClientHandler;

import java.util.function.Supplier;

public record SyncDenturePacket(CompoundTag tag) {
    public static void encode(SyncDenturePacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.tag);
    }

    public static SyncDenturePacket decode(FriendlyByteBuf buf) {
        return new SyncDenturePacket(buf.readNbt());
    }

    public static void handle(SyncDenturePacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                ClientHandler.handleSync(msg);
            }
        });

        context.setPacketHandled(true);
    }
}