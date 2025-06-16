package com.example.mode.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class AutoHuntClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register client command to open the AutoHunt UI
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("autohuntui")
                    .executes(context -> {
                        MinecraftClient.getInstance().setScreen(new AutoHuntScreen());
                        return 1;
                    }));
        });

        // Register tick handler for automatic hunting logic
        ClientTickEvents.END_CLIENT_TICK.register(client -> AutoHunter.onClientTick(client));
    }
}
