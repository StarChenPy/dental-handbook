package xyz.starchenpy.dental_handbook.common.item.toothpaste;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.util.EffectUtil;

public abstract class AbstractToothpaste extends Item {
    protected int maxLevel;
    protected int color;

    /**
     * 牙膏的抽象类
     * @param item 物品属性
     * @param maxLevel 能刷掉的蛀牙的最大等级
     */
    public AbstractToothpaste(Properties item, int maxLevel, int color) {
        super(item);
        this.maxLevel = maxLevel;
        this.color = color;
    }

    /**
     * 降低一级蛀牙效果
     * @param entity 玩家实体
     */
    protected void cleanTooth(LivingEntity entity) {
        MobEffectInstance effectToothDecay = entity.getEffect(ModEffects.TOOTH_DECAY.get());

        if (effectToothDecay == null) {
            return;
        }

        // 除蛀
        int amplifier = effectToothDecay.getAmplifier();
        if (amplifier < this.maxLevel) {
            EffectUtil.updateEffect(entity, effectToothDecay, 0, amplifier - 1);
        }
    }

    /**
     * 用来获取粒子效果的颜色
     */
    public int getColor() {
        return this.color;
    }

    public abstract void effect(LivingEntity entity);
}
