package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class startingScene extends Application {
	 Button button;
	
	public  Scene startScene1(Stage primaryStage) throws IOException {
		Image backgroundImage = new Image("C:\\Users\\DR.Sameh\\Downloads\\Milestone2-Solution\\images\\background.jpg");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		button = new Button("Start Game");
		Font font = Font.font("Courier New", FontWeight.BOLD, 36);
		button.setFont(font);
		button.setDefaultButton(false);
        button.setStyle("-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");
		 button.setOnMouseEntered(event -> {
		        button.setStyle("-fx-background-color: #2B4365; -fx-background-radius: 50px; -fx-text-fill: #fff;  -fx-padding: 10px 20px; -fx-cursor: hand;");
	            

	        });
		 button.setOnMouseExited(event -> {
		        button.setStyle("-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");


	        });
		
		
		StackPane layout =new StackPane();
		layout.getChildren().addAll(backgroundImageView,button);
		StackPane.setAlignment(button,Pos.BOTTOM_CENTER);
		
		layout.setPadding(new Insets(100));
		Scene two=heroesScene.startScene2();
		 Button switchButton = new Button("Switch to Scene 2");
	        switchButton.setOnAction(event -> {
	            primaryStage.setScene(two);
	        });
		Scene scene=new Scene(layout,backgroundImage.getWidth(),backgroundImage.getHeight());
		return scene;
	
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	
}
