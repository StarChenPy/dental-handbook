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
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class DentalHammer extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();

    public DentalHammer() {
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
                if (this.toothImplantation(player, usedHand)) {
                    return InteractionResultHolder.success(itemInHand);
                }
            }
        }
        return InteractionResultHolder.pass(itemInHand);
    }

    /**
     * 将另一只手中的牙齿植入到口腔
     * @param player    玩家
     * @param usedHand  使用工具的手
     */
    private boolean toothImplantation(Player player, InteractionHand usedHand) {
        ItemStack inUseDentalTool = player.getItemInHand(usedHand);
        // 取相反的手中的假牙
        InteractionHand dentureHead = usedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack denture = player.getItemInHand(dentureHead);

        // 另一只手没拿假牙，跳过
        if (denture.isEmpty()) return false;
        if (!(denture.getItem() instanceof Denture)) return false;

        // 工具没选择假牙，跳过
        int slot = NbtUtil.getSelectedDentureSlot(inUseDentalTool);
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

        // 选择的假牙槽已经有牙，跳过
        if (!data.getStackInSlot(slot).isEmpty()) {
            player.displayClientMessage(Component.literal(" 选定的位置有一颗牙了!"), true);
            return false;
        }

        data.insertItem(slot, denture.copy(), false);
        if (!player.getAbilities().instabuild) {
            denture.shrink(1);
        }

        return true;
    }
}
