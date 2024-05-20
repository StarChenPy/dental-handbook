package xyz.starchenpy.keepyourmouthhealthy.common;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import xyz.starchenpy.keepyourmouthhealthy.KeepYourMouthHealthy;

@Mod.EventBusSubscriber(modid = KeepYourMouthHealthy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue CHANCE_OF_TOOTH_DECAY = BUILDER
            .comment("This value determines the chance of tooth decay while eating")
            .defineInRange("chanceOfToothDecay", 5, 0, 100);

    private static final ForgeConfigSpec.IntValue TOOTH_DECAY_MAX_LEVEL = BUILDER
            .comment("This value determines the maximum level of tooth decay buff that can be obtained normally")
            .defineInRange("toothDecayMaxLevel", 4, 0, 256);

    private static final ForgeConfigSpec.IntValue ORAL_INJURY_MAX_LEVEL = BUILDER
            .comment("This value determines the maximum level of oral injury buff that can be obtained normally")
            .defineInRange("oralInjuryMaxLevel", 3, 0, 256);

    private static final ForgeConfigSpec.IntValue EXTRA_EAT_TIME = BUILDER
            .comment("This value determines the additional feed time (tick) that each level of tooth decay will increase")
            .defineInRange("extraEatTime", 16, 0, 256);

    private static final ForgeConfigSpec.IntValue EATING_DAMAGE = BUILDER
            .comment("This value determines the eating damage caused by the oral injury")
            .defineInRange("eatingDamage", 2, 0, 256);

    private static final ForgeConfigSpec.DoubleValue EXTRA_NUTRITION = BUILDER
            .comment("This value determines the extra nutrition (percentage)")
            .defineInRange("extraNutrition", 0.4, 0, 16);

    private static final ForgeConfigSpec.DoubleValue EXTRA_SATURATION = BUILDER
            .comment("This value determines the extra saturation (percentage)")
            .defineInRange("extraSaturation", 0.4, 0, 16);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int chanceOfToothDecay;
    public static int toothDecayMaxLevel;
    public static int oralInjuryMaxLevel;
    public static int extraEatTime;
    public static int eatingDamage;
    public static float extraNutrition;
    public static float extraSaturation;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        chanceOfToothDecay = CHANCE_OF_TOOTH_DECAY.get();
        toothDecayMaxLevel = TOOTH_DECAY_MAX_LEVEL.get() - 1;
        oralInjuryMaxLevel = ORAL_INJURY_MAX_LEVEL.get() - 1;
        extraEatTime = EXTRA_EAT_TIME.get();
        eatingDamage = EATING_DAMAGE.get();
        extraNutrition = EXTRA_NUTRITION.get().floatValue();
        extraSaturation = EXTRA_SATURATION.get().floatValue();
    }
}
