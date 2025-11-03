package xyz.starchenpy.dental_handbook.common.item.toothbrush;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static xyz.starchenpy.dental_handbook.DentalHandbook.MOD_ID;

public class RedstoneToothbrush extends AbstractToothbrush {
    public RedstoneToothbrush(Properties item) {
        super(item, 100);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip." + MOD_ID + ".redstone_toothbrush"));
    }
}
