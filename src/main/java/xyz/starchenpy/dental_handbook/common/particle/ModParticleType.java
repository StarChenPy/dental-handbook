package xyz.starchenpy.dental_handbook.common.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModParticleType {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MOD_ID);

    public static final Supplier<ToothpasteParticleType> TOOTHPASTE_PARTICLE = PARTICLE_TYPES.register("toothpaste_particle", ToothpasteParticleType::new);

    public static void register(IEventBus modEventBus) {
        PARTICLE_TYPES.register(modEventBus);
    }
}
