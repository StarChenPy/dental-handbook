package xyz.starchenpy.dental_handbook.common.item.denture.material;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public interface IMaterialEventHandler {
    /**
     * 玩家每Tick触发该方法
     * @param player    玩家
     * @param type  牙齿类型
     */
    default void onTick(Player player, DentureType type) {}

    /**
     * 玩家进食时触发该方法
     * @param player    玩家
     * @param type  牙齿类型
     */
    default void onEat(Player player, DentureType type) {}

    /**
     * 玩家攻击时触发该方法
     * @param player    玩家
     * @param type  牙齿类型
     * @param event 攻击事件
     */
    default void onAttack(Player player, DentureType type, AttackEntityEvent event) {}

    /**
     * 玩家被攻击时触发该方法
     * @param player    玩家
     * @param type  牙齿类型
     * @param event 被攻击事件
     */
    default void onHurt(Player player, DentureType type, LivingDamageEvent event) {}
}
