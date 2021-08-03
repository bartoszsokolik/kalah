package pl.sokolik.bartosz.kalah.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sokolik.bartosz.kalah.game.domain.GameService;
import pl.sokolik.bartosz.kalah.game.domain.dto.GameDto;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.sokolik.bartosz.kalah.game.GameResource.GAME_RESOURCE_UR_BASE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = GAME_RESOURCE_UR_BASE, produces = APPLICATION_JSON_VALUE)
public class GameResource {

  public static final String GAME_RESOURCE_UR_BASE = "/games";

  private final GameService gameService;

  @PostMapping
  @ResponseStatus(CREATED)
  public GameDto create() {
    return gameService.create();
  }

  @ResponseStatus(OK)
  @PutMapping("/{id}/pits/{pitId}")
  public GameDto move(@PathVariable UUID id, @PathVariable int pitId) {
    return gameService.move(id, pitId);
  }
}
