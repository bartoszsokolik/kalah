package contracts.game

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        url '/games/aba98aa8-5c0a-435c-9256-764aef8677b6/pits/1'
        method PUT()
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(""" 
            {
                    "id": "aba98aa8-5c0a-435c-9256-764aef8677b6",
                    "uri": "http://localhost:8080/games/aba98aa8-5c0a-435c-9256-764aef8677b6",
                    "status": {
                        "1": "0",
                        "2": "7",
                        "3": "7",
                        "4": "7",
                        "5": "7",
                        "6": "7",
                        "7": "1",
                        "8": "6",
                        "9": "6",
                        "10": "6",
                        "11": "6",
                        "12": "6",
                        "13": "6",
                        "14": "0",
                    }
            }
        """
        )
    }
}