package UnoGame;

public class Player {

	private String name;
	private boolean hasOne = false;
	private boolean hasDrawn = false;
	private boolean hasPlayed = false;
	private boolean calledUno = false;
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
	
	public void setHasOne(boolean b) {
		hasOne = b;
	}

	public boolean hasDrawn() {
		return hasDrawn;
	}
	public void setHasDrawn(boolean b) {
		hasDrawn = b;
	}

	public boolean hasPlayed() {
		return hasPlayed;
	}
	
	public void setHasPlayed(boolean b) {
		hasPlayed = b;
	}
	
	public boolean calledUno() {
		return calledUno;
	}
	
	public void setCalledUno(boolean b) {
		calledUno = b;
	}
	
	public int findPos(Card c) {
		int pos = 0;
		Node<Card> n = hand.getHead();
		Card d = n.getElement();
		while(d != c) {
			if(pos > hand.getSize())
				return -1;
			else {
				n = n.getNext();
				d = n.getElement();
				pos += 1;
			}
		}
		return pos;
	}
	
//	public void draw(ArrayList<Card> deck, ArrayList<Card> discardPile) {
//		if(deck.size() > 0) {
//			hand.addLast(deck.remove(0));
//		}	
//		
//		else {
//			Card tempCard = discardPile.remove(discardPile.size() - 1);
//			UnoGUI.shuffle(discardPile);
//			ArrayList<Card> tempDeck = discardPile;
//			discardPile = deck;
//			deck = tempDeck;
//			discardPile.add(tempCard);
//		}			
//	}
	
	public String toString() {
		return name + "'s hand:\n" + hand.display();
	}
}
