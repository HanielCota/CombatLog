package com.github.hanielcota.combatlog.task;

import com.github.hanielcota.combatlog.CombatLogPlugin;
import com.github.hanielcota.combatlog.cache.CombatCache;
import com.github.hanielcota.combatlog.utils.ActionBarUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class CombatTask {

	private final Map<Player, BukkitTask> combatTasks = new HashMap<>();
	private final CombatCache combatCache;
	private final CombatLogPlugin plugin;

	public CombatTask(CombatCache combatCache, CombatLogPlugin plugin) {
		this.combatCache = combatCache;
		this.plugin = plugin;
	}

	public void startCombat(Player player) {
		if (player == null || !player.isOnline()) {
			return;
		}

		combatCache.put(player, 15);
		startCombatTimer(player);
	}

	public void startCombatTimer(Player player) {
		if (!combatCache.contains(player)) {
			return;
		}

		cancelCombatTimer(player);

		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if (!player.isOnline() || !combatCache.contains(player)) {
					cancel();
					return;
				}

				int time = combatCache.get(player);
				if (time <= 0) {
					combatCache.remove(player);
					new ActionBarUtils().sendActionBarMessage(player, "§aVocê saiu de combate.");
					cancel();
					return;
				}

				combatCache.put(player, time - 1);
				new ActionBarUtils().sendActionBarMessage(player, "§cVocê está em combate por mais §e" + time + "s");
			}
		}.runTaskTimer(plugin, 0L, 20L);

		combatTasks.put(player, task);
	}

	public void cancelCombatTimer(Player player) {
		BukkitTask task = combatTasks.get(player);
		if (task != null) {
			task.cancel();
		}
		combatTasks.remove(player);
	}

}

