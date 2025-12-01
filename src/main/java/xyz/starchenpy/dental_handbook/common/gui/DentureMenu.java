package xyz.starchenpy.dental_handbook.common.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import xyz.starchenpy.dental_handbook.common.ModAttachmentTypes;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.util.DataComponentUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class DentureMenu extends AbstractContainerMenu {
    private final ItemStack itemInHand;

    public DentureMenu(int i, Inventory inventory) {
        this(i, inventory, inventory.player, null);
    }

    public DentureMenu(int i, Inventory inventory, Player player, ItemStack itemInHand) {
        super(ModMenu.DENTURE_MENU.get(), i);

        this.itemInHand = itemInHand;

        if (player != null) {
            ItemStackHandler data = player.getData(ModAttachmentTypes.DENTURE);
            
            // 门齿
            this.addSlot(new DentureSlot(data, DentureType.INCISOR, 0, 110, 35));
            this.addSlot(new DentureSlot(data, DentureType.INCISOR, 1, 135, 35));
            this.addSlot(new DentureSlot(data, DentureType.INCISOR, 2, 110, 210));
            this.addSlot(new DentureSlot(data, DentureType.INCISOR, 3, 135, 210));

            // 犬齿
            this.addSlot(new DentureSlot(data, DentureType.CANINE, 4, 85, 45));
            this.addSlot(new DentureSlot(data, DentureType.CANINE, 5, 160, 45));
            this.addSlot(new DentureSlot(data, DentureType.CANINE, 6, 85, 200));
            this.addSlot(new DentureSlot(data, DentureType.CANINE, 7, 160, 200));

            // 臼齿
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 8, 65, 85));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 9, 72, 65));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 10, 170, 65));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 11, 178, 85));

            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 12, 65, 160));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 13, 72, 180));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 14, 170, 180));
            this.addSlot(new DentureSlot(data, DentureType.MOLAR, 15, 178, 160));

            // 智齿
            this.addSlot(new DentureSlot(data, DentureType.WISDOM, 16, 60, 105));
            this.addSlot(new DentureSlot(data, DentureType.WISDOM, 17, 183, 105));
            this.addSlot(new DentureSlot(data, DentureType.WISDOM, 18, 60, 140));
            this.addSlot(new DentureSlot(data, DentureType.WISDOM, 19, 183, 140));
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (this.itemInHand == null) return;

        if (slotId >= 0 && slotId < this.slots.size()) {
            DataComponentUtil.setSelectDentureSlot(this.itemInHand, slotId);
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
