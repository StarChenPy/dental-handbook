package xyz.starchenpy.dental_handbook.common.item.denture.material;

import net.minecraft.world.entity.player.Player;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public abstract class AbstractMaterial {
    private final DentureType[] supportedTypes;
    private final int base_durability;
    private final String name;

    protected AbstractMaterial(String name, int base_durability, DentureType... supportedTypes) {
        this.name = name;
        this.base_durability = base_durability;
        this.supportedTypes = supportedTypes;
    }

    /**
     * 该材料作为门齿的时候，产生的效果
     */
    protected void incisor(Player player) {}

    /**
     * 该材料作为犬齿的时候，产生的效果
     */
    protected void canine(Player player) {}

    /**
     * 该材料作为臼齿的时候，产生的效果
     */
    protected void molar(Player player) {}

    /**
     * 该材料作为智齿的时候，产生的效果
     */
    protected void wisdom(Player player) {}

    public int getDurability(DentureType type) {
        if (type == DentureType.INCISOR) {
            return this.base_durability;
        } else if (type == DentureType.CANINE) {
            return (int) (this.base_durability * 0.9);
        } else if (type == DentureType.MOLAR) {
            return (int) (this.base_durability * 1.2);
        } else if (type == DentureType.WISDOM) {
            return (int) (this.base_durability * 1.2);
        }

        return this.base_durability;
    }

    public void tick(DentureType type, Player player) {
        if (type == DentureType.INCISOR) {
            this.incisor(player);
        } else if (type == DentureType.CANINE) {
            this.canine(player);
        } else if (type == DentureType.MOLAR) {
            this.molar(player);
        } else if (type == DentureType.WISDOM) {
            this.wisdom(player);
        }
    }

    /**
     * 判断该材料是否支持某部位
     * @param dentureType   假牙的部位类型
     * @return  是否支持
     */
    public boolean isSupported(DentureType dentureType) {
        for (DentureType type : supportedTypes) {
            if (dentureType == type) {
                return true;
            }
        }
        return false;
    }

    public DentureType[] getSupportedTypes() {
        return this.supportedTypes;
    }

    public String getName() {
        return name;
    }
}
