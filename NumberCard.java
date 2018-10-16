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
		if (this.num == -1) {
			return this.getColor();
		}
		else return this.getColor() + " " + num;
	}
}
