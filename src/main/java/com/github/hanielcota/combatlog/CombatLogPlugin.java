package com.github.hanielcota.combatlog;

import com.github.hanielcota.combatlog.cache.CombatCache;
import com.github.hanielcota.combatlog.listeners.EntityDamageByEntityListener;
import com.github.hanielcota.combatlog.listeners.PlayerCommandPreprocessListener;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLogPlugin extends JavaPlugin {
	@Getter
	public CombatCache combatCache;

	@Override
	public void onEnable() {
		getLogger().info("Starting combatLog plugin...");

		combatCache = new CombatCache(1000, 15);

		registerListeners();
	}

	private void registerListeners() {
		final PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new EntityDamageByEntityListener(combatCache, this), this);
		pluginManager.registerEvents(new PlayerCommandPreprocessListener(combatCache), this);

		getLogger().info("Registered listeners...");
	}

}
