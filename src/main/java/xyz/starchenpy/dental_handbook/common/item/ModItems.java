package xyz.starchenpy.dental_handbook.common.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.starchenpy.dental_handbook.common.item.tool.DentalForceps;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.RedstoneToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.WoodenToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final ArrayList<DeferredItem<Item>> TOOTHBRUSHES = new ArrayList<>();
    private static final ArrayList<DeferredItem<Item>> TOOTHPASTES = new ArrayList<>();

    // 牙刷
    public static final DeferredItem<Item> WOODEN_TOOTHBRUSH = registerToothbrush("wooden_toothbrush", WoodenToothbrush::new, 160);
    public static final DeferredItem<Item> REDSTONE_TOOTHBRUSH = registerToothbrush("redstone_toothbrush", RedstoneToothbrush::new, 200);

    // 牙膏
    public static final DeferredItem<Item> CHARCOAL_TOOTHPASTE = registerToothpaste("charcoal_toothpaste", CharcoalToothpaste::new, 16);
    public static final DeferredItem<Item> FLINT_TOOTHPASTE = registerToothpaste("flint_toothpaste", FlintToothpaste::new, 12);
    public static final DeferredItem<Item> QUARTZ_TOOTHPASTE = registerToothpaste("quartz_toothpaste", QuartzToothpaste::new, 12);
    public static final DeferredItem<Item> REDSTONE_TOOTHPASTE = registerToothpaste("redstone_toothpaste", RedstoneToothpaste::new, 12);
    public static final DeferredItem<Item> LAPIS_LAZULI_TOOTHPASTE = registerToothpaste("lapis_lazuli_toothpaste", LapisLazuliToothpaste::new, 12);
    public static final DeferredItem<Item> GOLDEN_APPLE_TOOTHPASTE = registerToothpaste("golden_apple_toothpaste", GoldenAppleToothpaste::new, 8);
    public static final DeferredItem<Item> ENDER_TOOTHPASTE = registerToothpaste("ender_toothpaste", EnderToothpaste::new, 12);
    public static final DeferredItem<Item> BLAZE_TOOTHPASTE = registerToothpaste("blaze_toothpaste", BlazeToothpaste::new, 10);

    // 工具
    public static final DeferredItem<Item> DENTAL_FORCEPS = ITEMS.registerItem("dental_forceps", DentalForceps::new, new Item.Properties().durability(8));

    // 材料
    public static final DeferredItem<Item> BRISTLE = ITEMS.registerSimpleItem("bristle");
    public static final DeferredItem<Item> TOOTHBRUSH_HEAD = ITEMS.registerSimpleItem("toothbrush_head");
    public static final DeferredItem<Item> TOOTHPASTE_TUBE = ITEMS.registerSimpleItem("toothpaste_tube");

    /**
     * 用于注册牙刷
     * @param name  id
     * @param func  牙刷类
     * @param durability    耐久
     * @return  注册出的物品的Holder
     */
    public static DeferredItem<Item> registerToothbrush(String name, Function<Item.Properties, Item> func, int durability) {
        DeferredItem<Item> toothbrush = ITEMS.registerItem(name, func, new Item.Properties().durability(durability));
        TOOTHBRUSHES.add(toothbrush);
        return toothbrush;
    }

    public static DeferredItem<?>[] getAllToothbrush() {
        return TOOTHBRUSHES.toArray(new DeferredItem[0]);
    }

    /**
     * 用于注册牙膏
     * @param name  id
     * @param func  牙膏类
     * @param durability    耐久
     * @return  注册出的物品的Holder
     */
    public static DeferredItem<Item> registerToothpaste(String name, Function<Item.Properties, Item> func, int durability) {
        DeferredItem<Item> toothpaste = ITEMS.registerItem(name, func, new Item.Properties().durability(durability));
        TOOTHPASTES.add(toothpaste);
        return toothpaste;
    }

    public static DeferredItem<?>[] getAllToothpaste() {
        return TOOTHPASTES.toArray(new DeferredItem[0]);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getAllModItem() {
        return ITEMS.getEntries();
    }
}
