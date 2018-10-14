package UnoGame;

public class NumberCard extends Card{
	private int num;

	public NumberCard(String color, int num) {
		super(color);
		this.num = num;
	}

	public int getNum() {
		return num;
	}
	
	public boolean compareNum(int n) {
		return num == n;
	}
	
	public String toString() {
		return this.getColor() + " " + num;
	}
}
