package xyz.starchenpy.keepyourmouthhealthy.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.starchenpy.keepyourmouthhealthy.common.particle.ModParticleType;
import xyz.starchenpy.keepyourmouthhealthy.common.particle.ToothpasteParticle;

import static xyz.starchenpy.keepyourmouthhealthy.KeepYourMouthHealthy.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProviderRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleType.TOOTHPASTE_PARTICLE.get(), new ToothpasteParticle.Provider());
    }
}
