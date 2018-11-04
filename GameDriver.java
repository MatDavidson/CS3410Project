package UnoGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameDriver {

	public static void main(String[] args) {
		Game game = new Game();

//		Creates players and deals hands to them, then sets the first face-up card on the discard pile from the deck.
		createPlayers(game);
		
		Player winner = playingTheGame(game);
		
		System.out.print(winner.getName() + " wins!");

	}
	
//	Asks for the number of players (between 2 and 10) and then creates and adds the players to the CDLL "players" for the Game object
	public static void createPlayers(Game game) {
		Scanner scnr = new Scanner(System.in);
		int numPlayers = 0;
		
		System.out.println("Enter the number of players (2-10): ");
		while(numPlayers == 0) {	
			try{
				numPlayers = scnr.nextInt();
				if(numPlayers > 10 || numPlayers < 2) {
					System.out.println("Please enter an integer between 2 and 10.");
					numPlayers = 0;
				}
			}
			catch (Exception e) {
				System.out.println("Please enter an integer between 2 and 10.");
			}
		}
		
		for(int x = 0; x < numPlayers; x++) {
			System.out.println("Enter Player " + (x + 1) + "'s name:");
			String name = scnr.next();
			game.getPlayers().addLast(new Player(name));
		}
		
		deal(game.getPlayers(), game.getDeck(), game.getDiscardPile());
	}
	
	
//	A method that can shuffle an ArrayList of cards, such as the deck or discard pile
	public static void shuffle(ArrayList<Card> deck) {
		for(int x = (deck.size() - 1); x >= 0; x--) {
			int j = (int)(Math.random() * (x + 1));
			Card temp = deck.get(x);
			deck.set(x, deck.get(j));
			deck.set(j, temp);
		}
	}
	
//	Deals 7 cards to all the players and sets the first card on the discard pile.
	public static void deal(CircularDoublyLinkedList<Player> players, ArrayList<Card> deck, ArrayList<Card> discardPile) {
		
//		Shuffles deck
		shuffle(deck);
		
//		Does the drawing for the players
		for(int draws = 0; draws < 7; draws++) {
			Node<Player> playerNode = players.getHead();
			while(playerNode.getNext() != players.getHead()) {
				playerNode.getElement().draw(deck, discardPile);
				playerNode = playerNode.getNext();
			}
			playerNode.getElement().draw(deck, discardPile);
		}
		
//		Draws top card from deck and sets it as first card. It makes sure it isn't a Wild card, because the rules are weird with those.
		Card top = deck.get(0);
		while(top.getColor().equals("Wild")) {
			shuffle(deck);
			top = deck.get(0);
		}
		discardPile.add(deck.remove(0));
		
//*******Remove later, it only prints the hand of all players to test*********		
//		for(int draws = 0; draws < 7; draws++) {
//			Node<Player> playerNode = players.getHead();
//			while(playerNode.getNext() != players.getHead()) {
//				System.out.println(playerNode.getElement().getHand().display());
//				playerNode = playerNode.getNext();
//			}
//			System.out.println(playerNode.getElement().getHand().display());
//		}
//*******Remove later, it only prints the hand of all players to test*********
	}
	
	
	public static ArrayList<Card> createDeck(){
	
		ArrayList<Card> deck2 = new ArrayList<Card>();
		
			try {
				File file = new File("src\\UnoGame\\CardNames");
				Scanner scnr;
				scnr = new Scanner(file);
			 
				while (scnr.hasNext()){
					String infoLine = scnr.nextLine();
					String[] info = infoLine.split(" ");
					String color = info[0];
					int number = -2;
					String type = "none";
					
					if(Character.isDigit(info[1].charAt(0))) {
						number = Integer.parseInt((info[1].substring(0,1)));
					}
					else if(Character.isDigit(info[1].charAt(1))) {
						number = -1;
					}
					else if(info[1].equals("Skip") || info[1].equals("Reverse")) {
						type = info[1];
					}
					else if(info.length > 2 && info[2].equals("Two")) {
						type = "Draw Two";
					}
					else if(info.length > 2 && info[2].equals("Four")) {
						type = "Draw Four";
					}
					
					if(number != -2) { 
						deck2.add(new NumberCard(color, number));
						if(number != 0) {
							deck2.add(new NumberCard(color, number));
						}
					}	
					
					if(!type.equals("none")) {
						deck2.add(new ConditionCard(color, type));
						deck2.add(new ConditionCard(color, type));
					}
				}
				
				scnr.close();
			}
			
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return deck2;
		}
	
	
//	This method will direct a player's turn, by having them play a card (or draw a new one if they
//	don't have one they can play), do the special action of a card (if it is one), and will go to the next player's turn and repeat until someone ends
//	their turn with no cards in hand.
	public static Player playingTheGame(Game game){
		Node<Player> playerNode = game.getPlayers().getHead();
		Player currentPlayer = playerNode.getElement();
		ArrayList<Card> discardPile = game.getDiscardPile();
		Scanner scnr= new Scanner(System.in);
		
		while(true) {
			Card topDiscard = discardPile.get(discardPile.size() - 1);
			System.out.println("Top card on the discard pile: \n" + topDiscard);
			
//			Displays current player's name and hand
			System.out.println(currentPlayer);

			System.out.println("What card will you play?");
			
			String color = scnr.next();
			String parameter = scnr.next();
 
			int number = -2;
			String type = "none";
			
//			if(Character.isDigit(info[1].charAt(0))) {
//				number = Integer.parseInt((info[1].substring(0,1)));
//			}
//			else if(Character.isDigit(info[1].charAt(1))) {
//				number = -1;
//			}
//			else if(info[1].equals("Skip") || info[1].equals("Reverse")) {
//				type = info[1];
//			}
//			else if(info.length > 2 && info[2].equals("Two")) {
//				type = "Draw Two";
//			}
//			else if(info.length > 2 && info[2].equals("Four")) {
//				type = "Draw Four";
			//turn their choice into a card and do stuff************************************************************************************************************************
			
			
//			Checks the players hand to see if it's empty and breaks the endless "While" loop to finish the game.
			if(currentPlayer.getHandSize() == 0)
				break;
			
//			Controls who the next player will be, depending on the direction of play
			if(game.getPlayerOrder()) { 
				playerNode = playerNode.getNext();
				currentPlayer = playerNode.getElement();
			}
			
			else {
				playerNode = playerNode.getPrev();
				currentPlayer = playerNode.getElement();
			}
		}
		
		return currentPlayer;
	}
}
