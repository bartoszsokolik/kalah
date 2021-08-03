package pl.sokolik.bartosz.kalah.game.domain;

import lombok.extern.slf4j.Slf4j;

import static pl.sokolik.bartosz.kalah.game.domain.Board.LAST_PIT_NUMBER;
import static pl.sokolik.bartosz.kalah.game.domain.Player.FIRST_PLAYER;
import static pl.sokolik.bartosz.kalah.game.domain.Player.SECOND_PLAYER;

@Slf4j
class MoveExecutor {

  void move(Game game, int pitNumber) {
    log.info("Moving stones from pit with number {}", pitNumber);

    moveStones(game, pitNumber);
    checkAndChooseWinner(game);
  }

  private void moveStones(Game game, int pitNumber) {
    var board = game.getBoard();
    var pit = board.getPit(pitNumber);
    var currentPlayer = game.getCurrentPlayer();

    var numberOfStones = pit.getStonesNumber();
    var currentPitNumber = pit.getNumber();
    pit.updateStonesNumber(0);

    while (numberOfStones > 0) {
      var currentPit = board.getPit(++currentPitNumber);

      if (currentPit.isApplicableForPlayer(currentPlayer)) {
        currentPit.updateStonesNumber(currentPit.getStonesNumber() + 1);
        numberOfStones--;
      }
    }

    var lastPit = board.getPit(currentPitNumber);
    checkAndGetOppositeStones(lastPit, board, currentPlayer);
    checkAndUpdateCurrentPlayer(game, lastPit);
  }

  private void checkAndGetOppositeStones(Pit lastPit, Board board, Player currentPlayer) {
    if (lastPit.getStonesNumber() == 1 && !lastPit.isKalah() && lastPit.isPlayersPit(currentPlayer)) {
      var oppositePit = board.getOppositePit(lastPit.getNumber());
      if (oppositePit.getStonesNumber() > 0) {
        var kalah = board.getPlayersKalah(currentPlayer);
        var kalahStones = lastPit.getStonesNumber() + oppositePit.getStonesNumber() + kalah.getStonesNumber();

        kalah.updateStonesNumber(kalahStones);
        oppositePit.updateStonesNumber(0);
        lastPit.updateStonesNumber(0);
      }
    }
  }

  private void checkAndUpdateCurrentPlayer(Game game, Pit pit) {
    if (!pit.isPlayersKalah(game.getCurrentPlayer())) {
      game.setCurrentPlayer(game.getCurrentPlayer().getOppositePlayer());
    }
  }

  private void checkAndChooseWinner(Game game) {
    if (game.isFinished()) {
      var firstPlayerTotalStonesCount = game.getBoard().getPlayerTotalStonesCount(FIRST_PLAYER);
      var secondPlayerTotalStonesCount = game.getBoard().getPlayerTotalStonesCount(SECOND_PLAYER);

      game.getBoard().clearBasePits();
      game.getBoard().getPit(LAST_PIT_NUMBER / 2).updateStonesNumber(firstPlayerTotalStonesCount);
      game.getBoard().getPit(LAST_PIT_NUMBER).updateStonesNumber(secondPlayerTotalStonesCount);

      if (firstPlayerTotalStonesCount > secondPlayerTotalStonesCount) {
        game.setWinner(FIRST_PLAYER);
      } else if (secondPlayerTotalStonesCount > firstPlayerTotalStonesCount) {
        game.setWinner(SECOND_PLAYER);
      }
    }
  }
}
