package view;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WinCheck extends Application {
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Last Of Us Legacy");

        String imagePath = "file:///C:/Users/Yousef Elbrolosy/Desktop/GUC/Semester 4/Milestone2-Solution/images/GameWin.png";
        Image backgroundImage = new Image(imagePath);

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(true);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        double imageWidth = backgroundImage.getWidth();
        double imageHeight = backgroundImage.getHeight();

        double scaleFactor = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

        backgroundImageView.setFitWidth(imageWidth * scaleFactor);
        backgroundImageView.setFitHeight(imageHeight * scaleFactor);

        StackPane layout = new StackPane();
        layout.getChildren().add(backgroundImageView);

        Scene scene = new Scene(layout, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
