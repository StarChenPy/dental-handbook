package xyz.starchenpy.dental_handbook.client.gui;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xyz.starchenpy.dental_handbook.common.gui.ModMenus;

public class ModScreens {
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(
                () -> MenuScreens.register(ModMenus.DENTURE_MENU.get(), DentureScreen::new)
        );
    }
}
