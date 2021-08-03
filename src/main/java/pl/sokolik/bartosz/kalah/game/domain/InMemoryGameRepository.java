package pl.sokolik.bartosz.kalah.game.domain;

import pl.sokolik.bartosz.kalah.game.domain.exception.GameNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryGameRepository {

  private static final Map<UUID, Game> GAME_MAP = new ConcurrentHashMap<>();

  Game save(Game game) {
    GAME_MAP.put(game.getId(), game);
    return game;
  }

  Game findById(UUID id) {
    return Optional.ofNullable(GAME_MAP.get(id)).orElseThrow(() -> new GameNotFoundException(id));
  }
}
