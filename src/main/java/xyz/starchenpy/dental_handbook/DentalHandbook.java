package xyz.starchenpy.dental_handbook;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.ModSounds;
import xyz.starchenpy.dental_handbook.common.advancement.ModTriggers;
import xyz.starchenpy.dental_handbook.common.ModAttachmentTypes;
import xyz.starchenpy.dental_handbook.common.data_component.ModDataComponents;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.gui.ModMenu;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.ModTabs;
import xyz.starchenpy.dental_handbook.common.particle.ModParticleType;

@Mod(DentalHandbook.MOD_ID)
public class DentalHandbook {
    public static final String MOD_ID = "dental_handbook";

    public DentalHandbook(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModTriggers.register(modEventBus);
        ModParticleType.register(modEventBus);
        ModSounds.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModMenu.register(modEventBus);
        ModAttachmentTypes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
