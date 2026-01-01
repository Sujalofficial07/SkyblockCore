package com.sujal.skyblockcore.event;

import com.sujal.skyblockcore.data.ProfileManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ProfileEvents {
    public static void register() {
        // Load data when player joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ProfileManager.loadPlayer(handler.player);
        });

        // Save and unload data when player disconnects
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ProfileManager.unloadPlayer(handler.player);
        });
    }
}
