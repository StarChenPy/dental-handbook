package xyz.starchenpy.dental_handbook.common.item.denture;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import xyz.starchenpy.dental_handbook.common.item.denture.material.AbstractMaterial;

public class Denture extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final DentureType type;
    private final AbstractMaterial material;

    public Denture(DentureType type, AbstractMaterial material) {
        this(getItemProp(material.getDurability(type)), type, material);
    }

    /**
     * 假牙物品，有4个部位，其效果由材料决定。
     * @param item  物品属性
     * @param type  假牙部位
     * @param material  假牙材料
     */
    public Denture(Properties item, DentureType type, AbstractMaterial material) {
        super(item);
        this.type = type;
        this.material = material;

        if (!this.material.isSupported(this.type)) {
            LOGGER.warn("给假牙 {} 传入了无效的材料: {}, 它似乎不支持 {}, 这个假牙不会有效果的.", this.getDescriptionId(), this.material, this.type);
        }
    }

    /**
     * 触发假牙效果
     * @param player    玩家
     */
    public void tick(Player player) {
        // 考虑到性能问题，将校验挪至构造函数，这里不做校验了
        this.material.tick(this.type, player);
    }

    private static Properties getItemProp(int maxDamage) {
        if (maxDamage > 0) {
            return new Properties().durability(maxDamage);
        }
        return new Properties().stacksTo(1);
    }
}
