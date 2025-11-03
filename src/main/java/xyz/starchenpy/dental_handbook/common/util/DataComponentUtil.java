package xyz.starchenpy.dental_handbook.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.starchenpy.dental_handbook.common.data_component.ModDataComponents;
import xyz.starchenpy.dental_handbook.common.data_component.ToothpasteRecord;

public class DataComponentUtil {
    /**
     * 从 DataComponent 获取牙膏
     * @param itemStack 牙刷 ItemStack
     * @return 牙膏
     */
    public static Item getToothpaste(ItemStack itemStack) {
        ToothpasteRecord toothpaste = itemStack.get(ModDataComponents.BASIC_EXAMPLE);
        if (toothpaste == null) {
            return null;
        }

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(toothpaste.toothpasteName()));
    }

    /**
     * 将牙膏添加到 DataComponent 中
     * @param itemStack 牙刷 ItemStack
     * @param toothpaste 牙膏
     */
    public static void setToothpaste(ItemStack itemStack, Item toothpaste) {
        String toothpasteNamespace = BuiltInRegistries.ITEM.getKey(toothpaste).toString();
        itemStack.set(ModDataComponents.BASIC_EXAMPLE, new ToothpasteRecord(toothpasteNamespace));
    }

    /**
     * 将牙膏从某物品上移除
     * @param itemStack 要移除牙膏的物品
     */
    public static void removeToothpaste(ItemStack itemStack) {
        itemStack.remove(ModDataComponents.BASIC_EXAMPLE);
    }
}
