package com.online.poker;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import org.springframework.messaging.simp.annotation.SendToUser;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import org.springframework.scheduling.annotation.Async;

@Controller
public class GameController {
    private GameState gameState = new GameState();
    private SimpMessagingTemplate messagingTemplate;
    
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String name = event.getUser().getName();

        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                gameState.PlayersOnTable.remove(i);
                break;
            }
        }

        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                gameState.PlayersOnHall.remove(i);
                break;
            }
        }

        messagingTemplate.convertAndSend("/topic/greetings", gameState);
    }



    @GetMapping("/")
    public String nothing() {
        return "redirect:table";
    }


    @GetMapping("/table")
    public String table(Principal principal) {
        String name = principal.getName();

        
        boolean one_user_test = true;
        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        
        if (one_user_test) {
            return "table";
        }else {
            return "redirect:error";
        }
    }



    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GameState greeting(Principal principal, PlayerInput playerInput) throws Exception {
        Thread.sleep(1000); // simulated delay
        return gameState;
    }

    @MessageMapping("/getinfo")
    @SendToUser("/queue/get_user_interface")
    public User getUserInfo(Principal principal) {
        String name = principal.getName();

        User player = new User();
        player.Name = name;
        player.Balance = 9999;


        boolean one_user_test = true;
        for (int i = 0; i < gameState.PlayersOnTable.size(); i++) {
            if (gameState.PlayersOnTable.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }
        for (int i = 0; i < gameState.PlayersOnHall.size(); i++) {
            if (gameState.PlayersOnHall.get(i).Name.equals(name)) {
                one_user_test = false;
                break;
            }
        }

        if (one_user_test) {
            gameState.PlayersOnHall.add(player);
        }
        
        System.out.println("\n\ntttttttttttttttttttttttttttttttttttttt\n\n");
        return player;
    }

}