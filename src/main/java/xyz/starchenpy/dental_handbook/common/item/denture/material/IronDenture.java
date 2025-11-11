package xyz.starchenpy.dental_handbook.common.item.denture.material;

import net.minecraft.world.entity.player.Player;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public class IronDenture extends AbstractMaterial {
    public IronDenture() {
        super("iron", 250, DentureType.INCISOR, DentureType.CANINE, DentureType.MOLAR);
    }

    @Override
    protected void incisor(Player player) {
        super.incisor(player);
    }

    @Override
    protected void canine(Player player) {
        super.canine(player);
    }

    @Override
    protected void molar(Player player) {
        super.molar(player);
    }
}
