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
	
	public boolean matches(Card card) {
		if(card instanceof NumberCard && num == ((NumberCard) card).getNum())
			return true;
		
		else if(card.getColor().equals("Black") || card.getColor().equals(this.getColor()))
			return true;
		
		else 
			return false;
	}
	
	public String toString() {
		if (this.num == -1) {
			return this.getColor();
		}
		else return this.getColor() + " " + num;
	}
}
