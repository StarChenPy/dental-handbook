package xyz.starchenpy.dental_handbook.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.network.SyncDenturePacket;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    public static void handleSync(SyncDenturePacket msg) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(ModCapabilities.DENTURE_CAP)
                    .ifPresent(store -> store.deserializeNBT(msg.tag()));
        }
    }
}
