package com.online.poker.repository;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public String Name;
    public int Balance;

    public User() {

    }
    public User(String name, int balance) {

        this.Name = name;
        this.Balance = balance;
    }
}


