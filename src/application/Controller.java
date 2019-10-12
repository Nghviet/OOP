package application;

import application.core.GameField;
import application.graphic.GameRenderer;

import application.utility.Waypoints;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class Controller extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private GameRenderer gameRenderer;
    private GameField gameField;

    public Controller(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.gameField = new GameField();
        gameRenderer = new GameRenderer(graphicsContext,gameField);
        System.out.println(Waypoints.instance.size());
    }

    @Override
    public void handle(long now) {
        gameField.update();
        gameRenderer.render();
    }

    public void start() {
        super.start();
    }

    void closeHandle(WindowEvent windowEvent) {
        stop();
        Platform.exit();
        System.exit(0);
    }

    public final void onKeyDown(KeyEvent keyEvent) {
        System.out.println(keyEvent);
        KeyCode keyCode = keyEvent.getCode();
        if(keyCode == KeyCode.S) {
            System.out.println("S");
            gameField.doSpawn();
        }

    }

}
