package com.online.poker.repository;
import java.util.ArrayList;


public class GameState {

	public ArrayList<User> PlayersOnTable = new ArrayList<User>();
	public ArrayList<User> PlayersOnHall = new ArrayList<User>();
	public ArrayList<Card> CardsOnTable = new ArrayList<Card>();
	
	public int BiggestBet;
	public ArrayList<Integer> PlayersBet = new ArrayList<Integer>();
	
	public int DilerId;
	public int StepId;
	
	public int Bank;

}
