package pl.sokolik.bartosz.kalah.game.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;
import java.util.UUID;

@Value
@Builder
public class GameDto {

  UUID id;
  String uri;
  Map<Integer, String> status;
}
