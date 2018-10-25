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
		if(this.getColor().equals("Wild"))
			return true;
		
		else if(this.getColor().equals(card.getColor()))
			return true;
		
		else if(card instanceof ConditionCard)
			return false;
		
		else if(num == ((NumberCard) card).getNum())
			return true;
		
		return false;
	}
	
	public String toString() {
		if (this.num == -1) {
			return this.getColor();
		}
		else return this.getColor() + " " + num;
	}
}
