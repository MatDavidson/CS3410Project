package UnoGame;

public class gameTester {

	public static void main(String[] args) {
		Game game = new Game();
		
		game.createPlayers("Mat Gabby Fozzy Sissy");
		
		System.out.print(game.getDeck().toString());
		
		game.deal();
		
		
//		System.out.print(game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
//		game.nextPlayer();
//		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
//		game.nextPlayer();
//		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
//		game.nextPlayer();
//		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
//		game.nextPlayer();
//		
//		System.out.print("\n\n*********************\nPlayer Order: ");
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		
//		game.reverse();
//		
//		System.out.print("\n\n*********************\nReverse Order: ");
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
//		System.out.print(game.getCurrentPlayer().getName() + ", ");
//		game.nextPlayer();
		
		Player p = game.getCurrentPlayer();
		System.out.print("\n\n" + p.toString());
		Card c = p.getHand().get(3);
		System.out.print("\n" + c.toString());
		
		game.setCurrentCard(c);
		
		game.play(c);
		System.out.print("\n\n" + p.toString());
		
		p = game.getCurrentPlayer();
		System.out.print("\n\n" + p.toString());
		game.draw();
		System.out.print("\n\n" + p.toString());
		
		c = p.getHand().get(3);
		
		System.out.print("\n" + c);
		System.out.print("\n" + game.getCurrentCard());
		System.out.print("\n" + game.play(c));
		
//		p.getHand().deleteAtPos(p.findPos(c));
//		
//		System.out.print("\n\n" + p.toString());
	}

}
