package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.AccessService;

import static org.junit.jupiter.api.Assertions.*;
import com.online.poker.service.CardDeck;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import com.online.poker.repository.Card;
public class CardDeckTest {
    @Test
    public void deckInitializationTest(){
        CardDeck deck = new CardDeck();
        assertEquals(52, deck.getDeckSize());
    }

    @Test
    public void uniqueCardsInDeck(){
        CardDeck deck = new CardDeck();
        Set<Card> uniqueCards = new HashSet<>(deck.getDeck());
        assertEquals(52, uniqueCards.size());
    }

    @Test
    public void drawRandomCardTest() {
        CardDeck deck = new CardDeck();
        Card drawnCard = deck.drawRandomCard();
        assertNotNull(drawnCard);
        assertTrue(deck.getDeckSize() < 52);
    }

    @Test
    public void testRestoreDeck() {
        CardDeck deck = new CardDeck();
        deck.drawRandomCard();
        deck.restoreDeck();
        assertEquals(52, deck.getDeckSize());
    }

    @Test
    public void testGetDeckSize() {
        CardDeck deck = new CardDeck();
        deck.drawRandomCard();
        assertEquals(51, deck.getDeckSize());
    }

}
