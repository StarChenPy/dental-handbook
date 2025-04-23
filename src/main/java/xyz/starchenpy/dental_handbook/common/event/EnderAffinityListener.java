package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

@Mod.EventBusSubscriber
public class EnderAffinityListener {
    @SubscribeEvent
    public static void enderManAngerEventListener(EnderManAngerEvent event) {
        MobEffectInstance effect = event.getPlayer().getEffect(ModEffects.ENDER_AFFINITY.get());

        if (effect != null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void enderPearlLand(EntityTeleportEvent.EnderPearl event) {
        MobEffectInstance effect = event.getPlayer().getEffect(ModEffects.ENDER_AFFINITY.get());

        if (effect != null) {
            event.setAttackDamage(0);
        }
    }
}
