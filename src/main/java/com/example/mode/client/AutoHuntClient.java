package com.example.mode.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class AutoHuntClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register client commands to open the challenge UI
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("autohuntui")
                    .executes(context -> {
                        MinecraftClient.getInstance().setScreen(new AutoHuntScreen());
                        return 1;
                    }));

            dispatcher.register(ClientCommandManager.literal("challengeui")
                    .executes(context -> {
                        MinecraftClient.getInstance().setScreen(new AutoHuntScreen());
                        return 1;
                    }));
        });

        // Register tick handlers for automatic hunting and challenges
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            AutoHunter.onClientTick(client);
            ChallengeHandler.onClientTick(client);
        });
    }
}
