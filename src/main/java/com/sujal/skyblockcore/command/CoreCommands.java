package com.sujal.skyblockcore.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.sujal.skyblockcore.api.SkyblockAPI;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class CoreCommands {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            
            // /hub command
            dispatcher.register(literal("hub")
                .executes(CoreCommands::teleportToHub));

            // /is command
            dispatcher.register(literal("is")
                .executes(CoreCommands::teleportToIsland));

            // Debug command: /coins (Check balance)
            dispatcher.register(literal("coins")
                .executes(context -> {
                   double coins = SkyblockAPI.getCoins(context.getSource().getPlayer());
                   context.getSource().sendMessage(Text.of("§6Coins: §e" + String.format("%.1f", coins)));
                   return Command.SINGLE_SUCCESS;
                }));
        });
    }

    private static int teleportToHub(CommandContext<ServerCommandSource> context) {
        // Placeholder for actual teleport logic (Needs Coordinates config later)
        context.getSource().sendMessage(Text.of("§aWarping to Hub..."));
        return Command.SINGLE_SUCCESS;
    }

    private static int teleportToIsland(CommandContext<ServerCommandSource> context) {
        // Placeholder for Island teleport
        context.getSource().sendMessage(Text.of("§aWarping to Home Island..."));
        return Command.SINGLE_SUCCESS;
    }
}
