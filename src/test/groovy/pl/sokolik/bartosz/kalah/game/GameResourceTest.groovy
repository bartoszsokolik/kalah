package pl.sokolik.bartosz.kalah.game

import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pl.sokolik.bartosz.kalah.game.domain.GameService
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.hamcrest.Matchers.not
import static org.hamcrest.Matchers.emptyString
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.nullValue
import static org.hamcrest.core.StringContains.containsString
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.CREATED

@SpringBootTest(webEnvironment = RANDOM_PORT)
class GameResourceTest extends Specification {

    @Autowired
    GameService gameService

    @LocalServerPort
    int port;

    def "should create game"() {
        when:
            def response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .post("/games")

        then:
            response.then()
                    .statusCode(CREATED.value())
                    .body("id", notNullValue())
                    .body("uri", is(not(emptyString())))
                    .body("uri", containsString("http://localhost:0/games"))
                    .body("status", nullValue())
    }

    def "should make a move"() {
        given:
            def game = gameService.create()

        when:
            def response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .put("/games/{id}/pits/{pitId}", game.getId(), 1)

        then:
            response.then()
                    .statusCode(OK.value())
                    .body("id", equalTo(game.getId().toString()))
                    .body("uri", equalTo("http://localhost:0/games/${game.getId()}".toString()))
                    .body("status.1", equalTo("0"))
                    .body("status.2", equalTo("7"))
                    .body("status.3", equalTo("7"))
                    .body("status.4", equalTo("7"))
                    .body("status.5", equalTo("7"))
                    .body("status.6", equalTo("7"))
                    .body("status.7", equalTo("1"))
                    .body("status.8", equalTo("6"))
                    .body("status.9", equalTo("6"))
                    .body("status.10", equalTo("6"))
                    .body("status.11", equalTo("6"))
                    .body("status.12", equalTo("6"))
                    .body("status.13", equalTo("6"))
                    .body("status.14", equalTo("0"))
    }

    def "should throw exception when player try to start move from other players pit"() {
        given:
            def game = gameService.create()

        when:
            def response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .put("/games/{id}/pits/{pitId}", game.getId(), 12)

        then:
            response.then()
                    .statusCode(BAD_REQUEST.value())
                    .body("errorId", notNullValue())
                    .body("message", equalTo("Current player is not an owner of pit with id 12"))
    }

    def "sshould throw exception when player try to start move from kalah"() {
        given:
            def game = gameService.create()

        when:
            def response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .put("/games/{id}/pits/{pitId}", game.getId(), 7)

        then:
            response.then()
                    .statusCode(BAD_REQUEST.value())
                    .body("errorId", notNullValue())
                    .body("message", equalTo("Cannot start move from kalah pit"))
    }

    def "should throw exception when player try to start move from empty pit"() {
        given:
            def game = gameService.create()
            gameService.move(game.getId(), 1)

        when:
            def response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .put("/games/{id}/pits/{pitId}", game.getId(), 1)

        then:
            response.then()
                    .statusCode(BAD_REQUEST.value())
                    .body("errorId", notNullValue())
                    .body("message", equalTo("Cannot start move from empty pit"))
    }
}
