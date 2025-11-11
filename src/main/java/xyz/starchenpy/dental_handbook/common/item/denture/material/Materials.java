package xyz.starchenpy.dental_handbook.common.item.denture.material;

import java.util.ArrayList;
import java.util.List;

public class Materials {
    private static final ArrayList<AbstractMaterial> MATERIALS = new ArrayList<>();

    static {
        MATERIALS.add(new IronDenture());
    }

    public static List<AbstractMaterial> getMaterials() {
        return MATERIALS;
    }
}
