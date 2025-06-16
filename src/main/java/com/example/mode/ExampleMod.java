package com.example.mode;

import com.example.mode.command.SpawnPlayerCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ExampleMod implements ModInitializer {
    public static final String MODID = "mode";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            SpawnPlayerCommand.register(dispatcher);
        });
    }
}
