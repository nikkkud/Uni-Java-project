package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;


import java.util.Objects;
import java.util.ArrayList;

public class GameService {
    

    public void move_stepid(GameState gameState) {
        int size = gameState.PlayersOnTable.size();
        gameState.CircleSize++;
        gameState.StepId++;
        if (gameState.StepId >= size) {
            gameState.StepId = 0;
        }
        
    }

    public void process_step(GameState gameState) {
        call(gameState);
        move_stepid(gameState);
        end_round(gameState);
    }
    public void process_step(GameState gameState, int desiredBet) {
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

    }

    public void end_round(GameState gameState) {
        int size = gameState.PlayersOnTable.size();
        if (gameState.CircleSize > size) {
            boolean end_test = true;
            for (int b: gameState.PlayersBet) {
                if (b != gameState.BiggestBet) {
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

                gameState.CardsOnTable.add(new Card(5,'H'));

            }
        }
    }



    public void game_start(GameState gameState){
        // System.out.println(gameState.FoldPlayers);
        for (int i = gameState.PlayersOnTable.size(); i > 0; i--) {
            if (gameState.FoldPlayers.get(0).equals("fl") == false) {
                
                gameState.PlayersOnHall.add(gameState.PlayersOnTable.get(0));
            }
        
            gameState.PlayersBet.remove(0);
            gameState.PlayersOnTable.remove(0);
            gameState.FoldPlayers.remove(0);
        }

        // gameState.PlayersOnTable = new ArrayList<User>();
        // gameState.PlayersBet = new ArrayList<Integer>();
        // gameState.FoldPlayers = new ArrayList<String>();
        gameState.CardsOnTable = new ArrayList<Card>();
        gameState.StepId = 0;
        gameState.BiggestBet = 0;

        for (int i = gameState.PlayersOnHall.size(); i > 0; i--){
            
            gameState.PlayersOnTable.add(gameState.PlayersOnHall.get(0));
            gameState.PlayersOnHall.remove(0);

            gameState.PlayersBet.add(0);
            gameState.FoldPlayers.add("p");
            
        }
    }
}
