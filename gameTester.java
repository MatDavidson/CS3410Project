package UnoGame;

public class gameTester {

	public static void main(String[] args) {
		Game game = new Game();
		
		game.createPlayers("Mat Gabby Fozzy Sissy");
		
		System.out.print(game.getDeck().toString() + "\n\n");
		
		game.deal();
		
		
		System.out.print(game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
		game.nextPlayer();
		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
		game.nextPlayer();
		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
		game.nextPlayer();
		System.out.print("\n\n" + game.getCurrentPlayer().getName() + "\n" + game.getCurrentPlayer().getHand().display());
		game.nextPlayer();
		
		System.out.print("\n\n*********************\nPlayer Order: ");
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		
		game.reverse();
		
		System.out.print("\n\n*********************\nReverse Order: ");
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		System.out.print(game.getCurrentPlayer().getName() + ", ");
		game.nextPlayer();
		
	}

}
