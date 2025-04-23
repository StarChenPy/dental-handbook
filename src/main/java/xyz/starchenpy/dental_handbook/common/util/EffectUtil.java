package xyz.starchenpy.dental_handbook.common.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectUtil {
    public static void getOrUpdateEffect(LivingEntity entity, MobEffect effect, int duration, int maxLevel, boolean canCure, boolean haveParticle) {
        MobEffectInstance effectInstance = entity.getEffect(effect);
        if (effectInstance == null) {
            MobEffectInstance instance = new MobEffectInstance(effect, duration, 0, false, haveParticle, true);
            // 设定无法被其它方法移除
            if (!canCure) {
                instance.getCures().clear();
            }
            entity.addEffect(instance);
        } else {
            int amplifier = effectInstance.getAmplifier();
            if (amplifier < maxLevel) {
                updateEffect(entity, effectInstance, 0, amplifier + 1);
            }
        }
    }

    /**
     * 用于更新实体药水效果，上升下降皆可
     */
    public static void updateEffect(LivingEntity entity, MobEffectInstance effect, int duration, int amplifier) {
        duration = duration != 0 ? duration : effect.getDuration();
        int oldLevel = effect.getAmplifier();

        if (oldLevel > amplifier) {
            entity.removeEffect(effect.getEffect());

            if (amplifier >= 0) {
                MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), duration, amplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon());
                newEffect.getCures().clear();
                newEffect.getCures().addAll(effect.getCures());
                entity.addEffect(newEffect);
            }
        } else {
            effect.update(new MobEffectInstance(effect.getEffect(), duration, amplifier));
            // effect.update() 不会触发成就，这里自行触发一次
            if (entity instanceof ServerPlayer player) {
                CriteriaTriggers.EFFECTS_CHANGED.trigger(player, player);
            }
        }
    }
}
