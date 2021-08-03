package pl.sokolik.bartosz.kalah.game.domain.exception;

import java.util.UUID;

import static java.lang.String.format;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException(UUID id) {
    super(format("Game with id %s not found", id));
  }
}
