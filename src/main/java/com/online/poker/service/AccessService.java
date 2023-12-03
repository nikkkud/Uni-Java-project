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

    //Add or Remove from GameSate
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

    //Step queue
    public boolean permit_to_step(String name, PlayerInput playerInput) {
        if (playerInput.Act.equals("start")) {
            gameService.game_start(gameState);
            return true;
        }
        


        int index = check_ontable(name);

        if (index == gameState.StepId) {


            if (playerInput.Act.equals("check")) {
                if (gameState.BiggestBet == gameState.PlayersBet.get(index)) {

                    gameService.process_step(gameState);
                    
                    return true;
                }else {
                    return false;
                }
            }else {
                
                if (playerInput.Act.equals("call")) {
                    gameService.process_step(gameState);
                }
                if (playerInput.Act.equals("bet")) {
                    gameService.process_step(gameState, playerInput.Bet);
                }

               
                return true;
            }


            // return true;
        }else {
            return false;
        }
    }
    public void player_disconnect(String name) {
        int index = check_ontable(name);

        if (index != -1) {
            gameState.FoldPlayers.set(
                check_ontable(name),
                "fl"
            );
        }

    }

}