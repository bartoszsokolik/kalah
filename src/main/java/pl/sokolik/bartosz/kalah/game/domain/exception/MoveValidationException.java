package pl.sokolik.bartosz.kalah.game.domain.exception;

public class MoveValidationException extends RuntimeException {

  public MoveValidationException(String message) {
    super(message);
  }
}
