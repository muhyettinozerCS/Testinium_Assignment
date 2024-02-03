package com.trello.test;

import com.trello.api.TrelloApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TrelloTest {

    private static String boardId;

    @BeforeAll
    public static void setup() {
        boardId = TrelloApi.createBoard("MyNewBoard");
        assumeTrue(boardId != null, "BoardId is null. Skipping tests.");
    }

    @Test
    public void testAddCardsToBoard() {
        String cardId1 = TrelloApi.createCard(boardId, "Card1");
        assertNotNull(cardId1);

        String cardId2 = TrelloApi.createCard(boardId, "Card2");
        assertNotNull(cardId2);

        // Add logical assertions here
    }

    @Test
    public void testUpdateRandomCard() {
        String updatedCardName = "UpdatedCard";
        String updatedCardId = TrelloApi.updateRandomCard(boardId, updatedCardName);
        assertNotNull(updatedCardId);

        // Add logical assertions here
    }

    @Test
    public void testDeleteCards() {
        String cardId1 = TrelloApi.createCard(boardId, "Card1");
        String cardId2 = TrelloApi.createCard(boardId, "Card2");

        // Delete the created cards
        TrelloApi.deleteCard(cardId1);
        TrelloApi.deleteCard(cardId2);

        // Add logical assertions here
    }

    @Test
    public void testDeleteBoard() {
        // Delete the created board
        TrelloApi.deleteBoard(boardId);

        // Add logical assertions here
    }
}
