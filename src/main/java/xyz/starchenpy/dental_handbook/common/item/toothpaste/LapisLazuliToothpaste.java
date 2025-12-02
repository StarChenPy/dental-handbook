package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class LapisLazuliToothpaste extends AbstractToothpaste {
    public LapisLazuliToothpaste() {
        super(new Item.Properties().durability(12), 3, 0x0059FF);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 6000, 0));
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL.get(), 9600));
    }
}
