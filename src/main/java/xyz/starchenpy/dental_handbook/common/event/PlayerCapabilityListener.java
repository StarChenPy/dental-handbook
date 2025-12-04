package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.starchenpy.dental_handbook.common.capability.DentureProvider;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.network.NetworkHandler;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

@Mod.EventBusSubscriber
public class PlayerCapabilityListener {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(MOD_ID, "denture"), new DentureProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        boolean fromEnd = event.getOriginal().level().dimension().equals(Level.END);
        boolean died = event.isWasDeath();
        if (!died && !fromEnd) {
            return;
        }

        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(ModCapabilities.DENTURE_CAP).ifPresent(oldStore -> {
            event.getEntity().getCapability(ModCapabilities.DENTURE_CAP).ifPresent(newStore -> {
                newStore.deserializeNBT(oldStore.serializeNBT());
            });
        });
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            NetworkHandler.syncDenture(sp);
        }
    }
}
