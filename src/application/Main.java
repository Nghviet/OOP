package application;

import application.core.GameField;
import application.graphic.ImageHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Canvas canvas = new Canvas(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        final Controller gameController = new Controller(graphicsContext);

        canvas.setFocusTraversable(true);
        graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);

        canvas.setOnKeyPressed(gameController::onKeyDown);
        canvas.setOnMouseMoved(gameController::mouseController);
        canvas.setOnMouseClicked(mouseEvent -> {
            try {
                gameController.mouseOnKeyPressed(mouseEvent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle(Config.NAME);
        primaryStage.setOnCloseRequest(gameController::closeHandle);
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();

        gameController.start();
    }


    public static void main(String[] args) {
        System.out.println(Config.SCREEN_WIDTH+" "+Config.SCREEN_HEIGHT);
        launch(args);
    }
}
