package xyz.starchenpy.dental_handbook.common.item.denture.material;

import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public abstract class AbstractMaterial implements IMaterialEventHandler {
    private final DentureType[] supportedTypes;
    private final int base_durability;
    private final String name;

    protected AbstractMaterial(String name, int base_durability, DentureType... supportedTypes) {
        this.name = name;
        this.base_durability = base_durability;
        this.supportedTypes = supportedTypes;
    }

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
