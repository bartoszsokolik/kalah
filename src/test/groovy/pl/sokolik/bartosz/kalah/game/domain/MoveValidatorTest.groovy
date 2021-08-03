package pl.sokolik.bartosz.kalah.game.domain

import pl.sokolik.bartosz.kalah.game.domain.exception.MoveValidationException
import spock.lang.Specification
import spock.lang.Subject

class MoveValidatorTest extends Specification {

    @Subject
    MoveValidator moveValidator = new MoveValidator()

    def "should throw exception when player try to start move from other players pit"() {
        given:
            def game = new Game()

        when:
            moveValidator.validate(game, 12)

        then:
            def ex = thrown MoveValidationException
            ex.getMessage() == 'Current player is not an owner of pit with id 12'
    }

    def "should throw exception when player try to start move from kalah"() {
        given:
            def game = new Game()

        when:
            moveValidator.validate(game, 7)

        then:
            def ex = thrown MoveValidationException
            ex.getMessage() == 'Cannot start move from kalah pit'
    }

    def "should throw exception when player try to start move from empty pit"() {
        given:
            def game = new Game()
            game.getBoard().clearBasePits()

        when:
            moveValidator.validate(game, 1)

        then:
            def ex = thrown MoveValidationException
            ex.getMessage() == 'Cannot start move from empty pit'
    }

    def "should not throw exception for valid move"() {
        given:
            def game = new Game()

        when:
            moveValidator.validate(game, 1)

        then:
            noExceptionThrown()
    }
}
