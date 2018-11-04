package UnoGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UnoGUI extends Application{
	
	private TextArea mainTextArea;
	private VBox introScene;
	private VBox playerNamingScene;
	private VBox playerTurnScene;
	private VBox nextTurnScene;
	private VBox winnerScene;
	private int maxNumPlayers;
	private int currentNumPlayers = 0;
	StackPane scenePanes = new StackPane();
	Game game = new Game();
	Player currentPlayer = new Player("Default Bob");
	
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
		playerTurnScene = buildPlayerTurnScene();
		nextTurnScene = buildNextTurnScene();
		winnerScene = buildWinnerScene();
		
		introScene.setVisible(true);
		playerNamingScene.setVisible(false);
		playerTurnScene.setVisible(false);
		nextTurnScene.setVisible(false);
		winnerScene.setVisible(false);
		
		scenePanes.getChildren().addAll(introScene, playerNamingScene, playerTurnScene, nextTurnScene, winnerScene);
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
	
//Build the page that allows players to give themselves names.	
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
					currentPlayer = game.getPlayers().getHead().getElement();
					playerTurnScene = buildPlayerTurnScene();
					scenePanes.getChildren().add(playerTurnScene);
					playerNamingScene.setVisible(false);
					playerTurnScene.setVisible(true);
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
	public VBox buildPlayerTurnScene() {
		
		VBox box = new VBox();
		Label l1 = new Label("Time to take a spin!");
		/*To be removed**************************** */
		Button button = new Button("Go to end.");
		class finish implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				winnerScene = buildWinnerScene();
				scenePanes.getChildren().add(winnerScene);
				playerTurnScene.setVisible(false);
				winnerScene.setVisible(true);
			}
		}
		button.setOnAction(new finish());
		/*To be removed *************************** */
		box.getChildren().addAll(l1, button);
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
				playerTurnScene = buildPlayerTurnScene();
				scenePanes.getChildren().add(playerTurnScene);
				nextTurnScene.setVisible(false);
				playerTurnScene.setVisible(true);
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
}
