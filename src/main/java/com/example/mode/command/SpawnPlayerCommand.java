package com.example.mode.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;

public class SpawnPlayerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("spawnplayer")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("name", StringArgumentType.word())
                                .executes(ctx -> spawn(ctx, StringArgumentType.getString(ctx, "name"))))
        );
    }

    private static int spawn(CommandContext<ServerCommandSource> ctx, String name) {
        ServerCommandSource source = ctx.getSource();
        ServerWorld world = source.getWorld();
        ArmorStandEntity stand = new ArmorStandEntity(EntityType.ARMOR_STAND, world);
        stand.refreshPositionAndAngles(source.getPosition(), source.getRotation().y, 0.0F);
        stand.setCustomName(Text.literal(name));
        world.spawnEntity(stand);
        return 1;
    }
}
