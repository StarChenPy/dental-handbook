package xyz.starchenpy.dental_handbook.common.gui;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModMenu {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);

    public static final Supplier<MenuType<DentureMenu>> DENTURE_MENU =
            MENU_TYPES.register("denture", () -> new MenuType<>(DentureMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
