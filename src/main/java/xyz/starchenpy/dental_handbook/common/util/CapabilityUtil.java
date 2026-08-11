package xyz.starchenpy.dental_handbook.common.util;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;
import xyz.starchenpy.dental_handbook.common.capability.DentureStorage;
import xyz.starchenpy.dental_handbook.common.capability.ModCapabilities;

import java.util.Optional;

public class CapabilityUtil {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ItemStackHandler getDenture(Player player) {
        LazyOptional<DentureStorage> capability = player.getCapability(ModCapabilities.DENTURE_CAP);

        if (!capability.isPresent()) {
            LOGGER.error("使用牙科工具时，发现玩家没有假牙能力，无法使用! 这很可能是Mod的Bug!");
            return null;
        }

        Optional<DentureStorage> resolve = capability.resolve();
        if (resolve.isEmpty()) {
            LOGGER.error("使用牙科工具时，发现能力是空的，无法使用! 这很可能是Mod的Bug!");
            return null;
        }

        return resolve.get().getHandler();
    }
}
