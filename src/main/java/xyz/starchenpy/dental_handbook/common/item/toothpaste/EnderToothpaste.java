package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class EnderToothpaste extends AbstractToothpaste {
    public EnderToothpaste(Properties item) {
        super(item, 3, 0x258474);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.removeEffect(ModEffects.INJURY_ORAL.get());
        entity.addEffect(new MobEffectInstance(ModEffects.ENDER_AFFINITY.get(), 3600, 0));
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL.get(), 9600));
    }
}
