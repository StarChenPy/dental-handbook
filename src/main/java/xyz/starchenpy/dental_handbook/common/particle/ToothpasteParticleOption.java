package xyz.starchenpy.dental_handbook.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public record ToothpasteParticleOption(ItemStack itemStack) implements ParticleOptions {
    private static final Codec<ItemStack> ITEM_CODEC = Codec.withAlternative(ItemStack.SINGLE_ITEM_CODEC, ItemStack.ITEM_NON_AIR_CODEC, ItemStack::new);

    public static MapCodec<ToothpasteParticleOption> codec() {
        return ITEM_CODEC.xmap(ToothpasteParticleOption::new, ToothpasteParticleOption::getItem).fieldOf("item");
    }

    public static StreamCodec<? super RegistryFriendlyByteBuf, ToothpasteParticleOption> streamCodec() {
        return ItemStack.STREAM_CODEC.map(ToothpasteParticleOption::new, ToothpasteParticleOption::getItem);
    }

    public ToothpasteParticleOption(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
    }

    @Nonnull
    @Override
    public ParticleType<ToothpasteParticleOption> getType() {
        return ModParticleType.TOOTHPASTE_PARTICLE.get();
    }

    public ItemStack getItem() {
        return this.itemStack;
    }
}
