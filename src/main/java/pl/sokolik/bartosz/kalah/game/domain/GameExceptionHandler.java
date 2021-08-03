package pl.sokolik.bartosz.kalah.game.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.sokolik.bartosz.kalah.game.domain.dto.ErrorDto;
import pl.sokolik.bartosz.kalah.game.domain.exception.GameNotFoundException;
import pl.sokolik.bartosz.kalah.game.domain.exception.MoveValidationException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
class GameExceptionHandler {

  @ExceptionHandler(value = {GameNotFoundException.class})
  ResponseEntity<ErrorDto> handleNotFoundException(GameNotFoundException ex) {
    final var error = new ErrorDto(UUID.randomUUID(), ex.getMessage());
    return status(NOT_FOUND).body(error);
  }

  @ExceptionHandler(value = MoveValidationException.class)
  ResponseEntity<ErrorDto> handleMoveValidationException(MoveValidationException ex) {
    final var error = new ErrorDto(UUID.randomUUID(), ex.getMessage());
    return badRequest().body(error);
  }
}
