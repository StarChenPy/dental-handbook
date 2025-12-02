package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class GoldenAppleToothpaste extends AbstractToothpaste {
    public GoldenAppleToothpaste() {
        super(new Item.Properties().durability(12), 4, 0xEAEE57);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.removeEffect(ModEffects.INJURY_ORAL.get());
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3600, 0));
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL.get(), 9600));
    }
}
