package xyz.starchenpy.dental_handbook.common.item;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;
import xyz.starchenpy.dental_handbook.common.ModDamageType;
import xyz.starchenpy.dental_handbook.common.capability.DentureStorage;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.effect.ModEffects;
import xyz.starchenpy.dental_handbook.common.gui.DentureMenu;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.util.CapabilityUtil;
import xyz.starchenpy.dental_handbook.common.util.NbtUtil;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class DentalHammer extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();

    public DentalHammer() {
        super(new Properties().stacksTo(1));
    }

    /**
     * 使用自定义动画应返回 CUSTOM
     */
    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.EAT;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getUseDuration(ItemStack itemStack) {
        return 40;
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
                        (id, inv, p) -> new DentureMenu(id, inv, p, itemInHand),
                        Component.translatable("menu.title.dental_handbook.select_tooth")));
                return InteractionResultHolder.success(itemInHand);
            } else {
                if (this.verifyToothImplantation(player, usedHand)) {
                    player.startUsingItem(usedHand);
                    return InteractionResultHolder.pass(itemInHand);
                }
            }
        }
        return InteractionResultHolder.fail(itemInHand);
    }

    /**
     * 将另一只手中的牙齿植入到口腔
     * @param player    玩家
     * @param usedHand  使用工具的手
     */
    private boolean verifyToothImplantation(Player player, InteractionHand usedHand) {
        ItemStack inUseDentalTool = player.getItemInHand(usedHand);
        // 取相反的手中的假牙
        InteractionHand dentureHead = usedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack denture = player.getItemInHand(dentureHead);

        // 工具没选择假牙，跳过
        int slot = NbtUtil.getSelectedDentureSlot(inUseDentalTool);
        if (slot < 0) {
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_hammer.no_select_tooth"), true);
            return false;
        }

        // 另一只手没拿假牙，跳过
        if (denture.isEmpty()) {
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_hammer.need_tooth"), true);
            return false;
        }
        if (!(denture.getItem() instanceof Denture)) return false;

        if (DentureMenu.getSlotDentureType(slot) != ((Denture) denture.getItem()).getType()) {
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_hammer.unhappy"), true);
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
            player.displayClientMessage(Component.translatable("message.dental_handbook.dental_hammer.already_exists"), true);
            return false;
        }

        return true;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            // 取相反的手中的假牙
            InteractionHand dentureHead = player.getUsedItemHand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack denture = player.getItemInHand(dentureHead);
            int slot = NbtUtil.getSelectedDentureSlot(pStack);

            ItemStackHandler data = CapabilityUtil.getDenture(player);
            if (data == null) return super.finishUsingItem(pStack, pLevel, pLivingEntity);

            data.insertItem(slot, denture.copy(), false);
            if (!player.getAbilities().instabuild) {
                denture.shrink(1);
                player.hurt(player.damageSources().source(ModDamageType.ORAL_BLEEDING), 6);
                player.addEffect(new MobEffectInstance(ModEffects.INJURY_ORAL.get(), 1800));
            }

            player.getCooldowns().addCooldown(this, 10);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
