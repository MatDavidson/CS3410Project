package UnoGame;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UnoGUI extends Application{
	
	private TextArea mainTextArea;
	private VBox introScene;
	private VBox playerNamingScene;
	private VBox tableScene;
	private VBox nextTurnScene;
	private VBox winnerScene;
	private VBox chooseColorScene;
	private int maxNumPlayers;
	private int currentNumPlayers = 0;
	StackPane scenePanes = new StackPane();
	Game game = new Game();
	Player currentPlayer = new Player("Mork");
	Player previousPlayer = null;
	Card topDiscard;
	
	
	public void start(Stage primaryStage) {
		
		try {
			StackPane root = buildRoot();
			Scene introScene = new Scene(root, 600, 800);
			introScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(introScene);
			primaryStage.setTitle("Uno in Java");
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
//Builds the StackPane, which will be used as the Scene in the GUI.	
	public StackPane buildRoot() {

		introScene = buildIntroScene();
		playerNamingScene = buildPlayerNamingScene();
		//playerTurnScene = buildPlayerTurnScene();
		nextTurnScene = buildNextTurnScene();
		winnerScene = buildWinnerScene();
		chooseColorScene = buildChooseColorScene();
		
		introScene.setVisible(true);
		playerNamingScene.setVisible(false);
		//playerTurnScene.setVisible(false);
		nextTurnScene.setVisible(false);
		winnerScene.setVisible(false);
		chooseColorScene.setVisible(false);
		
		scenePanes.getChildren().addAll(introScene, playerNamingScene, /*playerTurnScene,*/ nextTurnScene, winnerScene, chooseColorScene);
		scenePanes.setAlignment(Pos.CENTER);
		scenePanes.setPadding(new Insets(10,10,10,10));
		return scenePanes;
	}
	
//Builds the welcome page, and allows for the number of players to be selected.	
	public VBox buildIntroScene() {
		
		VBox box = new VBox();
		
		Label intro = new Label("Welcome to Uno!");
		intro.setTextFill(Color.rgb(204, 0, 0));
		Label numberOfPlayers = new Label("How many players will there be?");
		numberOfPlayers.setTextFill(Color.rgb(0, 102, 102));
		ComboBox<Integer> numPlayersBox = new ComboBox<Integer>();
		numPlayersBox.getItems().addAll(2,3,4,5,6,7,8,9,10);
		Button button = new Button("Begin Game");
		button.setStyle("-fx-base: mediumseagreen");
		
		class startButtonEventHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				maxNumPlayers = numPlayersBox.getValue();
				playerNamingScene = buildPlayerNamingScene();
				scenePanes.getChildren().add(playerNamingScene);  //Must "rebuild" and add the element again to update its values.
				introScene.setVisible(false);
				playerNamingScene.setVisible(true);
			}
		}
		button.setOnAction(new startButtonEventHandler());
		
		box.getChildren().addAll(intro, numberOfPlayers, numPlayersBox, button);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
//	Builds the page that allows players to give themselves names. 
//	Once the last player is named, it shuffles the deck and deals 7 cards to each player, and places the top card of the deck onto the discard pile.
	public VBox buildPlayerNamingScene() {
		
		Button button;
		VBox box = new VBox();
		Label l1 = new Label("Please choose a name, Player " + (currentNumPlayers + 1));
		TextField nameField = new TextField();
		nameField.setPromptText("Enter your name here.");
		
		if(currentNumPlayers + 1 < maxNumPlayers)
			button = new Button("Add this player.");
		else
			button = new Button("Start the game!");
		button.setStyle("-fx-base: dodgerblue");
		
		class nextButtonEventHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				if(currentNumPlayers + 1 < maxNumPlayers) {
					game.getPlayers().addLast(new Player(nameField.getText()));
					currentNumPlayers++;
					playerNamingScene.setVisible(false);
					playerNamingScene = buildPlayerNamingScene();
					scenePanes.getChildren().add(playerNamingScene);
					playerNamingScene.setVisible(true);
				}
				else {
					game.shuffle(game.getDeck());
					for(int draws = 0; draws < 7; draws++) {
						Node<Player> playerNode = game.getPlayers().getHead();
						for(int x = 0; x < maxNumPlayers; x++) {
							playerNode.getElement().draw(game.getDeck(), game.getDiscardPile());
							playerNode = playerNode.getNext();
						}
					}
					topDiscard = game.getDeck().remove(0);
					
					currentPlayer = game.getPlayers().getHead().getElement();
					tableScene = buildTableScene();
					scenePanes.getChildren().add(tableScene);
					playerNamingScene.setVisible(false);
					tableScene.setVisible(true);
				}
			}
		}
		button.setOnAction(new nextButtonEventHandler());
		
		box.getChildren().addAll(l1, nameField, button);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
//Builds the page that shows the player's hand, the size of other players' hands, and allows the player to play a card/processes their turn.	
	public VBox buildTableScene() {
		
		VBox box = new VBox();
		
		
		
		if(previousPlayer!= null) {
			Label l0 = new Label(previousPlayer.getName() + " played " + game.getDiscardPile().get(0));
			box.getChildren().add(l0);
		}
		
		Label opponentHandSize = new Label(buildOpponentHandSizeLabel());
		
		Label l1 = new Label("Turn order: ");
		
		HBox showTopCard = new HBox();
		Label l2 = new Label("Top card of discard pile: ");
		showTopCard.getChildren().add(l2);
		Button topCardButton;
		if(topDiscard instanceof NumberCard && ((NumberCard) topDiscard).getNum() == -1) {
				topCardButton = new Button(topDiscard.getColor());
				showTopCard.getChildren().add(topCardButton);
		}
		else {
			topCardButton = new Button(topDiscard + "");
			showTopCard.getChildren().add(topCardButton);
		}
		
		switch(topDiscard.getColor()) {
		case "Red" : topCardButton.setStyle("-fx-base: red");
			break;
		case "Blue" : topCardButton.setStyle("-fx-base: dodgerblue");
			break;
		case "Green" : topCardButton.setStyle("-fx-base: mediumseagreen");
			break;
		case "Yellow" : topCardButton.setStyle("-fx-base: gold");
			break;
		case "Wild" : topCardButton.setStyle("-fx-base: black");
			break;
	}
		
		box.getChildren().addAll(opponentHandSize, l1, showTopCard);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//Builds the "Next Turn" page that goes in-between turns to prevent people seeing the next player's hand.
	public VBox buildNextTurnScene() {
		
		VBox box = new VBox();
		Label l1 = new Label("It's " + currentPlayer.getName() + "'s turn.");
		Button button = new Button("Begin the next turn!");
		button.setStyle("-fx-base: goldenrod");
		class nextTurnEventHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				tableScene = buildTableScene();
				scenePanes.getChildren().add(tableScene);
				nextTurnScene.setVisible(false);
				tableScene.setVisible(true);
			}
		}
		button.setOnAction(new nextTurnEventHandler());

		box.getChildren().addAll(l1, button);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
//Builds the page that congratulates the winner.	
	public VBox buildWinnerScene() {
		
		VBox box = new VBox();
		Label l1 = new Label(currentPlayer.getName() + " Wins!");
		box.getChildren().add(l1);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	
//	Builds the page that allows players to choose a color to change to after playing a Wild or Draw Four Wild
	public VBox buildChooseColorScene() {
		
		VBox box = new VBox();
		Label l1 = new Label("What Color will you change to?");
		ComboBox<String> colorBox = new ComboBox<String>();
		colorBox.getItems().addAll("Red", "Blue", "Green", "Yellow");
		Button button = new Button("Confirm");
		
//		This changes the color of the "Confirm" button depending on the color selected.
		class colorChangeEventHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				String color = colorBox.getValue(); 
				switch(color) {
				case "Red" : button.setStyle("-fx-base: red");
					break;
				case "Blue" : button.setStyle("-fx-base: dodgerblue");
					break;
				case "Green" : button.setStyle("-fx-base: mediumseagreen");
					break;
				case "Yellow" : button.setStyle("-fx-base: gold");
					break;	
				}
			}
		}
		colorBox.setOnAction(new colorChangeEventHandler());
		
//		This creates a "dummy" card that is the color chosen by the player, so that the next player has a card to match to.
		class colorSelectEventHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				Card card = new NumberCard(colorBox.getValue(), -1);
				topDiscard = card;
			}
		}
		button.setOnAction(new colorSelectEventHandler());
		
		Label yourHand = new Label("Your hand:");
		HBox playerHand = new HBox();
		Node<Card> cardMarker = currentPlayer.getHand().getHead();
		for(int x = 0; x < currentPlayer.getHandSize(); x++) {
			Button cardButton = new Button(cardMarker.getElement().getColor());
			switch(cardMarker.getElement().getColor()) {
				case "Red" : cardButton.setStyle("-fx-base: red");
					break;
				case "Blue" : cardButton.setStyle("-fx-base: dodgerblue");
					break;
				case "Green" : cardButton.setStyle("-fx-base: mediumseagreen");
					break;
				case "Yellow" : cardButton.setStyle("-fx-base: gold");
					break;
				case "Wild" : cardButton.setStyle("-fx-base: black");
					break;
			}
			playerHand.getChildren().add(cardButton);
			cardMarker = cardMarker.getNext();
		}
		
		box.getChildren().addAll(l1, colorBox, button, yourHand, playerHand);
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
//	Creates the contents for the label in the playerTurnScene VBox that displays the hand size of all opponents.
	public String buildOpponentHandSizeLabel() {
		
		StringBuilder handSize = new StringBuilder();
		Node<Player> firstPlayerNode = game.getPlayers().getHead();
//		Moves the Node<Player> to be the current player so that the remaining code starts at the current player.
		while(firstPlayerNode.getElement() != currentPlayer) {
			firstPlayerNode = firstPlayerNode.getNext();
		}
		
//		Displays the hand sizes of all but the current player.
		Player player;
		while(firstPlayerNode.getNext() != firstPlayerNode) {
			firstPlayerNode = firstPlayerNode.getNext();
			player = firstPlayerNode.getElement();
			handSize.append(player.getName() + "'s hand has " + player.getHandSize() + " cards." + "\n");
		}
		return handSize +"";
	}
	
	private void setButtonColor(Button btn, Card card) {
		switch(card.getColor()) {
		case "Red" : btn.setStyle("-fx-base: red");
			break;
		case "Blue" : btn.setStyle("-fx-base: dodgerblue");
			break;
		case "Green" : btn.setStyle("-fx-base: mediumseagreen");
			break;
		case "Yellow" : btn.setStyle("-fx-base: gold");
			break;
		case "Wild" : btn.setStyle("-fx-base: black");
			break;
	}
	}
}
