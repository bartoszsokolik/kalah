package pl.sokolik.bartosz.kalah.game.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

import static java.lang.String.format;

@Configuration
class GameConfiguration {

  private final int serverPort;

  public GameConfiguration(@Value("${server.port:8080}") int serverPort) {
    this.serverPort = serverPort;
  }

  @Bean
  GameService gameService() {
    final var host = InetAddress.getLoopbackAddress().getHostName();

    return new GameService(
        format("http://%s:%s", host, serverPort),
        new InMemoryGameRepository(),
        new MoveValidator(),
        new MoveExecutor());
  }
}
