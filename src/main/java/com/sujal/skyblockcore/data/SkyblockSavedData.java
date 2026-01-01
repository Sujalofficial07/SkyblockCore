package com.sujal.skyblockcore.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockSavedData extends PersistentState {
    
    // Storage Maps
    public Map<String, WarpLocation> locations = new HashMap<>();
    public Map<UUID, PlayerProfile> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // Save Locations
        NbtCompound locTag = new NbtCompound();
        locations.forEach((name, loc) -> locTag.put(name, loc.toNbt()));
        nbt.put("locations", locTag);

        // Save Players
        NbtCompound playerTag = new NbtCompound();
        players.forEach((uuid, profile) -> playerTag.put(uuid.toString(), profile.toNbt()));
        nbt.put("players", playerTag);

        return nbt;
    }

    public static SkyblockSavedData createFromNbt(NbtCompound tag) {
        SkyblockSavedData state = new SkyblockSavedData();
        
        // Load Locations
        NbtCompound locTag = tag.getCompound("locations");
        locTag.getKeys().forEach(key -> {
            state.locations.put(key, WarpLocation.fromNbt(locTag.getCompound(key)));
        });

        // Load Players
        NbtCompound playerTag = tag.getCompound("players");
        playerTag.getKeys().forEach(key -> {
            state.players.put(UUID.fromString(key), PlayerProfile.fromNbt(playerTag.getCompound(key)));
        });

        return state;
    }

    public static SkyblockSavedData getServerState(MinecraftServer server) {
        // Overworld ka state manager use karte hain global data ke liye
        PersistentStateManager stateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        
        SkyblockSavedData state = stateManager.getOrCreate(
                SkyblockSavedData::createFromNbt,
                SkyblockSavedData::new,
                "skyblock_core_data"
        );
        
        state.markDirty(); // Always mark dirty ensures saves happen
        return state;
    }
}
