package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import xyz.starchenpy.dental_handbook.common.Config;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;

@EventBusSubscriber
public class CleanOralListener {
    @SubscribeEvent
    public static void entityEatFinishListener(LivingEntityUseItemEvent.Finish event) {
        ItemStack food = event.getItem();
        if (food.getFoodProperties(null) == null) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            if (player.getEffect(ModEffects.CLEAN_ORAL) == null) {
                return;
            }

            FoodProperties foodProperties = food.getFoodProperties(player);
            // 已经判断过不为 null 了，这里加断言消除警告
            assert foodProperties != null;
            int nutrition = (int) (foodProperties.nutrition() * (Config.EXTRA_NUTRITION.get() / 100.0f));
            float saturation = foodProperties.saturation() * (Config.EXTRA_SATURATION.get() / 100.0f);
            player.getFoodData().eat(nutrition, saturation);
        }
    }
}
