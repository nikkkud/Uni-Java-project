package com.online.poker.repository;
import java.util.ArrayList;


public class GameState {
	

	// місце у всіх списках
	public int StepId;

	private ArrayList<ArrayList<Card>> PlayersCards = new ArrayList<ArrayList<Card>>();
	public ArrayList<ArrayList<Card>> OpenCards = new ArrayList<ArrayList<Card>>();
	
	//у кого f того ти ігнориш він просто весить 
	//до кінця гри щоб гравці побачили які в нього карти були 
	//['p','p','f','p']
	public ArrayList<String> FoldPlayers = new ArrayList<String>();

	// Усы поставлені гроші
	public int Bank = 0;

	//Список гравціва [ [Name="gleb", Balance ] , ...]
	public ArrayList<User> PlayersOnTable = new ArrayList<User>();
	//Список з усіма ставками [ 100, 200, 0, ...]
	public ArrayList<Integer> PlayersBet = new ArrayList<Integer>();

	// [ [Number=9, Suit = 'H' ] , ... ]
	public ArrayList<Card> CardsOnTable = new ArrayList<Card>();

	// Найбільша ставка - її ти знаходиш відносно PlayersBet вона чисто технічна для роботи
	public int BiggestBet;

	

	//це у тебе є але ти цтим не користуєшся - це для мене 
	public ArrayList<User> PlayersOnHall = new ArrayList<User>();
	public int DilerId;
	public int CircleSize = 0;
	public boolean GameOver = true;



    // сетери + гетери
    public ArrayList<ArrayList<Card>> getPlayersCards(){
        return PlayersCards;
    }

    public void setPlayersCards(ArrayList<ArrayList<Card>> PlayersCards){
        this.PlayersCards = PlayersCards;
    }

    // public ArrayList<Card> getCardsOnTable(){
    //     return CardsOnTable;
    // }

    // public void setCardsOnTable(ArrayList<Card> cardsOnTable){
    //     this.CardsOnTable = cardsOnTable;
    // }

}
