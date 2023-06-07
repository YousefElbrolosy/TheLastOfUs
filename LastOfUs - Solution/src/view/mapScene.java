package view;

import engine.Game;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.characters.Hero;

public class mapScene extends Application {

	public void start(Stage primaryStage) throws Exception {
		String x = "";
		for (Hero hero : Game.availableHeroes) {
			x += (hero.getName() + "|");
		}
		//x = x.substring(0, x.length() - 1);
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(2);
		gridPane.setVgap(2);

		for (int row = 0; row < 14; row++) {
			for (int col = 0; col < 14; col++) {
				Label cell = new Label();
				cell.setPrefSize(5, 5);
				if(Game.map[row][col].isVisible()) {
					cell.setTextFill(Color.BLACK);
				}
				else {
					cell.setTextFill(Color.TRANSPARENT);
				}
				gridPane.add(cell, col, row);
			}
		}

	
		Label remainingHeroes = newLabel(x, 30, FontWeight.BOLD, Color.BLACK);
		StackPane root = new StackPane();
		root.getChildren().addAll(gridPane,remainingHeroes);
		Scene scene = new Scene(root, 1000, 1200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("The Last Of Us Legacy");
		primaryStage.setFullScreen(true);
		primaryStage.show();
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

}
