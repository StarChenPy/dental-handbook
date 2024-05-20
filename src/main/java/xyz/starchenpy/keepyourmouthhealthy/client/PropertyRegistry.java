package xyz.starchenpy.keepyourmouthhealthy.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.keepyourmouthhealthy.common.item.ModItems;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothbrush.AbstractToothbrush;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothpaste.AbstractToothpaste;
import xyz.starchenpy.keepyourmouthhealthy.common.util.NbtUtil;

import static xyz.starchenpy.keepyourmouthhealthy.KeepYourMouthHealthy.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class PropertyRegistry {
    /**
     * 为牙刷添加 property, 以控制牙膏显示与否
     */
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            for (RegistryObject<Item> holder : ModItems.getAllModItem()) {
                if (holder.get().asItem() instanceof AbstractToothbrush toothbrush) {
                    ItemProperties.register(toothbrush, new ResourceLocation(MOD_ID,"has_toothpaste"),
                            (stack, level, player, send)-> NbtUtil.getToothpaste(stack) instanceof AbstractToothpaste ? 1 : 0);

                }
            }
        });
    }
}