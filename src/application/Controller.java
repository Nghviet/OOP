package application;

import application.core.GameField;
import application.graphic.GameRenderer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

public class Controller extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private GameRenderer gameRenderer;
    private GameField gameField;
    //Mouse handler
    private boolean haveBuilding;
    private int mX,mY;

    public Controller(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.gameField = new GameField();
        gameRenderer = new GameRenderer(graphicsContext,gameField);
        haveBuilding = false;
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
        KeyCode keyCode = keyEvent.getCode();
        if(keyCode == KeyCode.S) {
            gameField.doSpawn();
        }
    }

    public final void mouseController(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();
        mX = x;
        mY = y;
        System.out.println(x+" "+y);
    }
}
