package com.online.poker.repository;


public class Card {
    // N N - nothing
    // A 2 3 4 5 6 7 8 9 10  J  Q  K
    // 1 2 3 4 5 6 7 8 9 10 11 12 13
    public int Number;
    // (H D C S) (Hearts Diamonds Clubs Spades)
    public char Suit;

    public Card() {

    }
    
    public Card(int number, char suit) {
        this.Number = number;
        this.Suit = suit;
    }
   
}