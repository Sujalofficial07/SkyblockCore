package com.sujal.skyblockcore.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WarpLocation {
    public double x, y, z;
    public float yaw, pitch;
    public String dimensionId;

    public WarpLocation(double x, double y, double z, float yaw, float pitch, String dimensionId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.dimensionId = dimensionId;
    }

    public static WarpLocation fromNbt(NbtCompound nbt) {
        return new WarpLocation(
            nbt.getDouble("x"),
            nbt.getDouble("y"),
            nbt.getDouble("z"),
            nbt.getFloat("yaw"),
            nbt.getFloat("pitch"),
            nbt.getString("dim")
        );
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putDouble("x", x);
        nbt.putDouble("y", y);
        nbt.putDouble("z", z);
        nbt.putFloat("yaw", yaw);
        nbt.putFloat("pitch", pitch);
        nbt.putString("dim", dimensionId);
        return nbt;
    }
}
