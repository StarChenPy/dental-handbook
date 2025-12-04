package xyz.starchenpy.dental_handbook.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.items.ItemStackHandler;

public class DentureStorage {

    private final ItemStackHandler handler = new ItemStackHandler(20);

    public ItemStackHandler getHandler() {
        return handler;
    }

    public CompoundTag serializeNBT() {
        return handler.serializeNBT();
    }

    public void deserializeNBT(CompoundTag nbt) {
        handler.deserializeNBT(nbt);
    }
}