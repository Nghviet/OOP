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
        this.gameField = null;
        gameRenderer = new GameRenderer(graphicsContext);
        haveBuilding = false;
    }

    public void initGame() {
        gameField = new GameField();
        gameRenderer.setGameField(gameField);
    }

    @Override
    public void handle(long now) {
        if(Config.UI_CUR == Config.UI_PLAYING) gameField.update();
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
        if(keyCode == KeyCode.S && Config.UI_CUR == Config.UI_PLAYING) {
            gameField.doSpawn();
        }
        else
        if(keyCode == KeyCode.ESCAPE && Config.UI_CUR == Config.UI_PLAYING) {
            System.out.println("Pause");
            Config.UI_CUR = Config.UI_PAUSE;
        }
        else
        if(keyCode == KeyCode.ESCAPE && Config.UI_CUR == Config.UI_PAUSE) {
            System.out.println("Unpause");
            Config.UI_CUR = Config.UI_PLAYING;
        }
    }

    public final void mouseOnKeyPressed(MouseEvent mouseEvent) {
        switch(Config.UI_CUR) {
            case Config.UI_START: {
                startMouse(mouseEvent);
                break;
            }
            case Config.UI_PLAYING: {
                playingMouse(mouseEvent);
                break;
            }
        }
    }

    public void startMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=50) {
            System.out.println("Init");
            Config.UI_CUR = Config.UI_PLAYING;
            System.out.println(Config.UI_CUR);
            initGame();
        }
    }

    public void playingMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();

        if(mX <= Config.GAME_WIDTH && gameField.isEmpty(mX,mY) && mouseButton == MouseButton.PRIMARY) {
            gameField.addTurret(mX,mY);
        }
    }

    public void pauseMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=50) {
            System.out.println("Unpause");
            Config.UI_CUR = Config.UI_PLAYING;
            System.out.println(Config.UI_CUR);
            initGame();
        }
    }


    public final void mouseController(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();
        mX = x;
        mY = y;
    }
}
