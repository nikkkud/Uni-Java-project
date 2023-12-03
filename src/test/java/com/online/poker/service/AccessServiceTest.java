package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.AccessService;




import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.online.poker.service.AccessService;
import com.online.poker.repository.User;
import com.online.poker.repository.GameState;

import java.util.ArrayList;

public class AccessServiceTest {

    @Test
    public void createUserTest(){
        User user = new User("Gleb", 1000);
        assertNotNull(user);
    }

    @Test
    public void testFindByNameExistingUser() {
        AccessService accessService = new AccessService();


        String userNameToCheck = "Nikita";

        User foundUser = accessService.find_by_name(userNameToCheck);

        assertNotNull(foundUser);
        assertEquals(userNameToCheck, foundUser.getName());
    }

    @Test
    public void checkOnTableTest(){
        AccessService accessService = new AccessService();
        GameState gameState = accessService.gameState;

        User user1 = new User("User1", 1000);
        User user2 = new User("User2", 1000);

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        gameState.setPlayersOnTable(users);

        int index = accessService.check_ontable("User2");
        assertEquals(1, index);

    }

    @Test
    public void checkOnHallTest(){
        AccessService accessService = new AccessService();
        GameState gameState = accessService.gameState;

        User user1 = new User("User1", 1000);
        User user2 = new User("User2", 1000);

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        gameState.setPlayersOnHall(users);

        int index = accessService.check_onhall("User1");
        assertEquals(0, index);

    }

    @Test
    public void testAddUserToHall() {
        AccessService accessService = new AccessService();
        GameState gameState = accessService.gameState;

        String playerName = "Nikita";
        String hallOrTable = "hall";

        accessService.set_user_in_gamestate(playerName, hallOrTable);

        ArrayList<User> playersOnHall = accessService.gameState.PlayersOnHall;

        assertTrue(playersOnHall.stream().anyMatch(user -> user.getName().equals(playerName)));

        assertFalse(accessService.gameState.PlayersOnTable.stream().anyMatch(user -> user.getName().equals(playerName)));
    }

    @Test
    public void testAddUserToTable() {
        AccessService accessService = new AccessService();

        String playerName = "Nikita";
        String hallOrTable = "table";

        accessService.set_user_in_gamestate(playerName, hallOrTable);

        ArrayList<User> playersOnHall = accessService.gameState.PlayersOnHall;

        assertFalse(playersOnHall.stream().anyMatch(user -> user.getName().equals(playerName)));

        assertTrue(accessService.gameState.PlayersOnTable.stream().anyMatch(user -> user.getName().equals(playerName)));
    }

    @Test
    public void testRemoveUserFromGameState() {
        AccessService accessService = new AccessService();
        GameState gameState = accessService.gameState;

        String playerName = "Nikita";
        int playerNameIndex = 1;
        String hallOrTable = "Table";

        accessService.remove_user_from_gamestate(playerNameIndex, hallOrTable);

        ArrayList<User> playersOnTable = accessService.gameState.PlayersOnTable;

        assertFalse(playersOnTable.stream().anyMatch(user -> user.getName().equals(playerName)));

    }

    @Test
    public void testCheckAllFoldAllPlayers() {
        AccessService accessService = new AccessService();

        for (int i = 0; i < accessService.gameState.FoldPlayers.size(); i++) {
            accessService.gameState.FoldPlayers.set(i, "f");
        }

        assertTrue(accessService.check_all_fold());
    }


}
