package xyz.starchenpy.dental_handbook.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;

import java.util.function.Supplier;

public record SyncDenturePacket(CompoundTag tag) {
    public static void encode(SyncDenturePacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.tag);
    }

    public static SyncDenturePacket decode(FriendlyByteBuf buf) {
        return new SyncDenturePacket(buf.readNbt());
    }

    public static void handle(SyncDenturePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(ModCapabilities.DENTURE_CAP).ifPresent(store -> {
                    store.deserializeNBT(msg.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}