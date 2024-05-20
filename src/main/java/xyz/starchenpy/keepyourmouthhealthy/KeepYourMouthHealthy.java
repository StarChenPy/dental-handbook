package xyz.starchenpy.keepyourmouthhealthy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.starchenpy.keepyourmouthhealthy.common.Config;
import xyz.starchenpy.keepyourmouthhealthy.common.ModSounds;
import xyz.starchenpy.keepyourmouthhealthy.common.advancements.ModTriggers;
import xyz.starchenpy.keepyourmouthhealthy.common.effect.ModEffects;
import xyz.starchenpy.keepyourmouthhealthy.common.event.EntityEatListener;
import xyz.starchenpy.keepyourmouthhealthy.common.event.ItemRenderListener;
import xyz.starchenpy.keepyourmouthhealthy.common.item.ModItems;
import xyz.starchenpy.keepyourmouthhealthy.common.item.ModTabs;
import xyz.starchenpy.keepyourmouthhealthy.common.particle.ModParticleType;

@Mod(KeepYourMouthHealthy.MOD_ID)
public class KeepYourMouthHealthy {
    public static final String MOD_ID = "keepyourmouthhealthy";

    public KeepYourMouthHealthy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticleType.register(modEventBus);
        ModSounds.register(modEventBus);

        modEventBus.addListener(ModTriggers::register);

        MinecraftForge.EVENT_BUS.register(ModTriggers.class);
        MinecraftForge.EVENT_BUS.register(EntityEatListener.class);
        MinecraftForge.EVENT_BUS.register(ItemRenderListener.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
