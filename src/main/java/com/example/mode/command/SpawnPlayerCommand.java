package com.example.mode.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class SpawnPlayerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("spawnplayer")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> spawn(ctx, StringArgumentType.getString(ctx, "name"))))
        );
    }

    private static int spawn(CommandContext<CommandSourceStack> ctx, String name) {
        CommandSourceStack source = ctx.getSource();
        ServerLevel level = source.getLevel();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);
        FakePlayer fakePlayer = FakePlayerFactory.get(level, profile);
        fakePlayer.moveTo(source.getPosition().x, source.getPosition().y, source.getPosition().z, source.getRotation().y, 0.0F);
        level.addFreshEntity(fakePlayer);
        return 1;
    }
}
