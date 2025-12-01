package xyz.starchenpy.dental_handbook.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.starchenpy.dental_handbook.common.item.ModItems;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> holder : ModItems.getAllModItem()) {
            if (holder.get() instanceof Denture denture) {
                basicItem(denture);
                System.out.println("Gen Item ID: " + holder.get().getDescriptionId());
            }
        }
    }
}
