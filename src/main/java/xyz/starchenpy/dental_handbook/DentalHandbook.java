package xyz.starchenpy.dental_handbook;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.ModSounds;
import xyz.starchenpy.dental_handbook.common.advancements.ModTriggers;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.ModTabs;
import xyz.starchenpy.dental_handbook.common.particle.ModParticleType;

@Mod(DentalHandbook.MOD_ID)
public class DentalHandbook {
    public static final String MOD_ID = "dental_handbook";

    public DentalHandbook(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticleType.register(modEventBus);
        ModSounds.register(modEventBus);
        // 1.20.1 还只能通过这种方式注册成就触发器
        modEventBus.addListener(ModTriggers::register);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
