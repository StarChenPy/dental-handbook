package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class RedstoneToothpaste extends AbstractToothpaste {
    public RedstoneToothpaste() {
        super(new Item.Properties().durability(12), 3, 0xFF0000);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0));
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL.get(), 9600));
    }
}
