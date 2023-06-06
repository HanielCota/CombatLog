package com.github.hanielcota.combatlog.listeners;

import com.github.hanielcota.combatlog.cache.CombatCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

	private final CombatCache combatCache;

	public PlayerCommandPreprocessListener(CombatCache combatCache) {
		this.combatCache = combatCache;
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		if (combatCache.contains(player)) {
			event.setCancelled(true);
			player.sendMessage("§cVocê não pode executar comandos enquanto está em combate!");
		}
	}
}
