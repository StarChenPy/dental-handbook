package xyz.starchenpy.dental_handbook.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import xyz.starchenpy.dental_handbook.DentalHandbook;

public class ModDamageType {
    public static final ResourceKey<DamageType> ORAL_BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DentalHandbook.MOD_ID, "oral_bleeding"));
}
