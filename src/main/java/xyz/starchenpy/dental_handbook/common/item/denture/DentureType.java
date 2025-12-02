package xyz.starchenpy.dental_handbook.common.item.denture;

/**
 * 假牙的种类
 */
public enum DentureType {
    /**
     * 门齿
     */
    INCISOR,

    /**
     * 犬齿
     */
    CANINE,

    /**
     * 臼齿
     */
    MOLAR,

    /**
     * 智齿
     */
    WISDOM;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
