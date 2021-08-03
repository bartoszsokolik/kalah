package pl.sokolik.bartosz.kalah.game.domain;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;
import static pl.sokolik.bartosz.kalah.game.domain.Player.FIRST_PLAYER;

@Getter
public class Game {

  private UUID id;
  private Board board;
  private Player currentPlayer;
  private Player winner;

  public Game() {
    this.id = UUID.randomUUID();
    this.board = new Board();
    this.currentPlayer = FIRST_PLAYER;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public void setWinner(Player winner) {
    this.winner = winner;
  }

  public boolean isFinished() {
    return board.getPits().stream()
        .filter(pit -> !pit.isKalah())
        .collect(groupingBy(Pit::getOwner))
        .values()
        .stream()
        .map(this::sumStones)
        .anyMatch(p -> p == 0);
  }

  private int sumStones(List<Pit> pits) {
    return pits.stream().mapToInt(Pit::getStonesNumber).sum();
  }
}
