package com.github.hanielcota.combatlog.cache.impl;

import org.bukkit.entity.Player;

public interface CombatCacheInterface<T> {

	void put(Player player, T value);

	T get(Player player);

	void remove(Player player);

	void clearCache();

	boolean contains(Player player);
}
