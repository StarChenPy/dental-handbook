package xyz.starchenpy.dental_handbook.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.item.denture.material.AbstractMaterial;
import xyz.starchenpy.dental_handbook.common.item.denture.material.Materials;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.RedstoneToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.WoodenToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    private static final ArrayList<RegistryObject<Item>> TOOTHBRUSHES = new ArrayList<>();

    // 牙刷
    public static final RegistryObject<Item> WOODEN_TOOTHBRUSH = registerToothbrush("wooden_toothbrush", WoodenToothbrush::new);
    public static final RegistryObject<Item> REDSTONE_TOOTHBRUSH = registerToothbrush("redstone_toothbrush", RedstoneToothbrush::new);

    // 牙膏
    public static final RegistryObject<Item> CHARCOAL_TOOTHPASTE = ITEMS.register("charcoal_toothpaste", CharcoalToothpaste::new);
    public static final RegistryObject<Item> FLINT_TOOTHPASTE = ITEMS.register("flint_toothpaste", FlintToothpaste::new);
    public static final RegistryObject<Item> QUARTZ_TOOTHPASTE = ITEMS.register("quartz_toothpaste", QuartzToothpaste::new);
    public static final RegistryObject<Item> REDSTONE_TOOTHPASTE = ITEMS.register("redstone_toothpaste", RedstoneToothpaste::new);
    public static final RegistryObject<Item> LAPIS_LAZULI_TOOTHPASTE = ITEMS.register("lapis_lazuli_toothpaste", LapisLazuliToothpaste::new);
    public static final RegistryObject<Item> GOLDEN_APPLE_TOOTHPASTE = ITEMS.register("golden_apple_toothpaste", GoldenAppleToothpaste::new);
    public static final RegistryObject<Item> ENDER_TOOTHPASTE = ITEMS.register("ender_toothpaste", EnderToothpaste::new);
    public static final RegistryObject<Item> BLAZE_TOOTHPASTE = ITEMS.register("blaze_toothpaste", BlazeToothpaste::new);

    // 工具
    public static final RegistryObject<Item> DENTAL_FORCEPS = ITEMS.register("dental_forceps", DentalForceps::new);
    public static final RegistryObject<Item> DENTAL_HAMMER = ITEMS.register("dental_hammer", DentalHammer::new);

    // 材料
    public static final RegistryObject<Item> BRISTLE = ITEMS.register("bristle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTHBRUSH_HEAD = ITEMS.register("toothbrush_head", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTHPASTE_TUBE = ITEMS.register("toothpaste_tube", () -> new Item(new Item.Properties()));
    // 牙
    public static final RegistryObject<Item> INCISOR = ITEMS.register("incisor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANINE = ITEMS.register("canine", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOLAR = ITEMS.register("molar", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WISDOM = ITEMS.register("wisdom", () -> new Item(new Item.Properties()));

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
     * @param sup  创建物品的接口
     * @return  注册出的物品的Holder
     */
    public static RegistryObject<Item> registerToothbrush(String name, final Supplier<? extends Item> sup) {
        RegistryObject<Item> toothbrush = ITEMS.register(name, sup);
        TOOTHBRUSHES.add(toothbrush);
        return toothbrush;
    }

    public static Collection<RegistryObject<Item>> getAllToothbrush() {
        return TOOTHBRUSHES;
    }

    public static void register(IEventBus modEventBus) {
        registerDenture();

        ITEMS.register(modEventBus);
    }

    public static Collection<RegistryObject<Item>> getAllModItem() {
        return ITEMS.getEntries();
    }
}
