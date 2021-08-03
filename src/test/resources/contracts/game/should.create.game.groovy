package contracts.game

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url '/games'
        method POST()
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body(""" 
            {
                    "id": "aba98aa8-5c0a-435c-9256-764aef8677b6",
                    "uri": "http://localhost:8080/games/aba98aa8-5c0a-435c-9256-764aef8677b6"
            }
        """
        )
    }
}