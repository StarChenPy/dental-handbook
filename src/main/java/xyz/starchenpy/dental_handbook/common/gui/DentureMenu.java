package xyz.starchenpy.dental_handbook.common.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class DentureMenu extends AbstractContainerMenu {
    private final ItemStack itemInHand;
    private static final DentureType[] slotDentureType = {
            DentureType.INCISOR, DentureType.INCISOR, DentureType.INCISOR, DentureType.INCISOR,
            DentureType.CANINE, DentureType.CANINE, DentureType.CANINE, DentureType.CANINE,
            DentureType.MOLAR, DentureType.MOLAR, DentureType.MOLAR, DentureType.MOLAR,
            DentureType.MOLAR, DentureType.MOLAR, DentureType.MOLAR, DentureType.MOLAR,
            DentureType.WISDOM, DentureType.WISDOM, DentureType.WISDOM, DentureType.WISDOM
    };

    public DentureMenu(int i, Inventory inventory) {
        this(i, inventory, inventory.player, null);
    }

    public DentureMenu(int i, Inventory inventory, Player player, ItemStack itemInHand) {
        super(ModMenus.DENTURE_MENU.get(), i);

        this.itemInHand = itemInHand;

        if (player != null) {
            player.getCapability(ModCapabilities.DENTURE_CAP).ifPresent(cap -> {
                ItemStackHandler data = cap.getHandler();

                // 门齿
                this.addSlot(new DentureSlot(data, slotDentureType[0], 0, 110, 35));
                this.addSlot(new DentureSlot(data, slotDentureType[1], 1, 135, 35));
                this.addSlot(new DentureSlot(data, slotDentureType[2], 2, 110, 210));
                this.addSlot(new DentureSlot(data, slotDentureType[3], 3, 135, 210));

                // 犬齿
                this.addSlot(new DentureSlot(data, slotDentureType[4], 4, 85, 45));
                this.addSlot(new DentureSlot(data, slotDentureType[5], 5, 160, 45));
                this.addSlot(new DentureSlot(data, slotDentureType[6], 6, 85, 200));
                this.addSlot(new DentureSlot(data, slotDentureType[7], 7, 160, 200));

                // 臼齿
                this.addSlot(new DentureSlot(data, slotDentureType[8], 8, 65, 85));
                this.addSlot(new DentureSlot(data, slotDentureType[9], 9, 72, 65));
                this.addSlot(new DentureSlot(data, slotDentureType[10], 10, 170, 65));
                this.addSlot(new DentureSlot(data, slotDentureType[11], 11, 178, 85));

                this.addSlot(new DentureSlot(data, slotDentureType[12], 12, 65, 160));
                this.addSlot(new DentureSlot(data, slotDentureType[13], 13, 72, 180));
                this.addSlot(new DentureSlot(data, slotDentureType[14], 14, 170, 180));
                this.addSlot(new DentureSlot(data, slotDentureType[15], 15, 178, 160));

                // 智齿
                this.addSlot(new DentureSlot(data, slotDentureType[16], 16, 60, 105));
                this.addSlot(new DentureSlot(data, slotDentureType[17], 17, 183, 105));
                this.addSlot(new DentureSlot(data, slotDentureType[18], 18, 60, 140));
                this.addSlot(new DentureSlot(data, slotDentureType[19], 19, 183, 140));
            });
        }
    }

    /**
     * 获取对应槽位的牙齿类型
     * @param slot  Slot Id
     * @return  假牙类型
     */
    public static DentureType getSlotDentureType(int slot) {
        return slotDentureType[slot];
    }

    @Override
    @ParametersAreNonnullByDefault
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (this.itemInHand == null) return;

        if (slotId >= 0 && slotId < this.slots.size()) {
            NbtUtil.setSelectDentureSlot(this.itemInHand, slotId);
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
