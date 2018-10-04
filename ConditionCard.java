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
}
