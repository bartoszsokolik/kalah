package pl.sokolik.bartosz.kalah.game.domain;

import lombok.extern.slf4j.Slf4j;
import pl.sokolik.bartosz.kalah.game.domain.dto.GameDto;

import java.util.UUID;

import static pl.sokolik.bartosz.kalah.game.GameResource.GAME_RESOURCE_UR_BASE;


@Slf4j
public class GameService {

  private final String uri;
  private final InMemoryGameRepository inMemoryGameRepository;
  private final MoveValidator moveValidator;
  private final MoveExecutor moveExecutor;

  public GameService(
      String uri,
      InMemoryGameRepository inMemoryGameRepository,
      MoveValidator moveValidator,
      MoveExecutor moveExecutor) {
    this.uri = uri;
    this.inMemoryGameRepository = inMemoryGameRepository;
    this.moveValidator = moveValidator;
    this.moveExecutor = moveExecutor;
  }

  public GameDto create() {
    var createdGame = inMemoryGameRepository.save(new Game());
    log.info("Created game with id {}", createdGame.getId());

    return GameDto.builder().id(createdGame.getId()).uri(getUri(createdGame)).build();
  }

  public GameDto move(UUID gameId, int pitNumber) {
    var game = inMemoryGameRepository.findById(gameId);
    moveValidator.validate(game, pitNumber);
    moveExecutor.move(game, pitNumber);

    return GameDto.builder()
        .id(game.getId())
        .uri(getUri(game))
        .status(game.getBoard().getStatus())
        .build();
  }

  private String getUri(Game game) {
    return String.format("%s%s/%s", uri, GAME_RESOURCE_UR_BASE, game.getId());
  }
}
