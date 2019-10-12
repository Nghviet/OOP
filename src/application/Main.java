package application;

import application.core.GameField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Canvas canvas = new Canvas(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        final Controller gameController = new Controller(graphicsContext);

        canvas.setFocusTraversable(true);
        graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);

        canvas.setOnKeyPressed(gameController::onKeyDown);


        primaryStage.setResizable(false);
        primaryStage.setTitle(Config.NAME);
        primaryStage.setOnCloseRequest(null);
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();

        gameController.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
