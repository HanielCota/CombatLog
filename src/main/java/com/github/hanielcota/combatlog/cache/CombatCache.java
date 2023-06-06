package com.github.hanielcota.combatlog.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.hanielcota.combatlog.exceptions.InvalidPlayerException;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.TimeUnit;

public class CombatCache {
	private final Cache<Player, Integer> playerCombatCache;

	public CombatCache(long maxSize, long expireAfterSeconds) {
		playerCombatCache = Caffeine.newBuilder()
				.maximumSize(maxSize)
				.expireAfterWrite(expireAfterSeconds, TimeUnit.SECONDS)
				.build();
	}

	/**
	 * Adiciona um jogador ao cache.
	 *
	 * @param player Jogador a ser adicionado.
	 * @param value  Valor associado ao jogador.
	 * @throws InvalidPlayerException Se o jogador for nulo ou já existir no cache.
	 */
	public void put(Player player, int value) {
		if (player != null && !contains(player)) {
			playerCombatCache.put(player, value);
		} else {
			throw new InvalidPlayerException("Player cannot be null or already exists in the cache");
		}
	}

	/**
	 * Obtém o valor associado a um jogador no cache.
	 *
	 * @param player Jogador para recuperar o valor.
	 * @throws InvalidPlayerException Se o jogador for nulo.
	 */
	public @Nullable Integer get(Player player) {
		if (player != null) {
			return playerCombatCache.getIfPresent(player);
		} else {
			throw new InvalidPlayerException("Player cannot be null");
		}
	}

	/**
	 * Verifica se um jogador está presente no cache.
	 *
	 * @param player Jogador para verificar a presença no cache.
	 * @return true se o jogador estiver presente, false caso contrário.
	 * @throws InvalidPlayerException Se o jogador for nulo.
	 */
	public boolean contains(Player player) {
		if (player != null) {
			return playerCombatCache.getIfPresent(player) != null;
		} else {
			throw new InvalidPlayerException("Player cannot be null");
		}
	}

	/**
	 * Remove um jogador do cache.
	 *
	 * @param player Jogador a ser removido.
	 * @throws InvalidPlayerException Se o jogador for nulo.
	 */
	public void remove(Player player) {
		if (player != null) {
			playerCombatCache.invalidate(player);
		} else {
			throw new InvalidPlayerException("Player cannot be null");
		}
	}

	public void clearCache() {
		playerCombatCache.invalidateAll();
	}
}
