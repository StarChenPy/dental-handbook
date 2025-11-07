package xyz.starchenpy.dental_handbook.client.registry;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import xyz.starchenpy.dental_handbook.client.gui.DentureScreen;
import xyz.starchenpy.dental_handbook.common.gui.ModMenu;

@EventBusSubscriber(Dist.CLIENT)
public class ScreensRegistry {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenu.DENTURE_MENU.get(), DentureScreen::new);
    }
}
