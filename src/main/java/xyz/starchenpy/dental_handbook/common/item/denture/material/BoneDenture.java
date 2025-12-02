package xyz.starchenpy.dental_handbook.common.item.denture.material;

import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public class BoneDenture extends AbstractMaterial {
    public BoneDenture() {
        super("bone", 200, DentureType.INCISOR, DentureType.CANINE, DentureType.MOLAR);
    }
}
