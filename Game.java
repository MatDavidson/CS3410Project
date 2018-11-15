package UnoGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Game{
	private ArrayList<Card> deck;
	private CircularDoublyLinkedList<Player> players;
	private ArrayList<Card> discardPile;
	private boolean playerOrder = true;
	
	public Game() {
		deck = GameDriver.createDeck();
		players = new CircularDoublyLinkedList<Player>();
		discardPile = new ArrayList<Card>();
	}

	public void reverse() {
		playerOrder = !playerOrder;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public CircularDoublyLinkedList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}

	public boolean getPlayerOrder() {
		return playerOrder;
	}

}
