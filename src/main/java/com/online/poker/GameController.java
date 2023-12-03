package com.online.poker;

//Our classes
import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.AccessService;

import java.util.ArrayList;

//Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import org.springframework.messaging.simp.annotation.SendToUser;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import org.springframework.messaging.simp.SimpMessagingTemplate;


@Controller
public class GameController {

    //Set Options Service and Data
    private SimpMessagingTemplate messagingTemplate;
    private AccessService accessService = new AccessService();
    // private GameState gameState = new GameState();
    // private ArrayList<User> ALL_USERS = new ArrayList<User>();
    
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        
        // ALL_USERS.add(new User(1,"Ivan"  ,1000));
        // ALL_USERS.add(new User(2,"Nikita",1000));
        // ALL_USERS.add(new User(3,"Gleb"  ,1000));
        // ALL_USERS.add(new User(4,"Maria" ,1000));
    }
    
    //Get user disconnect
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String name = event.getUser().getName();

        int index_hall = accessService.check_onhall(name);
        int index_table = accessService.check_ontable(name);
        if (index_hall != -1) {
            accessService.remove_user_from_gamestate(index_hall, "hall");
        }
        accessService.player_disconnect(name);

        messagingTemplate.convertAndSend("/topic/greetings", accessService.gameState);
    }

    //HTTP links
    @GetMapping("/")
    public String nothing() {
        return "redirect:table";
    }
    @GetMapping("/table")
    public String table(Principal principal) {
        String name = principal.getName();

        return "table";

        // if (accessService.check_onhall(name)  == -1 &&
        //     accessService.check_ontable(name) == -1) 
        // {
        //     return "table";
        // }else {
        //     return "redirect:error";
        // }
    }

    //WebSockets links
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GameState greeting(Principal principal, PlayerInput playerInput) throws Exception {
        // Thread.sleep(1000); // simulated delay
        
        String name = principal.getName();

        //Set on GameState
        if (accessService.check_ontable(name) == -1) {
            if (accessService.check_onhall(name) == -1) {
                accessService.set_user_in_gamestate(name,"hall");
            }
        }
        
        accessService.permit_to_step(name,playerInput);
        
        
        return accessService.gameState;
    }

    @MessageMapping("/getinfo")
    @SendToUser("/queue/get_user_interface")
    public OutputUserInfo getUserInfo(Principal principal) {
        //Get player info
        String name = principal.getName();
        
        return accessService.getOutputUserInfo(name);
    }

}