package com.trello.test;

import com.trello.api.TrelloApi;
import com.trello.utils.CustomList;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrelloTest {

    private static String boardId;
    private static String listId;
    private static String firstCardId;
    private static String secondCardId;
    private static boolean isBoardCreated = true;
    private static boolean isListCreated = true;

    @Test
    @Order(0)
    public void createBoard() {
        String boardNameToCreate = "Yeni Panom";
        CustomList customDataList = TrelloApi.createBoard(boardNameToCreate);
        boardId = customDataList.getId();
        String createdBoardName = customDataList.getName();

        if (boardId == null) {
            System.out.println("BoardId is null. Skipping tests.");
            isBoardCreated = false;
            Assertions.fail("Board creation failed.");
        }
        Assertions.assertEquals(createdBoardName, boardNameToCreate);
        System.out.println("Board creation is successfull, with board name: " + createdBoardName);
    }

    @Test
    @Order(1)
    public void addListToBoard() {
        assumeTrue(isBoardCreated);
        String listNameToCreate = "Liste";
        CustomList customDataList = TrelloApi.createList(boardId, listNameToCreate);
        listId = customDataList.getId();
        if (listId == null) {
            System.out.println("List creation failed. Skipping remaining tests.");
            isListCreated = false;
            Assertions.fail("List creation failed.");
        }
        String createdListName = customDataList.getName();
        Assertions.assertEquals(createdListName, listNameToCreate);
        System.out.println("List creation is successfull, with list name: " + createdListName);
    }

    @Test
    @Order(2)
    public void addCardsToList() {
        assumeTrue(isListCreated);

        String firstCardNameToCreate = "İlk Kart";
        String secondCardNameToCreate = "İkinci Kart";
        CustomList customDataListForFirstCard = TrelloApi.createCard(listId, firstCardNameToCreate);
        CustomList customDataListForSecondCard = TrelloApi.createCard(listId, secondCardNameToCreate);

        String createdFirstCardName = customDataListForFirstCard.getName();
        firstCardId = customDataListForFirstCard.getId();
        String createdSecondCardName = customDataListForSecondCard.getName();
        secondCardId = customDataListForSecondCard.getId();

        Assertions.assertEquals(firstCardNameToCreate, createdFirstCardName);
        Assertions.assertEquals(secondCardNameToCreate, createdSecondCardName);
        System.out.println("Two card is added to the list successfully");
    }

    @Test
    @Order(3)
    public void updateRandomCard() {
        String updatedCardName = "UpdatedCard";
        String updatedCardId = TrelloApi.updateRandomCard(boardId, updatedCardName);
        Assertions.assertNotNull(updatedCardId);
        System.out.printf("Updated card name to %s successfully%n", updatedCardName);
    }

    @Test
    @Order(4)
    public void deleteCards() {
        TrelloApi.deleteCard(firstCardId);
        TrelloApi.deleteCard(secondCardId);
        System.out.println("Deleted cards successfully");
    }

    @Test
    @Order(5)
    public void deleteBoard() {
        TrelloApi.deleteBoard(boardId);
        System.out.println("Deleted board successfully");
    }
}