package UnoGame;

public class CDLLTester {

	public static void main(String[] args) {
		CircularDoublyLinkedList<Card> cards = new CircularDoublyLinkedList<Card>();

		for(int i = 0; i < 10; i++) {
			cards.addLast(new NumberCard("Green", i));
		}	
		System.out.print(cards.display());
		
		cards.removeFirst();
		cards.removeLast();
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());
		
		cards.addFirst(new NumberCard("Red", 0));
		cards.addLast(new NumberCard("Red", 9));
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());

		cards.addFirst(new NumberCard("Blue", 9));
		cards.addLast(new NumberCard("Blue", 0));
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());
		
		cards.insertAtPos(5, new ConditionCard("Black", "Draw 4"));
		cards.insertAtPos(5, new ConditionCard("Black", "Wild"));
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());
		
		cards.deleteAtPos(2);
		cards.deleteAtPos(8);
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());
		
		cards.deleteAtPos(0);
		cards.deleteAtPos(80);
		
		System.out.print("\n**************************************************\n");
		System.out.print(cards.display());
	}

}
