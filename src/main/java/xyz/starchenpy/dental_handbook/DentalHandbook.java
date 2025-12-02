package xyz.starchenpy.dental_handbook;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.ModSounds;
import xyz.starchenpy.dental_handbook.common.advancements.ModTriggers;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.event.EntityEatListener;
import xyz.starchenpy.dental_handbook.common.event.ItemRenderListener;
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

        modEventBus.addListener(ModTriggers::register);

        MinecraftForge.EVENT_BUS.register(ModTriggers.class);
        MinecraftForge.EVENT_BUS.register(EntityEatListener.class);
        MinecraftForge.EVENT_BUS.register(ItemRenderListener.class);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
