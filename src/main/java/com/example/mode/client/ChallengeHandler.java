package com.example.mode.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class ChallengeHandler {
    private static boolean timeWarp = false;
    private static boolean lightningPurge = false;
    private static double knockback = 1.0;

    public static void toggleTimeWarp() {
        timeWarp = !timeWarp;
    }

    public static void toggleKnockback() {
        knockback = knockback == 1.0 ? 5.0 : 1.0;
        if (MinecraftClient.getInstance().player != null) {
            var attr = MinecraftClient.getInstance().player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
            if (attr != null) {
                attr.setBaseValue(knockback);
            }
        }
    }

    public static void activateLightningPurge() {
        lightningPurge = true;
    }

    static void onClientTick(MinecraftClient client) {
        if (client.world == null) return;
        World world = client.world;
        if (timeWarp) {
            world.setTimeOfDay(world.getTimeOfDay() + 99);
        }
        if (lightningPurge) {
            for (Entity e : world.getEntities()) {
                if (e instanceof HostileEntity) {
                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(e.getPos());
                        world.spawnEntity(lightning);
                    }
                    e.discard();
                }
            }
            lightningPurge = false;
        }
    }
}
