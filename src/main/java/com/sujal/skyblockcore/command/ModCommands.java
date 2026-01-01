package com.sujal.skyblockcore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sujal.skyblockcore.api.SkyblockAPI;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ModCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        
        // --- ADMIN COMMANDS ---
        
        // /sethub
        dispatcher.register(CommandManager.literal("sethub")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(context -> {
                SkyblockAPI.setLocation(context.getSource().getServer(), "hub", context.getSource().getPlayer());
                context.getSource().sendFeedback(() -> Text.literal("§aHub location set!"), false);
                return 1;
            }));

        // /setisland
        dispatcher.register(CommandManager.literal("setisland")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(context -> {
                SkyblockAPI.setLocation(context.getSource().getServer(), "island", context.getSource().getPlayer());
                context.getSource().sendFeedback(() -> Text.literal("§aIsland location set!"), false);
                return 1;
            }));

        // /warp set <name>
        dispatcher.register(CommandManager.literal("warp")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.literal("set")
                .then(CommandManager.argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String name = StringArgumentType.getString(context, "name");
                        SkyblockAPI.setLocation(context.getSource().getServer(), name, context.getSource().getPlayer());
                        context.getSource().sendFeedback(() -> Text.literal("§aWarp '" + name + "' set!"), false);
                        return 1;
                    })
                )
            ));

        // --- ECONOMY ADMIN COMMANDS ---
        
        // /eco add <amount>
        dispatcher.register(CommandManager.literal("eco")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.literal("add")
                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                    .executes(context -> {
                        int amount = IntegerArgumentType.getInteger(context, "amount");
                        SkyblockAPI.addCoins(context.getSource().getPlayer(), amount);
                        context.getSource().sendFeedback(() -> Text.literal("§6Given " + amount + " coins."), false);
                        return 1;
                    })
                )
            ));

        // --- PLAYER COMMANDS ---

        // /hub
        dispatcher.register(CommandManager.literal("hub")
            .executes(context -> {
                boolean success = SkyblockAPI.teleportTo(context.getSource().getPlayer(), "hub");
                if(success) context.getSource().sendFeedback(() -> Text.literal("§bWarped to Hub!"), false);
                return 1;
            }));

        // /is or /island
        dispatcher.register(CommandManager.literal("is")
            .executes(context -> {
                boolean success = SkyblockAPI.teleportTo(context.getSource().getPlayer(), "island");
                if(success) context.getSource().sendFeedback(() -> Text.literal("§bWarped to Island!"), false);
                return 1;
            }));
            
        // /coins
        dispatcher.register(CommandManager.literal("coins")
            .executes(context -> {
                long coins = SkyblockAPI.getCoins(context.getSource().getPlayer());
                context.getSource().sendFeedback(() -> Text.literal("§6Coins: " + coins), false);
                return 1;
            }));
    }
}
