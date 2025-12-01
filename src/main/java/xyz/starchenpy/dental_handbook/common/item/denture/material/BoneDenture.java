package xyz.starchenpy.dental_handbook.common.item.denture.material;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import xyz.starchenpy.dental_handbook.common.item.denture.DentureType;

public class BoneDenture extends AbstractMaterial {
    public BoneDenture() {
        super("bone", 200, DentureType.INCISOR, DentureType.CANINE, DentureType.MOLAR);
    }
}
