package pl.sokolik.bartosz.kalah.game.domain

import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.IntStream

import static pl.sokolik.bartosz.kalah.game.domain.Player.FIRST_PLAYER
import static pl.sokolik.bartosz.kalah.game.domain.Player.SECOND_PLAYER

class MoveExecutorTest extends Specification {

    @Subject
    MoveExecutor moveExecutor = new MoveExecutor()

    def "should make a move and not perform player change"() {
        given:
            def game = new Game()

        when:
            moveExecutor.move(game, 1)

        then:
            def increasedPits = [2, 3, 4, 5, 6, 7] as List
            def unchangedPits = [8, 9 ,10, 11, 12, 13] as List
            def emptyPits = [1, 14] as List

            sumStonesInPits(game, increasedPits) == 36
            sumStonesInPits(game, unchangedPits) == 36
            sumStonesInPits(game, emptyPits) == 0
            game.currentPlayer == FIRST_PLAYER
    }

    def "should make a move and change current player"() {
        given:
            def game = new Game()

        when:
            moveExecutor.move(game, 2)

        then:
            def increasedPits = [3, 4, 5, 6, 7, 8] as List
            def unchangedPits = [1, 9 ,10, 11, 12, 13] as List
            def emptyPits = [2, 14] as List

            sumStonesInPits(game, increasedPits) == 36
            sumStonesInPits(game, unchangedPits) == 36
            sumStonesInPits(game, emptyPits) == 0
            game.currentPlayer == SECOND_PLAYER
    }

    def "should make a move and not put stone to second players kalah"() {
        given:
            def game = new Game()
            game.getBoard().getPit(6).updateStonesNumber(8)

        when:
            moveExecutor.move(game, 6)

        then:
            def increasedPits = [1, 7, 8, 9, 10, 11, 12, 13] as List
            def unchangedPits = [2, 3, 4, 5] as List
            def emptyPits = [6, 14] as List

            sumStonesInPits(game, increasedPits) == 50
            sumStonesInPits(game, unchangedPits) == 24
            sumStonesInPits(game, emptyPits) == 0
    }

    def "should make a move and put stone to first players kalah"() {
        given:
            def game = new Game()
            game.setCurrentPlayer(SECOND_PLAYER)
            game.getBoard().getPit(13).updateStonesNumber(8)

        when:
            moveExecutor.move(game, 13)

        then:
            def increasedPits = [1, 2, 3, 4, 5, 6, 8, 14] as List
            def unchangedPits = [9, 10, 11, 12] as List
            def emptyPits = [7, 13] as List

            sumStonesInPits(game, increasedPits) == 50
            sumStonesInPits(game, unchangedPits) == 24
            sumStonesInPits(game, emptyPits) == 0
    }

    def "should make a move and get stones from opposite pit"() {
        given:
            def game = new Game()
            game.getBoard().getPit(1).updateStonesNumber(0)
            game.getBoard().getPit(6).updateStonesNumber(8)

        when:
            moveExecutor.move(game, 6)

        then:
            game.getBoard().getPit(7).getStonesNumber() == 9
            game.getBoard().getPit(13).getStonesNumber() == 0
            game.getBoard().getPit(1).getStonesNumber() == 0
            game.getCurrentPlayer() == SECOND_PLAYER
    }

    def "should make a move and check and get a winner"() {
        given:
            def game = new Game()
            IntStream.rangeClosed(1, 5)
                    .forEach(number -> updateStonesNumber(game, number, 0))
            IntStream.rangeClosed(8, 13)
                    .forEach(number -> updateStonesNumber(game, number, 1))

            updateStonesNumber(game, 6, 1)
            updateStonesNumber(game, 7, 15)
            updateStonesNumber(game, 14, 12)

        when:
            moveExecutor.move(game, 6)

        then:
            game.getBoard().getPit(7).getStonesNumber() == 16
            game.getBoard().getPit(14).getStonesNumber() == 18
            game.getWinner() == SECOND_PLAYER
    }

    private void updateStonesNumber(Game game, int pitNumber, int stonesNumber) {
        game.getBoard().getPit(pitNumber).updateStonesNumber(stonesNumber)
    }

    private int sumStonesInPits(Game game, List<Integer> pitNumbers) {
        return game.getBoard()
                .getPits()
                .stream()
                .filter(pit -> pitNumbers.contains(pit.getNumber()))
                .mapToInt(pit -> pit.getStonesNumber())
                .sum()
    }
}
