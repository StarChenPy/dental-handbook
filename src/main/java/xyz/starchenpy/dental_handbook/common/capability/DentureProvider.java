package xyz.starchenpy.dental_handbook.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class DentureProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    private final DentureStorage inst = new DentureStorage();
    private final LazyOptional<DentureStorage> optional = LazyOptional.of(() -> inst);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        return cap == ModCapabilities.DENTURE_CAP ? optional.cast() : LazyOptional.empty();
    }

    // 自动保存
    @Override
    public CompoundTag serializeNBT() {
        return inst.serializeNBT();
    }

    // 自动读取
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inst.deserializeNBT(nbt);
    }
}