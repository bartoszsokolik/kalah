package pl.sokolik.bartosz.kalah.game.domain;

public enum Player {
  FIRST_PLAYER {
    @Override
    Player getOppositePlayer() {
      return SECOND_PLAYER;
    }

    @Override
    int getKalahPosition() {
      return 7;
    }
  },
  SECOND_PLAYER {
    @Override
    Player getOppositePlayer() {
      return FIRST_PLAYER;
    }

    @Override
    int getKalahPosition() {
      return 14;
    }
  };

  abstract Player getOppositePlayer();

  abstract int getKalahPosition();
}
