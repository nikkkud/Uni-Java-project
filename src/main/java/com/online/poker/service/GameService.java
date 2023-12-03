package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;


import java.util.Objects;
import java.util.ArrayList;

public class GameService {
    CardDeck cardDeck = new CardDeck();

    public void move_stepid(GameState gameState) {
        int size = gameState.PlayersOnTable.size();
        int index;
        int i = 0;
        while (i < size) {
            i++;

            gameState.CircleSize++;
            gameState.StepId++;

            if (gameState.StepId >= size) {
                gameState.StepId = 0;
            }

            index = gameState.StepId;
            if (gameState.FoldPlayers.get(index).equals("p")) {
                break;
            }
        }        
    }

    public void fold(GameState gameState) {
        int index = gameState.StepId;

        gameState.FoldPlayers.set(
            index,
            "f"
        );
        move_stepid(gameState);
    }

    public void put_money(GameState gameState) {
        call(gameState);
        move_stepid(gameState);
        end_round(gameState);
    }
    public void put_money(GameState gameState, int desiredBet) {
        bet(gameState,desiredBet);
        move_stepid(gameState);
        end_round(gameState);
    }

    public void call(GameState gameState) {
        int index = gameState.StepId;

        int raise = gameState.BiggestBet - gameState.PlayersBet.get(index);

        gameState.PlayersBet.set(
            index,
            gameState.BiggestBet
        );

        gameState.PlayersOnTable.get(index).Balance -= raise;
        gameState.Bank += raise;
    }

    public void bet(GameState gameState, int desiredBet) {
        call(gameState);

        int index = gameState.StepId;
        
        gameState.BiggestBet += desiredBet;
        gameState.PlayersBet.set(
            index,
            gameState.BiggestBet
        );

        gameState.PlayersOnTable.get(index).Balance -= desiredBet;
        gameState.Bank += desiredBet;
    }

    public void end_round(GameState gameState) {
        int size = gameState.PlayersOnTable.size();
        if (gameState.CircleSize >= size) {
            boolean end_test = true;
            for (int i = 0; i < size; i++) {
                if (gameState.PlayersBet.get(i) != gameState.BiggestBet && gameState.FoldPlayers.get(i) == "p") {
                    end_test = false;
                }
            }

            if (end_test) {
                //add_card
                gameState.CircleSize = 0;
                gameState.BiggestBet = 0;

                for (int i=0; i < size; i++) {
                    gameState.PlayersBet.set(i,0);
                }


                int card_number = gameState.CardsOnTable.size();
                if (card_number == 0) {
                    gameState.CardsOnTable.add(cardDeck.drawRandomCard());
                    gameState.CardsOnTable.add(cardDeck.drawRandomCard());
                    gameState.CardsOnTable.add(cardDeck.drawRandomCard());
                }
                if (card_number == 3 || card_number == 4) {
                    gameState.CardsOnTable.add(cardDeck.drawRandomCard());
                }
                if (card_number >= 5) {
                    //end
                    int index = 0;
                    gameState.OpenCards = gameState.getPlayersCards();
                    gameState.PlayersOnTable.get(index).Balance += gameState.Bank;
                    gameState.Bank = 0;
                    gameState.GameOver = true;
                }
                
            }
        }
    }



    public void game_start(GameState gameState){
        // System.out.println(gameState.FoldPlayers);
        for (int i = gameState.PlayersOnTable.size(); i > 0; i--) {
            if (gameState.FoldPlayers.get(0).equals("fl") == false) {
                
                gameState.PlayersOnHall.add(gameState.PlayersOnTable.get(0));
            }
        
            gameState.PlayersOnTable.remove(0);
            gameState.FoldPlayers.remove(0);
        }

        gameState.PlayersOnTable = new ArrayList<User>();
        gameState.PlayersBet = new ArrayList<Integer>();
        gameState.FoldPlayers = new ArrayList<String>();
        // gameState.setPlayersCards(new ArrayList<ArrayList<Card>>());
        gameState.OpenCards = new ArrayList<ArrayList<Card>>();

        gameState.CardsOnTable = new ArrayList<Card>();
        gameState.StepId = 0;
        gameState.BiggestBet = 0;
        gameState.Bank = 0;


        cardDeck.restoreDeck();
        ArrayList<ArrayList<Card>> secret_cards = new ArrayList<ArrayList<Card>>();

        for (int i = gameState.PlayersOnHall.size(); i > 0; i--){
            
            gameState.PlayersOnTable.add(gameState.PlayersOnHall.get(0));
            gameState.PlayersOnHall.remove(0);

            gameState.PlayersBet.add(0);
            gameState.FoldPlayers.add("p");
            
            
            // gameState.OpenCards.add();
            ArrayList<Card> card_hand = new ArrayList<Card>();
            card_hand.add(cardDeck.drawRandomCard());
            card_hand.add(cardDeck.drawRandomCard());

            secret_cards.add(card_hand);
        }

        gameState.setPlayersCards(secret_cards);
    }
}
