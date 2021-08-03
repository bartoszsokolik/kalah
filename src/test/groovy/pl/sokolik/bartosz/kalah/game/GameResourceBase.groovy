package pl.sokolik.bartosz.kalah.game


import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.test.web.servlet.MockMvc
import pl.sokolik.bartosz.kalah.game.domain.GameService
import pl.sokolik.bartosz.kalah.game.domain.dto.GameDto
import spock.lang.Specification

import static io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc

@AutoConfigureMessageVerifier
@WebMvcTest(controllers = GameResource.class)
class GameResourceBase extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    GameService gameService = Mock()

    def setup() {
        mockMvc(mockMvc)
        def gameId = UUID.fromString("aba98aa8-5c0a-435c-9256-764aef8677b6")
        gameService.create() >> GameDto.builder()
                .id(gameId)
                .uri("http://localhost:8080/games/${gameId}".toString())
                .build()

        gameService.move(gameId, 1) >> GameDto.builder()
                .id(gameId)
                .uri("http://localhost:8080/games/${gameId}".toString())
                .status([1 : "0",
                         2 : "7",
                         3 : "7",
                         4 : "7",
                         5 : "7",
                         6 : "7",
                         7 : "1",
                         8 : "6",
                         9 : "6",
                         10: "6",
                         11: "6",
                         12: "6",
                         13: "6",
                         14: "0"]
                )
                .build()
    }
}
