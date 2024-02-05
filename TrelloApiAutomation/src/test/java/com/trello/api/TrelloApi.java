package com.trello.api;

import com.trello.utils.Config;
import com.trello.utils.CustomList;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class TrelloApi {

    private static final String API_KEY = Config.getApiKey();
    private static final String TOKEN = Config.getToken();

    public static CustomList createBoard(String boardName) {
        Response response = given()
                .queryParam("name", boardName)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/");

        ExtractableResponse<Response> extractableResponse = response.then()
                .statusCode(200)
                .extract();

        String boardId = extractableResponse.path("id");
        String createdBoardName = extractableResponse.path("name");

        return new CustomList(boardId, createdBoardName);
    }

    public static CustomList createList(String boardId, String listName) {
        Response response = given()
                .queryParam("name", listName)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/" + boardId + "/lists");

        String id = response.jsonPath().getString("id");
        String createdListName = response.jsonPath().getString("name");

        return new CustomList(id, createdListName);
    }

    public static CustomList createCard(String listId, String cardName) {
        Response response = given()
                .queryParam("name", cardName)
                .queryParam("idList", listId)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/cards");

        ExtractableResponse<Response> extractableResponse = response.then()
                .statusCode(200)
                .extract();

        String cardId = extractableResponse.path("id");
        String createdCardName = extractableResponse.path("name");

        return new CustomList(cardId, createdCardName);
    }

    public static String updateRandomCard(String boardId, String updatedCardName) {
        // Get the list of cards on the board
        Response response = given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/cards");

        // Extract the cardId of a random card from the response
        Random rand = new Random();
        String cardId = (String) response.jsonPath().getList("id").get(rand.nextInt(response.jsonPath().getList("id").size()));

        // Update the card with the new name
        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", updatedCardName)
                .contentType(ContentType.JSON)
                .when()
                .put("https://api.trello.com/1/cards/" + cardId)
                .then()
                .statusCode(200);

        return cardId;
    }

    public static void deleteCard(String cardId) {
        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/cards/" + cardId)
                .then()
                .statusCode(200);
    }

    public static void deleteBoard(String boardId) {
        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);
    }
}