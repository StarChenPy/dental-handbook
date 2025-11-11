package xyz.starchenpy.dental_handbook.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.starchenpy.dental_handbook.common.gui.DentureMenu;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class DentalForceps extends Item {
    public DentalForceps(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(DentureMenu::new,
                    Component.translatable("menu.title.dental_handbook.select_tooth")));
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
