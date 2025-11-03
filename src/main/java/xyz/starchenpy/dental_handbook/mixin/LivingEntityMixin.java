package xyz.starchenpy.dental_handbook.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);

    @Redirect(method = "shouldTriggerItemUseEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration(Lnet/minecraft/world/entity/LivingEntity;)I"))
    public int getUseDuration(ItemStack instance, LivingEntity entity) {
        int duration = instance.getUseDuration(entity);

        MobEffectInstance effect = getEffect(ModEffects.TOOTH_DECAY);

        if (effect != null) {
            int extraDuration = (int) ((effect.getAmplifier() + 1) * Config.EXTRA_EAT_TIME.get() * 0.85);
            return duration + extraDuration;
        }

        return duration;
    }
}
