package xyz.starchenpy.dental_handbook.common.item;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;
import xyz.starchenpy.dental_handbook.common.capability.DentureStorage;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.gui.DentureMenu;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class DentalForceps extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();

    public DentalForceps() {
        super(new Properties().stacksTo(1));
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // 按shift的时候右键打开选择假牙的GUI
            if (player.isShiftKeyDown()) {
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (id, inv, player1) -> new DentureMenu(id, inv, player1, itemInHand),
                        Component.translatable("menu.title.dental_handbook.select_tooth")));
                return InteractionResultHolder.success(itemInHand);
            } else {
                if (this.toothExtraction(player, itemInHand)) {
                    return InteractionResultHolder.success(itemInHand);
                }
            }
        }
        return InteractionResultHolder.pass(itemInHand);
    }

    /**
     * 将指定的牙齿拔出
     * @param player        玩家
     * @param itemInHand    手中的工具
     */
    private boolean toothExtraction(Player player, ItemStack itemInHand) {
        // 工具没选择假牙，跳过
        int slot = NbtUtil.getSelectedDentureSlot(itemInHand);
        if (slot < 0) {
            player.displayClientMessage(Component.literal(" 还未选择牙齿!"), true);
            return false;
        }

        LazyOptional<DentureStorage> capability = player.getCapability(ModCapabilities.DENTURE_CAP);

        if (!capability.isPresent()) {
            LOGGER.error("使用牙科工具时，发现玩家没有假牙能力，无法使用! 这很可能是Mod的Bug!");
            return false;
        }

        Optional<DentureStorage> resolve = capability.resolve();
        if (resolve.isEmpty()) {
            LOGGER.error("使用牙科工具时，发现能力是空的，无法使用! 这很可能是Mod的Bug!");
            return false;
        }

        ItemStackHandler data = resolve.get().getHandler();
        // 选择的假牙槽没有牙，跳过
        if (data.getStackInSlot(slot).isEmpty()) {
            player.displayClientMessage(Component.literal(" 选定的位置没有牙！"), true);
            return false;
        }

        // 拔牙并放入玩家物品栏
        ItemStack itemStack = data.extractItem(slot, 1, false);
        if (!itemStack.isEmpty()) {
            // 尝试放入玩家的物品栏
            boolean added = player.getInventory().add(itemStack);

            // 如果物品栏满了，就扔到玩家脚下
            if (!added) {
                player.drop(itemStack, false);
            }
        }

        return true;
    }
}
