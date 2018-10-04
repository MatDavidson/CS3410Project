package UnoGame;

public class Card{

	private String color;
	
	public Card(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	public boolean compareColor(String c) {
		return color == c;
	}
}
