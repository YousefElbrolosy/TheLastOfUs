package view;

import java.io.IOException;

import engine.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import model.characters.*;
import model.characters.Hero;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class heroesScene extends Application {
	static Button button;
	public static Scene startScene2() throws IOException {
		Image backgroundImage = new Image("C:\\Users\\DR.Sameh\\Downloads\\Milestone2-Solution\\images\\hereos.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		Game.loadHeroes("C:\\Users\\DR.Sameh\\Downloads\\Milestone2-Solution\\Heroes.csv");
		int numColumns = 4;

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		Label title = newLabel("Select a Hero", 40, FontWeight.BOLD, Color.WHITE);
		int i = 0;
		StackPane[] selectedCard = new StackPane[1];
		int []index=new int[1];
		for (Hero hero : Game.availableHeroes) {
			String name = hero.getName();
			int maxHp = hero.getMaxHp();
			int attackDmg = hero.getAttackDmg();
			String type = (hero instanceof Fighter) ? "Fighter" : (hero instanceof Medic) ? "Medic" : "Explorer";
			int maxActions = hero.getMaxActions();
			Rectangle card = new Rectangle(250, 200, Color.TRANSPARENT);
			StackPane cardPane = new StackPane(card);
			cardPane.setAlignment(Pos.CENTER);

		      cardPane.setOnMouseClicked(event -> {
		              index[0] = GridPane.getColumnIndex(cardPane) + GridPane.getRowIndex(cardPane) * numColumns;
     

		            card.setFill(Color.DARKORANGE);
		     

		            if (selectedCard[0] != null && !selectedCard[0].equals(cardPane)) {
		                ((Rectangle) selectedCard[0].getChildren().get(0)).setFill(Color.TRANSPARENT);
		            }

		            selectedCard[0] = cardPane;
		        });
			gridPane.add(cardPane, i % numColumns, i / numColumns);
			Label nameLabel = newLabel(name, 22, FontWeight.BOLD, Color.WHITE);
			Label typeLabel = newLabel(type, 18, FontWeight.NORMAL, Color.WHITE);
			Label maxHpLabel = newLabel("Max HP: " + maxHp, 18, FontWeight.NORMAL, Color.WHITE);
			Label attackDmgLabel = newLabel("Attack Damage: " + attackDmg, 18, FontWeight.NORMAL, Color.WHITE);
			Label maxActionsLabel = newLabel("Max Actions: " + maxActions, 18, FontWeight.NORMAL, Color.WHITE);
			nameLabel.setTranslateY(-50); 
			typeLabel.setTranslateY(-20); 
			maxHpLabel.setTranslateY(20); 
			attackDmgLabel.setTranslateY(50); 
			maxActionsLabel.setTranslateY(80); 
			cardPane.getChildren().addAll(nameLabel, typeLabel, maxHpLabel, attackDmgLabel, maxActionsLabel);
			i++;
		}
		
		button = new Button("Submit");
		Font font = Font.font("Courier New", FontWeight.BOLD, 36);
		button.setFont(font);
		button.setDefaultButton(false);
        button.setStyle("-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");
		 button.setOnMouseEntered(event -> {
		        button.setStyle("-fx-background-color: DARKORANGE; -fx-background-radius: 50px; -fx-text-fill: #fff;  -fx-padding: 10px 20px; -fx-cursor: hand;");
	            

	        });
		 button.setOnMouseExited(event -> {
		        button.setStyle("-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");


	        });
		StackPane root = new StackPane();
		root.getChildren().addAll(backgroundImageView, gridPane, title,button);
		StackPane.setAlignment(gridPane, Pos.CENTER_LEFT);
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		StackPane.setAlignment(button,Pos.BOTTOM_CENTER);

		root.setPadding(new Insets(100));
		button.setOnMouseClicked(e->{
			Hero x=Game.availableHeroes.remove(index[0]);
			Game.heroes.add(x);
			Game.startGame(x);
			
		});
		Scene scene = new Scene(root, backgroundImage.getWidth(), backgroundImage.getHeight());
		return scene;
	}

	public static Label newLabel(String x, int fontSize, FontWeight weight, Color color) {
		Label heroLabel = new Label(x);
		Font font = Font.font("Courier New", weight, fontSize);
		heroLabel.setFont(font);
		heroLabel.setTextFill(color);
		return heroLabel;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
