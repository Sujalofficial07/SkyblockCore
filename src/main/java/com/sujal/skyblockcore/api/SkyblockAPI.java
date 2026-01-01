package com.sujal.skyblockcore.api;

import com.sujal.skyblockcore.data.PlayerProfile;
import com.sujal.skyblockcore.data.SkyblockSavedData;
import com.sujal.skyblockcore.data.WarpLocation;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SkyblockAPI {

    // --- COINS API ---

    public static long getCoins(ServerPlayerEntity player) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(player.getServer());
        PlayerProfile profile = data.players.computeIfAbsent(player.getUuid(), k -> new PlayerProfile());
        return profile.getCoins();
    }

    public static void addCoins(ServerPlayerEntity player, long amount) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(player.getServer());
        PlayerProfile profile = data.players.computeIfAbsent(player.getUuid(), k -> new PlayerProfile());
        profile.addCoins(amount);
        data.markDirty();
    }

    public static boolean removeCoins(ServerPlayerEntity player, long amount) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(player.getServer());
        PlayerProfile profile = data.players.computeIfAbsent(player.getUuid(), k -> new PlayerProfile());
        boolean success = profile.removeCoins(amount);
        if (success) data.markDirty();
        return success;
    }

    // --- LOCATION & TELEPORT API ---

    public static void setLocation(MinecraftServer server, String name, ServerPlayerEntity player) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(server);
        String dimId = player.getWorld().getRegistryKey().getValue().toString();
        
        WarpLocation loc = new WarpLocation(
            player.getX(), player.getY(), player.getZ(),
            player.getYaw(), player.getPitch(),
            dimId
        );
        data.locations.put(name, loc);
        data.markDirty();
    }

    public static boolean teleportTo(ServerPlayerEntity player, String locationName) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(player.getServer());
        
        if (!data.locations.containsKey(locationName)) {
            player.sendMessage(Text.literal("§cWarp not set: " + locationName), false);
            return false;
        }

        WarpLocation loc = data.locations.get(locationName);
        
        // Dimension handling
        Identifier dimId = new Identifier(loc.dimensionId);
        RegistryKey<net.minecraft.world.World> worldKey = RegistryKey.of(RegistryKeys.WORLD, dimId);
        ServerWorld targetWorld = player.getServer().getWorld(worldKey);

        if (targetWorld == null) {
            player.sendMessage(Text.literal("§cDimension not found: " + loc.dimensionId), false);
            return false;
        }

        player.teleport(targetWorld, loc.x, loc.y, loc.z, loc.yaw, loc.pitch);
        return true;
    }
    
    public static boolean hasLocation(MinecraftServer server, String name) {
        SkyblockSavedData data = SkyblockSavedData.getServerState(server);
        return data.locations.containsKey(name);
    }
}
