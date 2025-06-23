package com.example.mode.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.text.Text;

import com.example.mode.client.ChallengeHandler;

public class AutoHuntScreen extends Screen {
    public AutoHuntScreen() {
        super(Text.literal("Auto Hunt"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        // Button to hunt hostile mobs only
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hostile Only"), (PressAction) button -> {
            AutoHunter.start(false);
            this.close();
        }).dimensions(centerX - 100, centerY - 10, 200, 20).build());

        // Button to hunt all mobs
        this.addDrawableChild(ButtonWidget.builder(Text.literal("All Mobs"), (PressAction) button -> {
            AutoHunter.start(true);
            this.close();
        }).dimensions(centerX - 100, centerY + 15, 200, 20).build());

        // Toggle time warp
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Time Warp"), (PressAction) button -> {
            ChallengeHandler.toggleTimeWarp();
        }).dimensions(centerX - 100, centerY + 40, 200, 20).build());

        // Toggle knockback multiplier
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Knockback"), (PressAction) button -> {
            ChallengeHandler.toggleKnockback();
        }).dimensions(centerX - 100, centerY + 65, 200, 20).build());

        // Lightning purge hostile mobs
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Lightning Purge"), (PressAction) button -> {
            ChallengeHandler.activateLightningPurge();
        }).dimensions(centerX - 100, centerY + 90, 200, 20).build());
    }

    private void close() {
        this.client.setScreen(null);
    }
}
