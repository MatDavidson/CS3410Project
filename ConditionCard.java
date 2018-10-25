package UnoGame;

public class ConditionCard extends Card{
	private String type;
	
	public ConditionCard(String color, String type) {
		super(color);
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public boolean compareType(String t) {
		return type == t;
	}
	
	public boolean matches(Card card) {
		if(this.getColor().equals("Wild"))
			return true;
		
		else if(this.getColor().equals(card.getColor()))
			return true;
		
		else if(card instanceof NumberCard)
			return false;
		
		else if(type.equals(((ConditionCard) card).getType()))
			return true;
		
		return false;
	}
	
	public String toString() {
		return this.getColor() + " " + type;
	}
}
