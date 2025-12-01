package xyz.starchenpy.dental_handbook.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.starchenpy.dental_handbook.common.ModAttachmentTypes;
import xyz.starchenpy.dental_handbook.common.item.denture.Denture;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;
import xyz.starchenpy.dental_handbook.common.item.denture.material.AbstractMaterial;
import xyz.starchenpy.dental_handbook.common.item.denture.material.IMaterialEventHandler;

@EventBusSubscriber
public class DentureEventListener {
    // 每 Tick
    @SubscribeEvent
    public static void onTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        handle(player, (mat, type) -> mat.onTick(player, type));
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
    public static void onHurt(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        handle(player, (mat, type) -> mat.onHurt(player, type, event));
    }

    /**
     * 通用事件处理
     * @param player    玩家
     * @param action    待传递的事件
     */
    private static void handle(ServerPlayer player, MaterialAction action) {
        ItemStackHandler handler = player.getData(ModAttachmentTypes.DENTURE.get());
        // 遍历假牙，触发功能
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (!(stack.getItem() instanceof Denture denture)) return;

            action.run(denture.getMaterial(), denture.getType());
        }
    }

    @FunctionalInterface
    private interface MaterialAction {
        void run(IMaterialEventHandler mat, DentureType type);
    }
}
