## About
*Java RESTful Web Service* that runs a game of 6-stone Kalah.

## Usage
To run tests go to root project directory and run command `./mvnw clean test`

To run application use `./mvnw spring-boot:run`

Game creation
```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```

Make a move
```
curl --header "Content-Type: application/json" --request PUT http://localhost:8080/games/{gameId}/pits/{pitNumber}
```

Swagger docs available at `http://localhost:8080/swagger-ui/`