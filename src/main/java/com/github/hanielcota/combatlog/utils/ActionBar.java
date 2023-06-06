package com.github.hanielcota.combatlog.utils;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBar {
    public void sendActionBar(Player player, String message) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

    public void sendActionBarToAll(String message) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            sendActionBar(onlinePlayer, message);
        }
    }

    public void sendActionBarWithDuration(Plugin plugin, Player player, String message, int duration) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        new BukkitRunnable() {
            int current = 0;

            @Override
            public void run() {
                if (current >= duration) {
                    cancel();
                }

                sendActionBar(player, message);
                current++;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    public void sendActionBarToAllWithDuration(Plugin plugin, String message, int duration) {
        new BukkitRunnable() {
            int current = 0;

            @Override
            public void run() {
                if (current >= duration) {
                    cancel();
                }

                sendActionBarToAll(message);
                current++;
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }
}