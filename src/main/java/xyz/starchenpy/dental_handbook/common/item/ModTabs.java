package xyz.starchenpy.dental_handbook.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".tab"))
            .icon(() -> ModItems.WOODEN_TOOTHBRUSH.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> holder : ModItems.getAllModItem()) {
                    output.accept(holder.get());
                }
            })
            .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
