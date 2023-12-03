package com.online.poker.repository;

public class PlayerInput {
    
    public String act;
    public String bet;

    public PlayerInput() {

    }

    public PlayerInput(String act) {
        this.act = act;
    }

    public PlayerInput(String act, String bet) {
        this.act = act;
        this.bet = bet;
    }
}