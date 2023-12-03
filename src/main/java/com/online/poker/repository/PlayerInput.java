package com.online.poker.repository;

public class PlayerInput {
    
    public String Act;
    public int Bet;

    public PlayerInput() {

    }

    public PlayerInput(String act) {
        this.Act = act;
    }

    public PlayerInput(String act, int bet) {
        this.Act = act;
        this.Bet = bet;
    }
}