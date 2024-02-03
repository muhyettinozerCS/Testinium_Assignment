package com.trello.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class TrelloApi {

    private static final String API_KEY = "c94701bd04067428cec20d2007026a9f";
    private static final String TOKEN = "ATTAd4aae83e55cc81504c6f523c2665601f16170e1bb73e36defa6efa61c3dbe054B865F3D7";



    public static String createBoard(String boardName) {
        Response response = given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", boardName)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/");

        response.then()
                .statusCode(200)
                .body("name", Matchers.equalTo(boardName)); // Use equalTo method for body assertion

        return response.jsonPath().getString("id");
    }



    public static String createCard(String boardId, String cardName) {
        Response response = given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", cardName)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/" + boardId + "/cards");

        String cardId = response.then()
                .statusCode(200)
                .extract()
                .path("id");

        return cardId;
    }

    public static String updateRandomCard(String boardId, String updatedCardName) {
        // Get the list of cards on the board
        Response response = given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("https://api.trello.com/1/boards/" + boardId + "/cards");

        // Extract the cardId of a random card from the response
        String cardId = (String) response.jsonPath().getList("id").get(new Random().nextInt(response.jsonPath().getList("id").size()));

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
        // Delete the specified card
        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/cards/" + cardId)
                .then()
                .statusCode(200);
    }

    public static void deleteBoard(String boardId) {
        // Delete the specified board
        given()
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/boards/" + boardId)
                .then()
                .statusCode(200);
    }
}
