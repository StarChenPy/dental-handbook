package xyz.starchenpy.dental_handbook.client.event;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    /**
     * 为牙刷添加 property, 以控制牙膏显示与否
     */
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<Item> item : ModItems.getAllToothbrush()) {
                ItemProperties.register(item.get(), ResourceLocation.fromNamespaceAndPath(MOD_ID,"has_toothpaste"),
                        (stack, level, player, send) -> NbtUtil.getToothpaste(stack) instanceof AbstractToothpaste ? 1 : 0);
            }
        });
    }
}