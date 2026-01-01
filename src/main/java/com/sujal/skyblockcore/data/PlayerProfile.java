package com.sujal.skyblockcore.data;

import net.minecraft.nbt.NbtCompound;
import java.util.UUID;

public class PlayerProfile {
    private final UUID uuid;
    private double coins;
    private long lastLogin;

    // Future extensibility: Store active area, bits, etc.
    private String currentArea = "HUB"; 

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        this.coins = 0.0;
        this.lastLogin = System.currentTimeMillis();
    }

    // --- Business Logic ---

    public void addCoins(double amount) {
        if (amount > 0) this.coins += amount;
    }

    public boolean removeCoins(double amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }
        return false;
    }

    // --- Getters & Setters ---

    public double getCoins() { return coins; }
    public void setCoins(double coins) { this.coins = coins; }
    public String getCurrentArea() { return currentArea; }
    public void setCurrentArea(String area) { this.currentArea = area; }

    // --- Serialization ---

    public void writeToNbt(NbtCompound nbt) {
        nbt.putDouble("coins", coins);
        nbt.putLong("lastLogin", System.currentTimeMillis());
        nbt.putString("currentArea", currentArea);
    }

    public void readFromNbt(NbtCompound nbt) {
        this.coins = nbt.getDouble("coins");
        this.lastLogin = nbt.getLong("lastLogin");
        if (nbt.contains("currentArea")) {
            this.currentArea = nbt.getString("currentArea");
        }
    }
}
