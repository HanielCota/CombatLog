package com.github.hanielcota.combatlog.utils;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarUtils {

	/**
	 * Envia uma ActionBarUtils para um jogador específico.
	 *
	 * @param player  O jogador que receberá a ActionBarUtils.
	 * @param message A mensagem a ser exibida na ActionBarUtils.
	 */
	public void sendActionBarMessage(Player player, String message) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}

		if (message == null || message.isEmpty()) {
			throw new IllegalArgumentException("Message cannot be null or empty");
		}

		CraftPlayer craftPlayer = (CraftPlayer) player;
		craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
	}

	/**
	 * Envia uma ActionBarUtils para todos os jogadores online.
	 *
	 * @param message A mensagem a ser exibida na ActionBarUtils.
	 */
	public void sendActionBarMessageToAll(String message) {
		Bukkit.getOnlinePlayers().forEach(player -> sendActionBarMessage(player, message));
	}

	/**
	 * Envia uma ActionBarUtils com duração para um jogador específico.
	 *
	 * @param plugin   O plugin responsável por agendar a tarefa.
	 * @param player   O jogador que receberá a ActionBarUtils.
	 * @param message  A mensagem a ser exibida na ActionBarUtils.
	 * @param duration A duração em segundos da exibição da ActionBarUtils.
	 */
	public void sendActionBarMessageWithDuration(Plugin plugin, Player player, String message, int duration) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}

		if (message == null || message.isEmpty()) {
			throw new IllegalArgumentException("Message cannot be null or empty");
		}

		new BukkitRunnable() {
			int current = 0;

			@Override
			public void run() {
				if (current >= duration) {
					cancel();
				}

				sendActionBarMessage(player, message);
				current++;
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 20L);
	}

	/**
	 * Envia uma ActionBarUtils com duração para todos os jogadores online.
	 *
	 * @param plugin   O plugin responsável por agendar a tarefa.
	 * @param message  A mensagem a ser exibida na ActionBarUtils.
	 * @param duration A duração em segundos da exibição da ActionBarUtils.
	 */
	public void sendActionBarMessageToAllWithDuration(Plugin plugin, String message, int duration) {
		Bukkit.getOnlinePlayers().forEach(player -> sendActionBarMessageWithDuration(plugin, player, message, duration));
	}
}