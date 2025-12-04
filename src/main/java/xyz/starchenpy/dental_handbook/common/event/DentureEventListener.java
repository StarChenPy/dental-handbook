package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.item.denture.material.IMaterialEventHandler;

@Mod.EventBusSubscriber
public class DentureEventListener {
    // 每 Tick
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        handle(event.player, (mat, type) -> mat.onTick(event.player, type));
    }

    // 进食
    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        handle(player, (mat, type) -> mat.onEat(player, type));
    }

    // 攻击时
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        handle(player, (mat, type) -> mat.onAttack(player, type, event));
    }

    // 被攻击时
    @SubscribeEvent
    public static void onHurt(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        handle(player, (mat, type) -> mat.onHurt(player, type, event));
    }

    /**
     * 通用事件处理
     * @param player    玩家
     * @param action    待传递的事件
     */
    private static void handle(Player player, MaterialAction action) {
        player.getCapability(ModCapabilities.DENTURE_CAP).ifPresent(cap -> {
            ItemStackHandler handler = cap.getHandler();
            // 遍历假牙，触发功能
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (stack.isEmpty()) continue;

                if (!(stack.getItem() instanceof Denture denture)) return;

                action.run(denture.getMaterial(), denture.getType());
            }
        });
    }

    @FunctionalInterface
    private interface MaterialAction {
        void run(IMaterialEventHandler mat, DentureType type);
    }
}
