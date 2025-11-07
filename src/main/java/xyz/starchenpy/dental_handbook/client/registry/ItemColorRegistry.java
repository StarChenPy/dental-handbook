package xyz.starchenpy.dental_handbook.client.registry;

import net.minecraft.client.color.item.ItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.util.DataComponentUtil;

@EventBusSubscriber(Dist.CLIENT)
public class ItemColorRegistry {

    /**
     * 注册牙膏的颜色
     * @param event 染色事件
     */
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColor color = (stack, tintIndex) -> {
            if (tintIndex == 1) {
                if (DataComponentUtil.getToothpaste(stack) instanceof AbstractToothpaste toothpaste) {
                    return toothpaste.getColor();
                }
            }
            return -1;
        };

        event.register(color, ModItems.getAllToothbrush());
    }
}
