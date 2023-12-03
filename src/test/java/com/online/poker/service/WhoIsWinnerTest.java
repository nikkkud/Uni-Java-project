package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.AccessService;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhoIsWinnerTest {

    @Test
    public void royalFlushHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        // Act
        ArrayList<Card> royalFlushCards = new ArrayList<>();

        // Royal Flush
        royalFlushCards.add(new Card(10, 'S'));
        royalFlushCards.add(new Card(11, 'S'));
        royalFlushCards.add(new Card(12, 'S'));
        royalFlushCards.add(new Card(2, 'D'));
        royalFlushCards.add(new Card(13, 'S'));
        royalFlushCards.add(new Card(1, 'S')); // Туз
        royalFlushCards.add(new Card(3, 'C'));

        int result = service.evaluateHand(royalFlushCards);

        // Assert
        assertEquals(10, result);
    }

    @Test
    public void straightFlushHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> straightFlushCards = new ArrayList<>();

        straightFlushCards.add(new Card(3, 'H'));
        straightFlushCards.add(new Card(4, 'H'));
        straightFlushCards.add(new Card(5, 'H'));
        straightFlushCards.add(new Card(1, 'S')); // Туз
        straightFlushCards.add(new Card(3, 'C'));
        straightFlushCards.add(new Card(6, 'H'));
        straightFlushCards.add(new Card(7, 'H'));

        int result = service.evaluateHand(straightFlushCards);

        // Assert
        assertEquals(9, result);
    }

    @Test
    public void FourOfAKindHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> forOfaKindCards = new ArrayList<>();

        forOfaKindCards.add(new Card(3, 'H'));
        forOfaKindCards.add(new Card(4, 'H'));
        forOfaKindCards.add(new Card(5, 'H'));
        forOfaKindCards.add(new Card(1, 'S')); // Туз
        forOfaKindCards.add(new Card(3, 'C'));
        forOfaKindCards.add(new Card(3, 'D'));
        forOfaKindCards.add(new Card(3, 'S'));
        int result = service.evaluateHand(forOfaKindCards);

        // Assert
        assertEquals(8, result);
    }

    @Test
    public void FullHouseHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> fullHouseCards = new ArrayList<>();

        fullHouseCards.add(new Card(4, 'H'));
        fullHouseCards.add(new Card(4, 'C'));
        fullHouseCards.add(new Card(5, 'H'));
        fullHouseCards.add(new Card(13, 'S')); // Туз
        fullHouseCards.add(new Card(3, 'C'));
        fullHouseCards.add(new Card(13, 'D'));
        fullHouseCards.add(new Card(13, 'H'));
        int result = service.evaluateHand(fullHouseCards);

        // Assert
        assertEquals(7, result);
    }

    @Test
    public void flushHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> flushCards = new ArrayList<>();
        flushCards.add(new Card(4, 'H'));
        flushCards.add(new Card(5, 'H'));
        flushCards.add(new Card(2, 'H'));
        flushCards.add(new Card(13, 'S')); // Туз
        flushCards.add(new Card(1, 'H'));
        flushCards.add(new Card(13, 'D'));
        flushCards.add(new Card(13, 'H'));
        int result = service.evaluateHand(flushCards);

        // Assert
        assertEquals(6, result);
    }

    @Test
    public void straightHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> straightCards = new ArrayList<>();
        straightCards.add(new Card(6, 'S'));
        straightCards.add(new Card(5, 'H'));
        straightCards.add(new Card(7, 'D'));
        straightCards.add(new Card(8, 'C')); // Туз
        straightCards.add(new Card(9, 'D'));
        straightCards.add(new Card(10, 'H'));
        straightCards.add(new Card(13, 'H'));

        int result = service.evaluateHand(straightCards);

        // Assert
        assertEquals(5, result);
    }

    @Test
    public void threeOfKindHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> threeOfKindCards = new ArrayList<>();
        threeOfKindCards.add(new Card(6, 'S'));
        threeOfKindCards.add(new Card(5, 'H'));
        threeOfKindCards.add(new Card(13, 'D'));
        threeOfKindCards.add(new Card(8, 'C')); // Туз
        threeOfKindCards.add(new Card(9, 'D'));
        threeOfKindCards.add(new Card(13, 'H'));
        threeOfKindCards.add(new Card(13, 'S'));

        int result = service.evaluateHand(threeOfKindCards);

        // Assert
        assertEquals(4, result);
    }

    @Test
    public void twoPairsHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> twoPairsCards = new ArrayList<>();
        twoPairsCards.add(new Card(1, 'C'));
        twoPairsCards.add(new Card(4, 'S'));
        twoPairsCards.add(new Card(6, 'S'));
        twoPairsCards.add(new Card(6, 'C')); // Туз
        twoPairsCards.add(new Card(8, 'H'));
        twoPairsCards.add(new Card(5, 'H'));
        twoPairsCards.add(new Card(1, 'S'));

        int result = service.evaluateHand(twoPairsCards);

        // Assert
        assertEquals(3, result);
    }

    @Test
    public void onePairHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> onePairCards = new ArrayList<>();
        onePairCards.add(new Card(6, 'S'));
        onePairCards.add(new Card(5, 'H'));
        onePairCards.add(new Card(8, 'D'));
        onePairCards.add(new Card(8, 'C')); // Туз
        onePairCards.add(new Card(9, 'D'));
        onePairCards.add(new Card(10, 'H'));
        onePairCards.add(new Card(13, 'S'));

        int result = service.evaluateHand(onePairCards);

        // Assert
        assertEquals(2, result);
    }

    @Test
    public void higherRankHandTest() {
        WhoIsWinner service = new WhoIsWinner();

        ArrayList<Card> higherRankCards = new ArrayList<>();
        higherRankCards.add(new Card(6, 'S'));
        higherRankCards.add(new Card(5, 'H'));
        higherRankCards.add(new Card(3, 'D'));
        higherRankCards.add(new Card(8, 'C')); // Туз
        higherRankCards.add(new Card(9, 'D'));
        higherRankCards.add(new Card(10, 'H'));
        higherRankCards.add(new Card(13, 'S'));

        int result = service.evaluateHand(higherRankCards);

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void WhoWonTest() {
        WhoIsWinner service = new WhoIsWinner();

        // player1
        ArrayList<Card> player1Cards = new ArrayList<>();
        player1Cards.add(new Card(12, 'C'));
        player1Cards.add(new Card(11, 'D'));

        //player2 should win

        ArrayList<Card> player2Cards = new ArrayList<>();
        player2Cards.add(new Card(13, 'H'));
        player2Cards.add(new Card(11, 'H'));

        ArrayList<Card> cardsOnTable = new ArrayList<>();
        cardsOnTable.add(new Card(11, 'C'));
        cardsOnTable.add(new Card(2, 'C'));
        cardsOnTable.add(new Card(3, 'C'));
        cardsOnTable.add(new Card(8, 'H'));
        cardsOnTable.add(new Card(6, 'D'));

        GameState gameState = new GameState();
        ArrayList<ArrayList<Card>> playersCards = new ArrayList<>();
        playersCards.add(player1Cards);
        playersCards.add(player2Cards);
        gameState.setPlayersCards(playersCards);
        gameState.setCardsOnTable(cardsOnTable);

        int result = service.getWinner(gameState);

        // Assert
        assertEquals(1, result);
    }


}