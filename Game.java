package UnoGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game{
	private ArrayList<Card> deck;
	private CircularDoublyLinkedList<Player> players;
	private ArrayList<Card> discardPile;
	private boolean playerOrder = true;
	private Node<Player> currentPlayer;
	private Card currentCard;

	//Constructor
	public Game() {
		deck = createDeck();
		players = new CircularDoublyLinkedList<Player>();
		discardPile = new ArrayList<Card>();
	}

	//Switches order of play by changing the boolean condition playerOrder
	public void reverse() {
		playerOrder = !playerOrder;
	}

	//returns playerOrder
	public boolean getPlayerOrder() {
		return playerOrder;
	}

	//Builds the deck (taken from gameDriver)
	public ArrayList<Card> createDeck(){

		ArrayList<Card> deck2 = new ArrayList<Card>();

		try {
			File file = new File("src\\UnoGame\\CardNames");
			Scanner scnr = new Scanner(file);

			while (scnr.hasNext()){
				String infoLine = scnr.nextLine();
				String[] info = infoLine.split(" ");
				String color = info[0];
				int number = -2;
				String type = "none";

				if(Character.isDigit(info[1].charAt(0))) {
					number = Integer.parseInt((info[1].substring(0,1)));
				}
				else if(info[1].equals("Skip") || info[1].equals("Reverse") || info[1].equals("Wild")) {
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

	//returns the deck
	public ArrayList<Card> getDeck() {
		return deck;
	}

	//Accepts a String of player names and creates all the players
	public void createPlayers(String input) {
		String[] names = input.split(" ");
		for(String s: names) {
			players.addLast(new Player(s));
		}
		currentPlayer = players.getHead();
	}

	//Advances control to the next player
	public void nextPlayer() {
		if(playerOrder)
			currentPlayer = currentPlayer.getNext();
		else
			currentPlayer = currentPlayer.getPrev();
	}
	
	//Returns the currently active player
	public Player getCurrentPlayer() {
		return currentPlayer.getElement();
	}

	//returns the list of players
	public CircularDoublyLinkedList<Player> getPlayers() {
		return players;
	}

	//	Deals 7 cards to all the players and sets the first card on the discard pile.(taken from gameDriver)
	public void deal() {

		//		Shuffles deck
		shuffle();

		//		Does the drawing for the players
		for(int draws = 0; draws < 7; draws++) {
			Node<Player> playerNode = players.getHead();
			while(playerNode.getNext() != players.getHead()) {
				playerNode.getElement().getHand().addLast(deck.remove(0));
				playerNode = playerNode.getNext();
			}
			playerNode.getElement().getHand().addLast(deck.remove(0));
		}

		//		Draws top card from deck and sets it as first card. It makes sure it isn't a Wild card, because the rules are weird with those.
		Card top = deck.get(0);
		while(top.getColor().equals("Wild")) {
			shuffle();
			top = deck.get(0);
		}
		discardPile.add(deck.remove(0));
		currentCard = discardPile.get(0);
		if(currentCard instanceof ConditionCard)
			applyCondition((ConditionCard)currentCard);
	}
	
	//returns the current top of the discard pile
	public Card getCurrentCard() {
		return currentCard;
	}

	//	Method to shuffle the deck. (taken from UnoGUI)
	public void shuffle() {
		for(int x = (deck.size() - 1); x >= 0; x--) {
			int j = (int)(Math.random() * (x + 1));
			Card temp = deck.get(x);
			deck.set(x, deck.get(j));
			deck.set(j, temp);
		}
	}
	
	//Method that will check to see if if any players have one card but did not call uno, than checks the current player
	public void callUno() {
		if(checkUno()) {
			return;
		}	
		else if(getCurrentPlayer().hasOne())
			getCurrentPlayer().setCalledUno(true);
	}
	
	//checks if the current player has drawn, if not gives the player a card (taken from UnoGUI)
	public void draw() {
		Player p = getCurrentPlayer();
		
		if(p.hasDrawn())
			return;
		
		p.getHand().addFirst(deck.remove(0));
		
		if(p.getHandSize()>1)
			p.setHasOne(false);
		
		p.setHasDrawn(true);
		
		if(deck.isEmpty())
			reshuffle();
		
		if(matches(p.getHand().getHead().getElement()))
			play(p.getHand().getHead().getElement());
	}
	
	//Transfers the cards in the discard pile to the deck and shuffles 
	public void reshuffle() {
		while(!discardPile.isEmpty()) {
			deck.add(discardPile.remove(0));
			discardPile.add(deck.remove(deck.size()-1));
			shuffle();
		}
	}
	
	//Plays the card from the current player's hand.
	public boolean play(Card c) {
		if(matches(c)) {
			currentCard = c;
			Player p = getCurrentPlayer();
			p.getHand().deleteAtPos(p.findPos(c));
			discardPile.add(c);
			if(c instanceof ConditionCard) 
				applyCondition((ConditionCard)c);
			p.setHasPlayed(true);
			nextTurn();
			return true;
		}	
		else
			return false;
	}
	
	//Checks to see if the card arg can be played on the current card (compiled from the card classes)
	public boolean matches(Card card) {
		if(card.getColor().equals("Black") || currentCard.getColor().equals("Black") || card.getColor().equals(currentCard.getColor()))
			return true;
		else if(card instanceof ConditionCard && currentCard instanceof ConditionCard) {
			if(((ConditionCard)card).getType() == ((ConditionCard) currentCard).getType())
			return true;
		}
		else if(card instanceof NumberCard && currentCard instanceof NumberCard) {
			if(((NumberCard)card).getNum() == ((NumberCard) currentCard).getNum())
			return true;
		}
		else 
			return false;
		return false;
	}
	
	//Applies the condition of a condition card
	public void applyCondition(ConditionCard c) {
		switch (c.getType()) {
		
		case "Skip":
			nextTurn();
			nextTurn();
			break;
			
		case "Draw Two":
			nextTurn();
			drawTwo();
			nextTurn();
			break;
			
		case "Reverse":
			reverse();
			nextTurn();
			break;
			
		case "Wild":
			break;
			
		case "Draw Four":
			nextTurn();
			drawFour();
			nextTurn();
		}
	}
	
	public void chooseColor(String s) {
		ConditionCard c = (ConditionCard)currentCard;
		Card temp = new ConditionCard(s, c.getType());
		currentCard = temp;
	}
	
	public void drawTwo() {
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().setHasOne(false);
	}
	
	public void drawFour() {
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().getHand().addFirst(deck.remove(0));
		getCurrentPlayer().setHasOne(false);
	}
	
	public void busted(Player p) {
		p.getHand().addFirst(deck.remove(0));
		p.getHand().addFirst(deck.remove(0));
		p.getHand().addFirst(deck.remove(0));
		p.getHand().addFirst(deck.remove(0));
		p.setHasOne(false);
	}

	public void nextTurn() {
		Player p = getCurrentPlayer();
		
		p.setHasDrawn(false);
		p.setHasPlayed(false);
		
		nextPlayer();
	}

	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}
	
	public boolean checkUno() {
		Node<Player> p = currentPlayer.getNext();
		
		while(p != currentPlayer) {
			if(p.getElement().hasOne() && !p.getElement().calledUno()) {
				busted(p.getElement());
				return true;
			}
		}
		return false;
	}
	
	public void setCurrentCard(Card c) {
		currentCard = c;
	}
}
























