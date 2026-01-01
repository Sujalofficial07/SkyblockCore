package com.sujal.skyblockcore.api;

import com.sujal.skyblockcore.data.PlayerProfile;
import com.sujal.skyblockcore.data.ProfileManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class SkyblockAPI {

    /**
     * Retrieves the Skyblock Profile for a given player.
     * Contains Coins, Stats, etc.
     */
    public static PlayerProfile getProfile(ServerPlayerEntity player) {
        return ProfileManager.getProfile(player);
    }

    /**
     * Adds coins to a player efficiently.
     */
    public static void addCoins(ServerPlayerEntity player, double amount) {
        getProfile(player).addCoins(amount);
    }

    /**
     * Removes coins. Returns false if insufficient funds.
     */
    public static boolean removeCoins(ServerPlayerEntity player, double amount) {
        return getProfile(player).removeCoins(amount);
    }

    /**
     * Gets current coin balance.
     */
    public static double getCoins(ServerPlayerEntity player) {
        return getProfile(player).getCoins();
    }
}
