package UnoGame;

import java.util.ArrayList;

public class Player {

	private String name;
	private boolean hasOne = false;
	private boolean hasDrawn = false;
	private boolean hasPlayed = false;
	private CircularDoublyLinkedList<Card> hand;
	
	public Player(String name) {
		this.name = name;
		this.hand = new CircularDoublyLinkedList<Card>();
	}
	
	public String getName() {
		return name;
	}
	
	public CircularDoublyLinkedList<Card> getHand() {
		return hand;
	}
	
	public int getHandSize() {
		return hand.getSize();
	}
	
	public boolean hasOne() {
		return hasOne;
	}

	public boolean hasDrawn() {
		return hasDrawn;
	}

	public boolean hasPlayed() {
		return hasPlayed;
	}
	
	public void draw(ArrayList<Card> deck, ArrayList<Card> discardPile) {
		if(deck.size() > 0) {
			hand.addLast(deck.remove(0));
		}	
		
		else {
			Card tempCard = discardPile.remove(discardPile.size() - 1);
			UnoGUI.shuffle(discardPile);
			ArrayList<Card> tempDeck = discardPile;
			discardPile = deck;
			deck = tempDeck;
			discardPile.add(tempCard);
		}			
	}
	
	public String toString() {
		return name + "'s hand:\n" + hand.display();
	}
}
