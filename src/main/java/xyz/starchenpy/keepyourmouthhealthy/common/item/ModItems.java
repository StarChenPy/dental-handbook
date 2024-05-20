package xyz.starchenpy.keepyourmouthhealthy.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothbrush.RedstoneToothbrush;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothbrush.WoodenToothbrush;
import xyz.starchenpy.keepyourmouthhealthy.common.item.toothpaste.*;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collection;

import static xyz.starchenpy.keepyourmouthhealthy.KeepYourMouthHealthy.MOD_ID;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    // 牙刷
    public static final RegistryObject<Item> WOODEN_TOOTHBRUSH = ITEMS.register("wooden_toothbrush", WoodenToothbrush::new);
    public static final RegistryObject<Item> REDSTONE_TOOTHBRUSH = ITEMS.register("redstone_toothbrush", RedstoneToothbrush::new);

    // 牙膏
    public static final RegistryObject<Item> CHARCOAL_TOOTHPASTE = ITEMS.register("charcoal_toothpaste", CharcoalToothpaste::new);
    public static final RegistryObject<Item> FLINT_TOOTHPASTE = ITEMS.register("flint_toothpaste", FlintToothpaste::new);
    public static final RegistryObject<Item> QUARTZ_TOOTHPASTE = ITEMS.register("quartz_toothpaste", QuartzToothpaste::new);
    public static final RegistryObject<Item> REDSTONE_TOOTHPASTE = ITEMS.register("redstone_toothpaste", RedstoneToothpaste::new);
    public static final RegistryObject<Item> LAPIS_LAZULI_TOOTHPASTE = ITEMS.register("lapis_lazuli_toothpaste", LapisLazuliToothpaste::new);
    public static final RegistryObject<Item> GOLDEN_APPLE_TOOTHPASTE = ITEMS.register("golden_apple_toothpaste", GoldenAppleToothpaste::new);

    // 材料
    public static final RegistryObject<Item> BRISTLE = ITEMS.register("bristle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTHBRUSH_HEAD = ITEMS.register("toothbrush_head", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTHPASTE_TUBE = ITEMS.register("toothpaste_tube", () -> new Item(new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static Collection<RegistryObject<Item>> getAllModItem() {
        return ITEMS.getEntries();
    }
}
