package pl.sokolik.bartosz.kalah.game.domain;

import pl.sokolik.bartosz.kalah.game.domain.exception.MoveValidationException;

import static java.lang.String.*;

class MoveValidator {

  void validate(Game game, int pitNumber) {
    var pit = game.getBoard().getPit(pitNumber);
    var currentPlayer = game.getCurrentPlayer();

    validateKalah(pit);
    validateCurrentPlayer(pit, currentPlayer);
    validateStonesNumber(pit);
  }

  private void validateKalah(Pit pit) {
    if (pit.isKalah()) {
      throw new MoveValidationException("Cannot start move from kalah pit");
    }
  }

  private void validateCurrentPlayer(Pit pit, Player currentPlayer) {
    if (!pit.isPlayersPit(currentPlayer)) {
      throw new MoveValidationException(
          format("Current player is not an owner of pit with id %s", pit.getNumber()));
    }
  }

  private void validateStonesNumber(Pit pit) {
    if (pit.getStonesNumber() == 0) {
      throw new MoveValidationException("Cannot start move from empty pit");
    }
  }
}
