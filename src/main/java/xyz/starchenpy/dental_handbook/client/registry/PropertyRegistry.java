package xyz.starchenpy.dental_handbook.client.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.util.DataComponentUtil;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

@EventBusSubscriber(Dist.CLIENT)
public class PropertyRegistry {
    /**
     * 为牙刷添加 property, 以控制牙膏显示与否
     */
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (DeferredItem<?> item : ModItems.getAllToothbrush()) {
                ItemProperties.register(item.get(), ResourceLocation.fromNamespaceAndPath(MOD_ID,"has_toothpaste"),
                        (stack, level, player, send) -> DataComponentUtil.getToothpaste(stack) instanceof AbstractToothpaste ? 1 : 0);
            }
        });
    }
}