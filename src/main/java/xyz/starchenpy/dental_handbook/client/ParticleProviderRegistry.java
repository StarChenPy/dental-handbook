package xyz.starchenpy.dental_handbook.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.starchenpy.dental_handbook.common.particle.ModParticleType;
import xyz.starchenpy.dental_handbook.common.particle.ToothpasteParticle;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProviderRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleType.TOOTHPASTE_PARTICLE.get(), new ToothpasteParticle.Provider());
    }
}
