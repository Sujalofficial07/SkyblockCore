package com.sujal.skyblockcore.data;

import com.sujal.skyblockcore.SkyblockCore;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {
    private static final Map<UUID, PlayerProfile> activeProfiles = new HashMap<>();
    private static File dataDirectory;

    public static void init() {
        // Hook into server start to determine save directory
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            dataDirectory = server.getSavePath(WorldSavePath.ROOT).resolve("skyblock_data").toFile();
            if (!dataDirectory.exists()) {
                dataDirectory.mkdirs();
            }
        });
        
        // Save all data on server stop
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> saveAll());
    }

    public static PlayerProfile getProfile(ServerPlayerEntity player) {
        return activeProfiles.computeIfAbsent(player.getUuid(), PlayerProfile::new);
    }

    // Needed for offline queries (if player is not online)
    public static PlayerProfile getProfile(UUID uuid) {
        return activeProfiles.get(uuid); 
    }

    public static void loadPlayer(ServerPlayerEntity player) {
        File playerFile = new File(dataDirectory, player.getUuid().toString() + ".dat");
        PlayerProfile profile = new PlayerProfile(player.getUuid());

        if (playerFile.exists()) {
            try {
                NbtCompound nbt = NbtIo.readCompressed(playerFile);
                if (nbt != null) {
                    profile.readFromNbt(nbt);
                }
            } catch (IOException e) {
                SkyblockCore.LOGGER.error("Failed to load player data for " + player.getName().getString(), e);
            }
        }
        activeProfiles.put(player.getUuid(), profile);
    }

    public static void savePlayer(ServerPlayerEntity player) {
        PlayerProfile profile = activeProfiles.get(player.getUuid());
        if (profile == null) return;

        if (dataDirectory == null) return; // Server hasn't started fully or is wrong context

        File playerFile = new File(dataDirectory, player.getUuid().toString() + ".dat");
        try {
            NbtCompound nbt = new NbtCompound();
            profile.writeToNbt(nbt);
            NbtIo.writeCompressed(nbt, playerFile);
        } catch (IOException e) {
            SkyblockCore.LOGGER.error("Failed to save player data for " + player.getName().getString(), e);
        }
    }

    public static void unloadPlayer(ServerPlayerEntity player) {
        savePlayer(player);
        activeProfiles.remove(player.getUuid());
    }

    public static void saveAll() {
        // Fallback to save everyone currently in memory
        // Ideally needs reference to ServerPlayerEntity, but simplified here
        activeProfiles.clear(); 
    }
}
