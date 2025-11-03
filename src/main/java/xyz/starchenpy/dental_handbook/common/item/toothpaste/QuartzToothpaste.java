package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class QuartzToothpaste extends AbstractToothpaste {
    public QuartzToothpaste(Properties item) {
        super(item, 4, 0xFFE9E9E4);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL, 9600));
    }
}
