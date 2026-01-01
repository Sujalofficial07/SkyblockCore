package com.sujal.skyblockcore;

import com.sujal.skyblockcore.command.ModCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockCore implements ModInitializer {
    public static final String MOD_ID = "skyblockcore";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SkyblockCore...");

        // Register Commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ModCommands.register(dispatcher);
        });
        
        LOGGER.info("SkyblockCore initialized successfully.");
    }
}
