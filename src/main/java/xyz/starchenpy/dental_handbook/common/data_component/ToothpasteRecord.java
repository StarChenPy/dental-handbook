package xyz.starchenpy.dental_handbook.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ToothpasteRecord(String toothpasteName) {
    public static final Codec<ToothpasteRecord> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("toothpasteName").forGetter(ToothpasteRecord::toothpasteName)
            ).apply(instance, ToothpasteRecord::new)
    );

    public static final StreamCodec<ByteBuf, ToothpasteRecord> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ToothpasteRecord::toothpasteName,
            ToothpasteRecord::new
    );
}
