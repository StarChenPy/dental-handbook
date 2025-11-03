package xyz.starchenpy.dental_handbook.common;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CHANCE_OF_TOOTH_DECAY = BUILDER
            .comment("This value determines the chance of tooth decay while eating (%)")
            .defineInRange("chanceOfToothDecay", 5, 0, 100);

    public static final ModConfigSpec.IntValue EXTRA_EAT_TIME = BUILDER
            .comment("This value determines the additional feed time (tick) that each level of tooth decay will increase")
            .defineInRange("extraEatTime", 16, 0, 256);

    public static final ModConfigSpec.IntValue EATING_DAMAGE = BUILDER
            .comment("This value determines the eating damage caused by the oral injury")
            .defineInRange("eatingDamage", 2, 0, 256);

    public static final ModConfigSpec.IntValue EXTRA_NUTRITION = BUILDER
            .comment("This value determines the extra nutrition (%)")
            .defineInRange("extraNutrition", 40, 0, 1000);

    public static final ModConfigSpec.IntValue EXTRA_SATURATION = BUILDER
            .comment("This value determines the extra saturation (%)")
            .defineInRange("extraSaturation", 40, 0, 1000);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
