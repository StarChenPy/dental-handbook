package xyz.starchenpy.dental_handbook.common.data_component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ToothpasteRecord>> BASIC_EXAMPLE = DATA_COMPONENTS.registerComponentType(
            "basic",
            builder -> builder
                    .persistent(ToothpasteRecord.BASIC_CODEC)
                    .networkSynchronized(ToothpasteRecord.BASIC_STREAM_CODEC)
    );

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
