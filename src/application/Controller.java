package application;

import application.core.GameField;
import application.core.spawner.Spawner;
import application.core.spawner.Wave;
import application.core.tower.NormalTower;
import application.core.tower.RangerTower;
import application.core.tower.RapidTower;
import application.core.tower.Tower;
import application.graphic.GameRenderer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private GameRenderer gameRenderer;
    private GameField gameField;
    private Spawner spawner;
    //Mouse handler
    private boolean haveBuilding;
    public int mX,mY;
    public Tower curTower;
    public Tower showTower;
    public Controller(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.gameField = null;
        gameRenderer = new GameRenderer(graphicsContext,this);
        haveBuilding = false;
        curTower = null;
    }

    //Game init

    public void initGame() {
        gameField = new GameField();
        gameRenderer.setGameField(gameField);
        List<Integer> enemy = new ArrayList<>();
        List<Integer> delay = new ArrayList<>();
        for(int i=0;i<10;i++) {
            enemy.add(0);
            delay.add(i+1);
        }
        Wave t = new Wave(gameField,enemy,10,0);
        List<Wave> w = new ArrayList<>();
        for(int i=0;i<10;i++) w.add(t);
        spawner = new Spawner(gameField,w);
    }

    public void initGame(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        /*      File format
            Integer n: number of waypoints
            Next n pair of Integer : waypoints
         */

        List<Integer> pX = new ArrayList<>();
        List<Integer> pY = new ArrayList<>();

        int n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            pX.add(x);
            pY.add(y);
        }

        gameField = new GameField(pX,pY);
        gameRenderer.setGameField(gameField);

        int noWave = scanner.nextInt();
        List<Wave> waves = new ArrayList<>();
        for(int i=0;i<noWave;i++) {
            int no = scanner.nextInt();
            List<Integer> enemy = new ArrayList<>();
            for(int j=0;j<no;j++) {
                int t = scanner.nextInt();
                enemy.add(t);
            }
            Wave t = new Wave(gameField,enemy,10,i);
            waves.add(t);
        }

        spawner = new Spawner(gameField,waves);
    }

    //Update handler
    @Override
    public void handle(long now) {
        if(Config.UI_CUR == Config.UI_PLAYING)
        {
            if(gameField.isComplete() || spawner.gameComplete()) {
                Config.UI_CUR = Config.UI_GAME_COMPLETE;
                return;
            }


            spawner.update();
            gameField.update();
        }
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


    //Keyboard handling
    public final void onKeyDown(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if(keyCode == KeyCode.S && Config.UI_CUR == Config.UI_PLAYING) {
            gameField.doSpawn();
        }
        else
        if(keyCode == KeyCode.ESCAPE && Config.UI_CUR == Config.UI_PLAYING) {
            Config.UI_CUR = Config.UI_PAUSE;
        }
        else
        if(keyCode == KeyCode.ESCAPE && Config.UI_CUR == Config.UI_PAUSE) {
            Config.UI_CUR = Config.UI_PLAYING;
            gameField.resetTimer();
            spawner.resetTimer();
        }
    }


    //Mouse handling
    public final void mouseOnKeyPressed(MouseEvent mouseEvent) throws FileNotFoundException {
        switch(Config.UI_CUR) {
            case Config.UI_START: {
                startMouse(mouseEvent);
                break;
            }
            case Config.UI_STAGE_CHOOSING: {
                stageMouse(mouseEvent);
                break;
            }
            case Config.UI_PLAYING: {
                playingMouse(mouseEvent);
                break;
            }
            case Config.UI_GAME_COMPLETE: {
                gameCompleteMouse(mouseEvent);
                break;
            }
            case Config.UI_PAUSE: {
                pauseMouse(mouseEvent);
                break;
            }
        }
    }

    private void startMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=133 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=25) {
            Config.UI_CUR = Config.UI_STAGE_CHOOSING;
        }
    }

    private void gameCompleteMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(Math.abs(mX-Config.SCREEN_WIDTH/2)<=60 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=17) {
            Config.UI_CUR = Config.UI_START;
            gameField = null;
            spawner = null;
        }
    }

    private void stageMouse(MouseEvent mouseEvent) throws FileNotFoundException {

        curTower = null;
        showTower = null;

        File stage;
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX - Config.SCREEN_WIDTH/2 + 140)<=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2+80)<=60) {
            stage = new File("src/stageInfo/stage_1_data").getAbsoluteFile();
            initGame(stage);
            Config.UI_CUR = Config.UI_PLAYING;
        }

        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX - Config.SCREEN_WIDTH/2 - 140)<=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2+80)<=60) {
            stage = new File("src/stageInfo/stage_2_data").getAbsoluteFile();
            initGame(stage);
            Config.UI_CUR = Config.UI_PLAYING;
        }

        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX - Config.SCREEN_WIDTH/2 + 140)<=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2-80)<=60) {
            stage = new File("src/stageInfo/stage_3_data").getAbsoluteFile();
            initGame(stage);
            Config.UI_CUR = Config.UI_PLAYING;
        }

        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX - Config.SCREEN_WIDTH/2 - 140)<=100 && Math.abs(mY - Config.SCREEN_HEIGHT/2-80)<=60) {
            stage = new File("src/stageInfo/stage_4_data").getAbsoluteFile();
            initGame(stage);
            Config.UI_CUR = Config.UI_PLAYING;
        }
    }


    private void playingMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(!gameField.isComplete())
        {
            //
            if(Math.abs(mX - (Config.GAME_WIDTH+Config.UI_HORIZONTAL/2-10-Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2)
            {
                if(Math.abs(mY-(10+Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= NormalTower.instance.getPrice()) curTower = new NormalTower(gameField,null);
                    showTower = null;
                }
                if(Math.abs(mY -(90+Config.TILE_SIZE/2))<=Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= RangerTower.instance.getPrice()) curTower = new RangerTower(gameField,null);
                    showTower = null;
                }
            }

            if(Math.abs(mX - (Config.GAME_WIDTH+Config.UI_HORIZONTAL/2+10+Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2)
            {
                if(Math.abs(mY-(10+Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= RapidTower.instance.getPrice()) curTower = new RapidTower(gameField,null);
                    showTower = null;
                }
            }

            if(Math.abs(mX - (Config.SCREEN_WIDTH-Config.UI_HORIZONTAL+65)) <= 60 && Math.abs(mY - 317) <= 17) {
                spawner.nextWave();
            }

            //
            if(mX <= Config.GAME_WIDTH && gameField.isEmpty(mX,mY) && curTower == null) {
                showTower = null;
            }

            if(mX <= Config.GAME_WIDTH && !gameField.isEmpty(mX,mY)) {
                curTower = null;
                showTower = gameField.tower(mX,mY);
            }

            if(mX <= Config.GAME_WIDTH && gameField.isEmpty(mX,mY) && mouseButton == MouseButton.PRIMARY && curTower != null) {
                gameField.addTurret(mX,mY,curTower);
                gameField.charge(curTower.getPrice());
                curTower = null;
                showTower = null;
            }


        }
        else {
            if(Math.abs(mX-Config.SCREEN_WIDTH/2)<=100 && Math.abs(mY-Config.SCREEN_HEIGHT/2-20)<=50) {
                Config.UI_CUR = Config.UI_START;
            }
        }

        if(showTower!=null) {
            if(Math.abs(mX - (Config.SCREEN_WIDTH-64) - 29) <= 29 && Math.abs(mY - (Config.SCREEN_HEIGHT-150) - 15) <= 15
                    && gameField.getPlayerMoney()>=4 && showTower.getLevel()<3) {
                showTower.upgrade();
                gameField.charge(4);
            }

            if(Math.abs(mX - (Config.GAME_WIDTH+10)-21) <=21 && Math.abs(mY - (Config.SCREEN_HEIGHT-150) - 15) <=15 ) {
                gameField.charge(-showTower.getPrice());
                gameField.removeTower(showTower);
                showTower = null;
            }

        }
    }

    private void pauseMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=81 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=20) {
            Config.UI_CUR = Config.UI_PLAYING;
            gameField.resetTimer();
                spawner.resetTimer();

            }
            if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=81 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 50)<=20) {
                    Config.UI_CUR = Config.UI_START;
            gameField.resetTimer();
            spawner.resetTimer();
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
