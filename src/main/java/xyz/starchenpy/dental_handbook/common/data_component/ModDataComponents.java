package xyz.starchenpy.dental_handbook.common.data_component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ToothpasteRecord>> TOOTHPASTE = DATA_COMPONENTS.registerComponentType(
            "toothpaste",
            builder -> builder
                    .persistent(ToothpasteRecord.BASIC_CODEC)
                    .networkSynchronized(ToothpasteRecord.BASIC_STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SELECTED_DENTURE_SLOT = DATA_COMPONENTS.registerComponentType(
            "selected_denture_slot",
            builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
