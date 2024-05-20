package xyz.starchenpy.keepyourmouthhealthy.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.keepyourmouthhealthy.common.item.ModItems;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothbrush.AbstractToothbrush;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.keepyourmouthhealthy.common.util.NbtUtil;

import static xyz.starchenpy.keepyourmouthhealthy.KeepYourMouthHealthy.MOD_ID;

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

        for (RegistryObject<Item> holder : ModItems.getAllModItem()) {
            if (holder.get().asItem() instanceof AbstractToothbrush toothbrush) {
                event.register(color, toothbrush);
            }
        }
    }
}
