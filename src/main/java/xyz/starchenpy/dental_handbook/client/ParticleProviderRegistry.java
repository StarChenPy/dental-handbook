package xyz.starchenpy.dental_handbook.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import xyz.starchenpy.dental_handbook.common.particle.ModParticleType;
import xyz.starchenpy.dental_handbook.common.particle.ToothpasteParticle;

@EventBusSubscriber(Dist.CLIENT)
public class ParticleProviderRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleType.TOOTHPASTE_PARTICLE.get(), new ToothpasteParticle.Provider());
    }
}
