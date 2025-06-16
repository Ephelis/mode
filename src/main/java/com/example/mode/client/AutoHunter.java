package com.example.mode.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class AutoHunter {
    private static boolean active = false;
    private static boolean killAll = false;

    public static void start(boolean huntAll) {
        active = true;
        killAll = huntAll;
    }

    public static void stop() {
        active = false;
    }

    static void onClientTick(MinecraftClient client) {
        if (!active || client.player == null || client.world == null) {
            return;
        }

        // Find nearest target entity
        Entity target = client.world.getEntities().stream()
                .filter(e -> e instanceof LivingEntity)
                .filter(e -> killAll || e instanceof HostileEntity)
                .filter(e -> e != client.player)
                .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(client.player)))
                .orElse(null);

        if (target == null) return;

        // Simple movement toward target (ignores obstacles)
        Vec3d direction = target.getPos().subtract(client.player.getPos());
        Vec3d velocity = direction.normalize().multiply(0.5);
        client.player.addVelocity(velocity.x, velocity.y, velocity.z);

        // If close enough, attack
        if (client.player.squaredDistanceTo(target) < 4.0) {
            client.interactionManager.attackEntity(client.player, target);
            client.player.swingHand(client.player.getActiveHand());
        }
    }
}
