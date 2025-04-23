package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.util.EffectUtil;

import java.util.Random;

@Mod.EventBusSubscriber
public class ToothDecayListener {
    @SubscribeEvent
    public static void entityEatStartListener(LivingEntityUseItemEvent.Start event) {
        if (!event.getItem().isEdible()) {
            return;
        }

        MobEffectInstance effect = event.getEntity().getEffect(ModEffects.TOOTH_DECAY.get());
        if (effect != null) {
            int useDuration = event.getDuration();
            int extraDuration = (effect.getAmplifier() + 1) * Config.extraEatTime;
            // 增加吃东西的时间
            event.setDuration(useDuration + extraDuration);
        }

    }

    @SubscribeEvent
    public static void entityEatFinishListener(LivingEntityUseItemEvent.Finish event) {
        if (!event.getItem().isEdible()) {
            return;
        }

        LivingEntity entity = event.getEntity();

        // 如果有口腔洁净 buff 就不会蛀牙
        if (entity.getEffect(ModEffects.CLEAN_ORAL.get()) != null) {
            return;
        }

        // 获得蛀牙的几率
        if (new Random().nextInt(100) > Config.chanceOfToothDecay) {
            return;
        }

        EffectUtil.getOrUpdateEffect(entity, ModEffects.TOOTH_DECAY.get(), -1, 3, false, false);
    }
}
