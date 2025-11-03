package xyz.starchenpy.dental_handbook.common.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class ToothpasteParticleType extends ParticleType<ToothpasteParticleOption> {
    public ToothpasteParticleType() {
        super(false);
    }

    @NotNull
    @Override
    public MapCodec<ToothpasteParticleOption> codec() {
        return ToothpasteParticleOption.codec();
    }

    @NotNull
    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, ToothpasteParticleOption> streamCodec() {
        return ToothpasteParticleOption.streamCodec();
    }
}
