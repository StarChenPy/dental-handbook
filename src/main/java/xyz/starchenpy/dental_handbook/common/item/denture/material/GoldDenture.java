package xyz.starchenpy.dental_handbook.common.item.denture.material;

import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public class GoldDenture extends AbstractMaterial {
    protected GoldDenture() {
        super("gold", 200, DentureType.INCISOR, DentureType.CANINE, DentureType.MOLAR);
    }
}