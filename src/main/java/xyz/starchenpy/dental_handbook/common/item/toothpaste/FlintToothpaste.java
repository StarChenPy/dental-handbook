package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.starchenpy.dental_handbook.common.ModDamageType;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class FlintToothpaste extends AbstractToothpaste {
    public FlintToothpaste(Properties item) {
        super(item, 4, 0x0D0D10);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        // 加口腔损伤buff
        entity.addEffect(new MobEffectInstance(ModEffects.INJURY_ORAL.get(), 1200));
        entity.hurt(entity.damageSources().source(ModDamageType.ORAL_BLEEDING), 2);
    }
}
