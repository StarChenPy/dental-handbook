package xyz.starchenpy.dental_handbook.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.AbstractToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ItemColorRegistry {
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColor color = (stack, tintIndex) -> {
            if (tintIndex == 1) {
                if (NbtUtil.getToothpaste(stack) instanceof AbstractToothpaste toothpaste) {
                    return toothpaste.getColor();
                }
            }
            return -1;
        };

        for (DeferredHolder<Item, ? extends Item> holder : ModItems.getAllModItem()) {
            if (holder.get().asItem() instanceof AbstractToothbrush toothbrush) {
                event.register(color, toothbrush);
            }
        }
    }
}
