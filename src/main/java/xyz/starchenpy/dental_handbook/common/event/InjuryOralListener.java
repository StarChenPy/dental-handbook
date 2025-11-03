package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.ModDamageType;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.util.EffectUtil;

import java.util.Random;

@EventBusSubscriber
public class InjuryOralListener {
    @SubscribeEvent
    public static void entityEatFinishListener(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().getFoodProperties(null) == null) {
            return;
        }

        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = entity.getEffect(ModEffects.INJURY_ORAL);

        if (effect != null) {
            int damage = (effect.getAmplifier() + 1) * Config.EATING_DAMAGE.get();
            entity.hurt(entity.damageSources().source(ModDamageType.ORAL_BLEEDING), damage);
        }

        giveEffect(entity);
    }

    private static void giveEffect(LivingEntity entity) {
        MobEffectInstance effect = entity.getEffect(ModEffects.TOOTH_DECAY);

        if (effect == null) {
            return;
        }

        // 获得口腔损伤的几率
        int chance = 5 * effect.getAmplifier();
        if (new Random().nextInt(100) > chance) {
            return;
        }

        EffectUtil.getOrUpdateEffect(entity, ModEffects.INJURY_ORAL, 180, 2, false, false);
    }
}
