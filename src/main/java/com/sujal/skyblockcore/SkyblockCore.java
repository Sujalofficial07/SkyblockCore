package com.sujal.skyblockcore;

import com.sujal.skyblockcore.command.CoreCommands;
import com.sujal.skyblockcore.data.ProfileManager;
import com.sujal.skyblockcore.event.ProfileEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockCore implements ModInitializer {
    public static final String MOD_ID = "skyblockcore";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SkyblockCore Systems...");

        // 1. Initialize Profile Manager (Data Persistence)
        ProfileManager.init();

        // 2. Register Events (Join/Leave)
        ProfileEvents.register();

        // 3. Register Commands (/hub, /is, etc.)
        CoreCommands.register();
        
        LOGGER.info("SkyblockCore Initialized successfully!");
    }
}
