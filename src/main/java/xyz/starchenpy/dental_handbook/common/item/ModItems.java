package xyz.starchenpy.dental_handbook.common.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.item.denture.material.AbstractMaterial;
import xyz.starchenpy.dental_handbook.common.item.denture.material.Materials;
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

    // 牙刷
    public static final DeferredItem<Item> WOODEN_TOOTHBRUSH = registerToothbrush("wooden_toothbrush", WoodenToothbrush::new, 80);
    public static final DeferredItem<Item> REDSTONE_TOOTHBRUSH = registerToothbrush("redstone_toothbrush", RedstoneToothbrush::new, 200);

    // 牙膏
    public static final DeferredItem<Item> CHARCOAL_TOOTHPASTE = ITEMS.registerItem("charcoal_toothpaste", CharcoalToothpaste::new, new Item.Properties().durability(16));
    public static final DeferredItem<Item> FLINT_TOOTHPASTE = ITEMS.registerItem("flint_toothpaste", FlintToothpaste::new, new Item.Properties().durability(12));
    public static final DeferredItem<Item> QUARTZ_TOOTHPASTE = ITEMS.registerItem("quartz_toothpaste", QuartzToothpaste::new, new Item.Properties().durability(12));
    public static final DeferredItem<Item> REDSTONE_TOOTHPASTE = ITEMS.registerItem("redstone_toothpaste", RedstoneToothpaste::new, new Item.Properties().durability(12));
    public static final DeferredItem<Item> LAPIS_LAZULI_TOOTHPASTE = ITEMS.registerItem("lapis_lazuli_toothpaste", LapisLazuliToothpaste::new, new Item.Properties().durability(12));
    public static final DeferredItem<Item> GOLDEN_APPLE_TOOTHPASTE = ITEMS.registerItem("golden_apple_toothpaste", GoldenAppleToothpaste::new, new Item.Properties().durability(8));
    public static final DeferredItem<Item> ENDER_TOOTHPASTE = ITEMS.registerItem("ender_toothpaste", EnderToothpaste::new, new Item.Properties().durability(12));
    public static final DeferredItem<Item> BLAZE_TOOTHPASTE = ITEMS.registerItem("blaze_toothpaste", BlazeToothpaste::new, new Item.Properties().durability(10));

    // 工具
    public static final DeferredItem<Item> DENTAL_FORCEPS = ITEMS.registerItem("dental_forceps", DentalForceps::new, new Item.Properties().durability(40));
    public static final DeferredItem<Item> DENTAL_HAMMER = ITEMS.registerItem("dental_hammer", DentalHammer::new, new Item.Properties().durability(40));

    // 材料
    public static final DeferredItem<Item> BRISTLE = ITEMS.registerSimpleItem("bristle");
    public static final DeferredItem<Item> TOOTHBRUSH_HEAD = ITEMS.registerSimpleItem("toothbrush_head");
    public static final DeferredItem<Item> TOOTHPASTE_TUBE = ITEMS.registerSimpleItem("toothpaste_tube");
    // 牙
    public static final DeferredItem<Item> INCISOR = ITEMS.registerSimpleItem("incisor");
    public static final DeferredItem<Item> CANINE = ITEMS.registerSimpleItem("canine");
    public static final DeferredItem<Item> MOLAR = ITEMS.registerSimpleItem("molar");
    public static final DeferredItem<Item> WISDOM = ITEMS.registerSimpleItem("wisdom");

    /**
     * 自动注册假牙
     */
    private static void registerDenture() {
        for (AbstractMaterial material : Materials.getMaterials()) {
            for (DentureType type : material.getSupportedTypes()) {
                String name = material.getName() + "_denture_" + type;
                ITEMS.register(name, () -> new Denture(type, material));
            }
        }
    }

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

    public static void register(IEventBus modEventBus) {
        registerDenture();

        ITEMS.register(modEventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getAllModItem() {
        return ITEMS.getEntries();
    }
}
