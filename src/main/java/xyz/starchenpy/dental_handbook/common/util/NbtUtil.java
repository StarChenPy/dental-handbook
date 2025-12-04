package xyz.starchenpy.dental_handbook.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NbtUtil {
    public static final String TOOTHBRUSH_TOOTHPASTE_NBT = "toothpaste";
    public static final String SELECTED_DENTURE_SLOT_NBT = "selected_denture_slot";

    /**
     * 从 NBT 获取牙膏
     * @param itemStack 牙刷 ItemStack
     * @return 牙膏
     */
    public static Item getToothpaste(ItemStack itemStack) {
        if (!itemStack.getOrCreateTag().contains(TOOTHBRUSH_TOOTHPASTE_NBT)) {
            return null;
        }

        String toothpasteName = itemStack.getOrCreateTag().getString(TOOTHBRUSH_TOOTHPASTE_NBT);
        if (toothpasteName.isEmpty()) {
            return null;
        }

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(toothpasteName));
    }

    /**
     * 将牙膏添加到 NBT 中
     * @param itemStack 牙刷 ItemStack
     * @param toothpaste 牙膏
     */
    public static void setToothpaste(ItemStack itemStack, Item toothpaste) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (toothpaste == null) {
            tag.putString(TOOTHBRUSH_TOOTHPASTE_NBT, "");
        } else {
            tag.putString(TOOTHBRUSH_TOOTHPASTE_NBT, BuiltInRegistries.ITEM.getKey(toothpaste).toString());
        }
    }

    /**
     * 从 NBT 获取选择的假牙槽
     * @param itemStack 牙科工具的 ItemStack
     * @return  槽位
     */
    public static int getSelectedDentureSlot(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();

        return itemStack.getOrCreateTag().getInt(SELECTED_DENTURE_SLOT_NBT);
    }

    /**
     * 从 DataComponent 设置选择的假牙槽
     * @param itemStack 牙科工具的 ItemStack
     */
    public static void setSelectDentureSlot(ItemStack itemStack, int slot) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt(SELECTED_DENTURE_SLOT_NBT, slot);
    }
}
