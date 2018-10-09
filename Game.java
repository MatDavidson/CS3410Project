package UnoGame;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private LinkedList<Card> deck;
	private CircularDoublyLinkedList<Player> players;
	private ArrayList<Card> discardPile = new ArrayList<>();
	private boolean playerOrder = true;
	
	public Game() {
		
	}
	
	public void setPlayerOrder() {
		if(playerOrder == true)
			playerOrder = false;
		else
			playerOrder = true;
	}
	
	
}
