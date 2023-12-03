package com.online.poker.service;

import org.springframework.stereotype.Service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;

import java.util.ArrayList;


@Service
public class AccessService {
    //Set Data
    public GameService gameService = new GameService();
    public GameState gameState = new GameState();
    private ArrayList<User> ALL_USERS = new ArrayList<User>();

    public AccessService() {
        ALL_USERS.add(new User("Ivan"  ,1000));
        ALL_USERS.add(new User("Nikita",1000));
        ALL_USERS.add(new User("Gleb"  ,1000));
        ALL_USERS.add(new User("Maria" ,1000));
        gameState.StepId = -2;
        gameState.BiggestBet = 0;
    }
    
    //Base operation for find
    public User find_by_name(String name) {
        User user = new User();
        for (User u: ALL_USERS) {
            if (name.equals(u.getName())) {
                user = u;
            }
        }
        return user;
    }
    public int check_onhall(String name) {
        int index = -1;
        for (int i=0; i < gameState.PlayersOnHall.size(); i++) {
            if (name.equals(gameState.PlayersOnHall.get(i).getName())) {
                index = i;
            }
        }
        return index;
    }
    public int check_ontable(String name) {
        int index = -1;
        for (int i=0; i < gameState.PlayersOnTable.size(); i++) {
            if (name.equals(gameState.PlayersOnTable.get(i).getName())) {
                index = i;
            }
        }
        return index;
    }

    //Add or Remove from GameState
    public void set_user_in_gamestate(String name, String hall_or_table) {
        if (hall_or_table == "hall") {
            gameState.PlayersOnHall.add(find_by_name(name));
        }
        if (hall_or_table== "table") {
            gameState.PlayersOnTable.add(find_by_name(name));
        }
    }
    public void remove_user_from_gamestate(int index, String hall_or_table) {
        if (hall_or_table == "hall") {
            gameState.PlayersOnHall.remove(index);
        }
        if (hall_or_table== "table") {
            gameState.PlayersOnTable.remove(index);
        }
    }
    public boolean check_all_fold() {
        int index = gameState.FoldPlayers.size();

        for (String fold: gameState.FoldPlayers) {
            if (fold.equals("f") || fold.equals("fl")) {
                index--;
            }
        }

        if (index <= 0) {
            return true;
        }else {
            return false;
        }
    }
    //Step queue
    public boolean permit_to_step(String name, PlayerInput playerInput) {

        if (playerInput.Act.equals("start")) {
            if (gameState.GameOver || check_all_fold()) {
                gameService.game_start(gameState);
                gameState.GameOver = false;
                return true; 
            }
            if (gameState.FoldPlayers.get(gameState.StepId) != "p") {
                gameService.move_stepid(gameState);
            }

        }

        int index = check_ontable(name);
        if (index == gameState.StepId) {

            if (playerInput.Act.equals("check")) {
                if (gameState.BiggestBet == gameState.PlayersBet.get(index)) {

                    gameService.put_money(gameState);
                    
                    return true;
                }else {
                    return false;
                }
            }else {

                if (gameState.GameOver == false) {
                    if (playerInput.Act.equals("call")) {
                        gameService.put_money(gameState);
                    }
                    if (playerInput.Act.equals("bet")) {
                        gameService.put_money(gameState, playerInput.Bet);
                    }
                    if (playerInput.Act.equals("fold")) {
                        gameService.fold(gameState);
                    }
                    return true;
                }
                return false;
            }


            // return true;
        }else {
            return false;
        }
    }
    public void player_disconnect(String name) {
        PlayerInput playerInput = new PlayerInput();
        playerInput.Act = "fold";
        playerInput.Bet = 0;

        permit_to_step(name, playerInput);

        int index_table = check_ontable(name);
        if (index_table != -1) {
            gameState.FoldPlayers.set(
                check_ontable(name),
                "fl"
            );
        }

        int index_hall = check_onhall(name);
        if (index_hall != -1) {
            remove_user_from_gamestate(index_hall, "hall");
        }
    }

    public OutputUserInfo getOutputUserInfo(String name) {
        OutputUserInfo output = new OutputUserInfo();
        output.Player = find_by_name(name);
        


        int index = check_ontable(name);
        if (index != -1) {
            output.Bet = gameState.PlayersBet.get(index);
            
            ArrayList<ArrayList<Card>> secret_cards = gameState.getPlayersCards();
            output.Cards = secret_cards.get(index);

            

            if (gameState.StepId == index) {
                output.StepTest = true;
            }else {
                output.StepTest = false;
            }
           

        }else {
            output.Bet = 0;
            ArrayList<Card> cards = new ArrayList<Card>();
            cards.add(new Card(0,'N'));
            cards.add(new Card(0,'N'));
            output.Cards = cards;

            output.StepTest = false;
        }


        
        
        return output;
    }

}