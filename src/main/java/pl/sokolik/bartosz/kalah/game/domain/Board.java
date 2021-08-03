package pl.sokolik.bartosz.kalah.game.domain;

import lombok.Getter;

import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.rangeClosed;

@Getter
public class Board {

  public static final int FIRST_PIT_NUMBER = 1;
  public static final int LAST_PIT_NUMBER = 14;

  private List<Pit> pits;

  public Board() {
    this.pits = rangeClosed(FIRST_PIT_NUMBER, LAST_PIT_NUMBER).mapToObj(Pit::new).collect(toList());
  }

  public Pit getPit(int number) {
    return pits.get((number - 1) % LAST_PIT_NUMBER);
  }

  public Pit getOppositePit(int number) {
    return getPit(LAST_PIT_NUMBER - number);
  }

  public Pit getPlayersKalah(Player currentPlayer) {
    return getPit(currentPlayer.getKalahPosition());
  }

  public int getPlayerTotalStonesCount(Player player) {
    return pits.stream()
        .filter(pit -> pit.isPlayersPit(player))
        .mapToInt(Pit::getStonesNumber)
        .sum();
  }

  public Map<Integer, String> getStatus() {
    return pits.stream().collect(toMap(Pit::getNumber, pit -> valueOf(pit.getStonesNumber())));
  }

  public void clearBasePits() {
    pits.stream().filter(pit -> !pit.isKalah()).forEach(pit -> pit.updateStonesNumber(0));
  }
}
