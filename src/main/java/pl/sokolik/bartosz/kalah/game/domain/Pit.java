package pl.sokolik.bartosz.kalah.game.domain;

import lombok.Getter;

import static pl.sokolik.bartosz.kalah.game.domain.Board.LAST_PIT_NUMBER;
import static pl.sokolik.bartosz.kalah.game.domain.Player.FIRST_PLAYER;
import static pl.sokolik.bartosz.kalah.game.domain.Player.SECOND_PLAYER;

@Getter
public class Pit {

  private int number;
  private Player owner;
  private boolean kalah;
  private int stonesNumber;

  public Pit(int number) {
    this.number = number;
    this.owner = number <= LAST_PIT_NUMBER / 2 ? FIRST_PLAYER : SECOND_PLAYER;
    this.kalah = number == LAST_PIT_NUMBER || number == LAST_PIT_NUMBER / 2;
    this.stonesNumber = isKalah() ? 0 : 6;
  }

  public void updateStonesNumber(int stonesNumber) {
    this.stonesNumber = stonesNumber;
  }

  public boolean isPlayersKalah(Player player) {
    return isKalah() && player == owner;
  }

  public boolean isPlayersPit(Player player) {
    return owner == player;
  }

  public boolean isApplicableForPlayer(Player player) {
    return isApplicableForFirstPlayer(player) || isApplicableForSecondPlayer(player);
  }

  private boolean isApplicableForFirstPlayer(Player player) {
    return FIRST_PLAYER == player && number != LAST_PIT_NUMBER;
  }

  private boolean isApplicableForSecondPlayer(Player player) {
    return SECOND_PLAYER == player && number != LAST_PIT_NUMBER / 2;
  }
}
