package xyz.starchenpy.dental_handbook;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.ModSounds;
import xyz.starchenpy.dental_handbook.common.advancement.ModTriggers;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.ModTabs;
import xyz.starchenpy.dental_handbook.common.particle.ModParticleType;

@Mod(DentalHandbook.MOD_ID)
public class DentalHandbook {
    public static final String MOD_ID = "dental_handbook";

    public DentalHandbook(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModTriggers.register(modEventBus);
        ModParticleType.register(modEventBus);
        ModSounds.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
