package pl.sokolik.bartosz.kalah.game.domain

import spock.lang.Specification
import spock.lang.Subject

import static pl.sokolik.bartosz.kalah.game.domain.TestGameServiceConfiguration.testGameService

class GameServiceTest extends Specification {

    @Subject
    GameService gameService = testGameService()

    def "should create game"() {
        when:
            def result = gameService.create()

        then:
            result.id != null
            result.uri == "http://localhost:8080/games/${result.id}"
    }

    def "should make a move"() {
        given:
            def game = gameService.create()

        when:
            def result = gameService.move(game.getId(), 6)

        then:
            result.id != null
            result.uri == "http://localhost:8080/games/${result.id}"
            result.status.findAll {it -> [3, 4, 5, 6, 8].contains(it.key)}
                    .collect {it -> it.value}
                    .every(it -> "7")

            result.status.findAll { [1, 9, 10, 11, 12, 13].contains(it.key) }
                    .collect { it -> it.value }
                    .every(it -> "6")

            result.status.findAll { [2, 14].contains(it.key) }
                    .collect { it -> it.value }
                    .every(it -> "0")
    }
}