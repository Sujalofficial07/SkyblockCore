package com.sujal.skyblockcore.data;

import net.minecraft.nbt.NbtCompound;

public class PlayerProfile {
    private long coins = 0;

    public PlayerProfile() {}

    public long getCoins() {
        return coins;
    }

    public void addCoins(long amount) {
        this.coins += amount;
    }

    public boolean removeCoins(long amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }
        return false;
    }

    public void setCoins(long amount) {
        this.coins = amount;
    }

    public static PlayerProfile fromNbt(NbtCompound nbt) {
        PlayerProfile profile = new PlayerProfile();
        profile.coins = nbt.getLong("coins");
        return profile;
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putLong("coins", coins);
        return nbt;
    }
}
