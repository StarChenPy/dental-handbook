package xyz.starchenpy.dental_handbook.common.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import xyz.starchenpy.dental_handbook.common.ModAttachmentTypes;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class DentureMenu extends AbstractContainerMenu {
    public DentureMenu(int i, Inventory inventory) {
        this(i, inventory, inventory.player);
    }

    public DentureMenu(int i, Inventory inventory, @Nullable Player player) {
        super(ModMenu.DENTURE_MENU.get(), i);

        if (player != null) {
            ItemStackHandler data = player.getData(ModAttachmentTypes.DENTURE);
            for (int l = 0; l < 2; l++) {
                for (int j1 = 0; j1 < 10; j1++) {
                    this.addSlot(new SlotItemHandler(data, j1 + l * 10, 8 + j1 * 18, 10 + l * 18 + i));
                }
            }
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(inventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean stillValid(Player player) {
        return true;
    }
}
