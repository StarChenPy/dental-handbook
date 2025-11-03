package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class CharcoalToothpaste extends AbstractToothpaste {
    public CharcoalToothpaste(Properties item) {
        super(item, 2, 0xFF2E2E30);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        // 加口腔清洁buff
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL, 9600));
    }
}
