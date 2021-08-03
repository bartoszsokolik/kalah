package pl.sokolik.bartosz.kalah.game.domain.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class ErrorDto {

  UUID errorId;
  String message;
}
