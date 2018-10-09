package UnoGame;

public class Player {

	private String name;
	private boolean hasOne = false;
	private boolean hasDrawn = false;
	private boolean hasPlayed = false;
	private CircularDoublyLinkedList<Card> hand;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void draw() {
		
	}
}
