package com.online.poker.service;

import com.online.poker.repository.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CardDeck {
    private List<Card> deck;

    public CardDeck() {
        initializeDeck();
    }

    private void initializeDeck() {
        deck = new ArrayList<>();

        char[] suits = {'H', 'D', 'C', 'S'};
        for (char suit : suits) {
            for (int number = 1; number <= 13; number++) {
                deck.add(new Card(number, suit));
            }
        }
    }

    public Card drawRandomCard() {
        if (deck.isEmpty()) {
            return null; // Возвращаем null, если колода пуста
        }

        int randomIndex = (int) (Math.random() * deck.size());
        Card drawnCard = deck.remove(randomIndex);
        return drawnCard;
    }

    public void restoreDeck() {
        initializeDeck();
    }

    public int getDeckSize() {
        return deck.size();
    }

}
