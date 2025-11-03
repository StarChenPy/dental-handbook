package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class GoldenAppleToothpaste extends AbstractToothpaste {
    public GoldenAppleToothpaste(Properties item) {
        super(item, 4, 0xFFEAEE57);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.removeEffect(ModEffects.INJURY_ORAL);
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3600));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600));
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL, 9600));
    }
}
