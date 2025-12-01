package xyz.starchenpy.dental_handbook.common.gui;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

import java.util.Map;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class DentureSlot extends SlotItemHandler {
    private final DentureType type;
    private static final ResourceLocation INCISOR_BG = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/empty_tooth_slot_incisor");
    private static final ResourceLocation CANINE_BG = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/empty_tooth_slot_canine");
    private static final ResourceLocation MOLAR_BG = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/empty_tooth_slot_molar");
    private static final ResourceLocation WISDOM_BG = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/empty_tooth_slot_wisdom");
    private static final Map<DentureType, ResourceLocation> TEXTURE_EMPTY_SLOTS = Map.of(
            DentureType.INCISOR, INCISOR_BG,
            DentureType.CANINE, CANINE_BG,
            DentureType.MOLAR, MOLAR_BG,
            DentureType.WISDOM, WISDOM_BG
    );

    public DentureSlot(IItemHandler itemHandler, DentureType type, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.type = type;
    }

    public DentureType getType() {
        return type;
    }

    @Override
    public @Nullable Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS.get(type));
    }
}
