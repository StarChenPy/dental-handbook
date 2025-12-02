package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class BlazeToothpaste extends AbstractToothpaste {
    public BlazeToothpaste() {
        super(new Item.Properties().durability(10), 4, 0xFFFFFFB5);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 7200));
        entity.addEffect(new MobEffectInstance(ModEffects.INJURY_ORAL.get(), 2400));
    }
}
