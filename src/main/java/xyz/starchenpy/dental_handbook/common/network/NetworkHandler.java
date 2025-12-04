package xyz.starchenpy.dental_handbook.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(MOD_ID, "denture"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    private static int packetId = 0;

    public static void register() {
        CHANNEL.registerMessage(
                packetId++,
                SyncDenturePacket.class,
                SyncDenturePacket::encode,
                SyncDenturePacket::decode,
                SyncDenturePacket::handle
        );
    }

    // 发送玩家数据到客户端
    public static void syncDenture(ServerPlayer player) {
        player.getCapability(ModCapabilities.DENTURE_CAP).ifPresent(
                store -> CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                        new SyncDenturePacket(store.serializeNBT())
        ));
    }
}
