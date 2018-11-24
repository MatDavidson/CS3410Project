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
		if(card instanceof ConditionCard && type == ((ConditionCard) card).getType())
			return true;
		
		else if(card.getColor().equals("Black") || card.getColor().equals(this.getColor()))
			return true;
		
		else 
			return false;
	}
	
	public String toString() {
		return this.getColor() + " " + type;
	}
}
