package com.github.hanielcota.combatlog.listeners;

import com.github.hanielcota.combatlog.CombatLogPlugin;
import com.github.hanielcota.combatlog.cache.CombatCache;
import com.github.hanielcota.combatlog.task.CombatTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

	private final CombatCache combatCache;
	private final CombatLogPlugin plugin;

	public EntityDamageByEntityListener(CombatCache combatCache, CombatLogPlugin plugin) {
		this.combatCache = combatCache;
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			Player victim = (Player) event.getEntity();

			handlePlayerCombat(attacker, victim);
		}
	}

	private void handlePlayerCombat(Player attacker, Player victim) {
		if (victim == null || attacker == null || combatCache.contains(victim) || combatCache.contains(attacker)) {
			return;
		}
		final CombatTask combatTask = new CombatTask(combatCache, plugin);

		combatCache.put(attacker, 15);
		combatCache.put(victim, 15);

		combatTask.startCombat(victim);
		combatTask.startCombat(attacker);
	}

}
