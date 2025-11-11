package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.starchenpy.dental_handbook.common.ModAttachmentTypes;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;

@EventBusSubscriber
public class DentureTickListener {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide()) return;

        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStackHandler handler = player.getData(ModAttachmentTypes.DENTURE.get());
        // 遍历假牙，触发功能
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (!(stack.getItem() instanceof Denture denture)) return;

            denture.tick(player);
        }
    }
}
