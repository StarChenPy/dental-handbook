package xyz.starchenpy.dental_handbook.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.toothbrush.AbstractToothbrush;
import xyz.starchenpy.dental_handbook.common.item.toothpaste.AbstractToothpaste;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    /**
     * 生成本 Mod 中物品的 Model 文件
     * 解放双手，好耶!
     */
    @Override
    protected void registerModels() {
        for (RegistryObject<Item> supplier : ModItems.getAllModItem()) {
            if (supplier.get() instanceof AbstractToothpaste) {
                // 生成牙膏 Model 文件
                withExistingParent(supplier.getId().toString(), modLoc("item/toothpaste"))
                        .texture("layer0", "item/" + supplier.getId().getPath());
            } else if (supplier.get() instanceof AbstractToothbrush) {
                // 覆盖层生成
                ItemModelBuilder overlay = withExistingParent(supplier.getId() + "_overlay", modLoc("item/toothbrush"))
                        .texture("layer0", "item/" + supplier.getId().getPath())
                        .texture("layer1", "item/toothbrush_overlay");

                // 生成牙刷 Model 文件
                withExistingParent(supplier.getId().toString(), modLoc("item/toothbrush"))
                        .texture("layer0", "item/" + supplier.getId().getPath())
                        .override()
                        .predicate(ResourceLocation.fromNamespaceAndPath(MOD_ID, "has_toothpaste"), 1)
                        .model(overlay);
            } else {
                // 其它物品
                basicItem(supplier.get());
            }
        }
    }
}
