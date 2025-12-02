package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

public class CharcoalToothpaste extends AbstractToothpaste {
    public CharcoalToothpaste() {
        super(new Item.Properties().durability(16), 2, 0x2E2E30);
    }

    @Override
    public void effect(LivingEntity entity) {
        cleanTooth(entity);
        // 加口腔清洁buff
        entity.addEffect(new MobEffectInstance(ModEffects.CLEAN_ORAL.get(), 9600));
    }
}
