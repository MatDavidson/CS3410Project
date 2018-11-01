package UnoGame;
//Changes made
public abstract class Card{

	private String color;
	
	public Card(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	public boolean compareColor(String c) {
		return color.equals(c);
	}
	
	public abstract boolean matches(Card card);
	
	public abstract String toString();
}
