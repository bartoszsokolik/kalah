package pl.sokolik.bartosz.kalah.game.domain

class TestGameServiceConfiguration {

    static GameService testGameService() {
        return new GameService(
                "http://localhost:8080",
                new InMemoryGameRepository(),
                new MoveValidator(),
                new MoveExecutor()
        )
    }
}
