package application;

import application.core.GameField;
import application.core.audio.Audio;
import application.core.enemy.*;
import application.core.player.Player;
import application.core.spawner.Spawner;
import application.core.spawner.Wave;
import application.core.tile.Waypoints;
import application.core.tower.*;
import application.graphic.GameRenderer;
import application.network.Network;

import application.utility.Vector2;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Controller extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private GameRenderer gameRenderer;
    public GameField gameField;
    private Spawner spawner;
    //Mouse handler
    private boolean haveBuilding;
    public int mX,mY;
    public Tower curTower;
    public Tower showTower;
    public Network net;

    public String name = null;

    public Controller(GraphicsContext graphicsContext) throws URISyntaxException {
        this.graphicsContext = graphicsContext;
        this.gameField = null;
        gameRenderer = new GameRenderer(graphicsContext,this);
        haveBuilding = false;
        curTower = null;
        net = new Network();
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
        name = null;
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
        Audio.instance.backgroundPlay();
    }

    public void start() {
        super.start();
        File save = new File("src/application/loadData").getAbsoluteFile();
        Scanner scanner;
        try {
            scanner = new Scanner(save);
            Config.saved = scanner.nextBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
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
            System.out.println(keyCode.getName());
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
        if(Config.UI_CUR == Config.UI_ADDSCORE) {
            if(keyCode.isLetterKey()) {
                if(name != null)
                name += keyCode.name();
                else name = keyCode.name();
            }
        }
    }

    //Mouse handling
    public final void mouseOnKeyPressed(MouseEvent mouseEvent) throws IOException {
        Audio.instance.onClick();
        switch(Config.UI_CUR) {
            case Config.UI_START: {
                startMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
            case Config.UI_STAGE_CHOOSING: {
                stageMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
            case Config.UI_PLAYING: {
                playingMouse(mouseEvent);
                break;
            }
            case Config.UI_GAME_COMPLETE: {
                gameCompleteMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
            case Config.UI_PAUSE: {
                pauseMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
            case Config.UI_HIGHSCORE: {
                highscoreMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
            case Config.UI_ADDSCORE: {
                addScoreMouse(mouseEvent);
                mouseAudio(mouseEvent);
                break;
            }
        }
    }

    private void addScoreMouse(MouseEvent mouseEvent) {
        if(Math.abs(mX - Config.SCREEN_WIDTH/2) <= 60 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 47) <= 17) {
            net.addHighscore(name,gameField.getScore());
            Config.UI_CUR = Config.UI_START;
        }
    }

    private void mouseAudio(MouseEvent mouseEvent) {
        if(Math.abs(mX - (Config.SCREEN_WIDTH - Config.TILE_SIZE / 2 - 25)) <= Config.TILE_SIZE / 2 &&
           Math.abs(mY - (Config.SCREEN_HEIGHT - 3 * Config.TILE_SIZE / 2 - 20)) <= Config.TILE_SIZE / 2) {
            if(Audio.instance.isBackgroundMuted()) Audio.instance.unmuteBackground();
            else Audio.instance.muteBackground();
        }

        if(Math.abs(mX - (Config.SCREEN_WIDTH - Config.TILE_SIZE / 2 - 25)) <= Config.TILE_SIZE / 2 &&
                Math.abs(mY - (Config.SCREEN_HEIGHT - Config.TILE_SIZE / 2 - 10)) <= Config.TILE_SIZE/2) {
            if(Audio.instance.isMuted()) Audio.instance.unmuteSFX();
            else Audio.instance.muteSFX();
        }
    }

    private void startMouse(MouseEvent mouseEvent) throws IOException {
        MouseButton mouseButton = mouseEvent.getButton();
        if(mouseButton == MouseButton.PRIMARY && Math.abs(mX-Config.SCREEN_WIDTH/2) <=133 && Math.abs(mY - Config.SCREEN_HEIGHT/2 + 50)<=25) {
            Config.UI_CUR = Config.UI_STAGE_CHOOSING;
        }
        if(Math.abs(mX-Config.SCREEN_WIDTH/2)<=133 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 20) <=25) {
            Config.UI_CUR = Config.UI_HIGHSCORE;
            if(net == null) {
                try {
                    net = new Network();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            if(net!=null) net.update();
        }

        if(Math.abs(mX-Config.SCREEN_WIDTH/2)<=133 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 75) <=25) {
            loadFile();
            Config.UI_CUR = Config.UI_PLAYING;
        }
    }

    private void gameCompleteMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(Math.abs(mX-Config.SCREEN_WIDTH/2)<=60 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=17) {
            Config.UI_CUR = Config.UI_START;
            gameField = null;
            spawner = null;
        }
        if(net.isConnected() && net.isHighscore(gameField.getScore())) {
            if(Math.abs(mX - Config.SCREEN_WIDTH/2) <= 60 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 52) <= 17) {
                Config.UI_CUR = Config.UI_ADDSCORE;
            }
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

        if(Math.abs(mX - 150) <= 133 && Math.abs(mY - Config.SCREEN_HEIGHT + 45) <= 25) {
            Config.UI_CUR = Config.UI_START;
        }
    }

    private void playingMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(!gameField.isComplete())
        {
            if(Math.abs(mY-(10+Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2) {
                if(Math.abs(mX - (Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 20 - 1.5 * Config.TILE_SIZE)) <= Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= NormalTower.instance.getPrice()) curTower = new NormalTower(gameField,null);
                    showTower = null;
                }

                if(Math.abs(mX - (Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - Config.TILE_SIZE / 2)) <= Config.TILE_SIZE / 2) {
                    if(gameField.getPlayerMoney() >= RapidTower.instance.getPrice()) curTower = new RapidTower(gameField,null);
                    showTower = null;
                }

                if(Math.abs(mX - (Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + Config.TILE_SIZE/2)) <= Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= RangerTower.instance.getPrice()) curTower = new RangerTower(gameField,null);
                    showTower = null;
                }

                if(Math.abs(mX - (Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + 10 + Config.TILE_SIZE * 3/2)) <= Config.TILE_SIZE/2) {
                    if(gameField.getPlayerMoney() >= FlakTower.instance.getPrice()) curTower = new FlakTower(gameField,null);
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
                Audio.instance.sell();
            }

        }
    }

    private void pauseMouse(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        if(Math.abs(mX-Config.SCREEN_WIDTH/2) <=81 && Math.abs(mY - Config.SCREEN_HEIGHT/2)<=20) {
            Config.UI_CUR = Config.UI_PLAYING;
            gameField.resetTimer();
                spawner.resetTimer();

        }

        if(Math.abs(mX-Config.SCREEN_WIDTH/2) <=81 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 50)<=20) {
            Config.UI_CUR = Config.UI_START;
            gameField.resetTimer();
            spawner.resetTimer();
        }

        if(Math.abs(mX-Config.SCREEN_WIDTH/2) <=81 && Math.abs(mY - Config.SCREEN_HEIGHT/2 - 100)<=20) {
            try {
                saveFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    private void highscoreMouse(MouseEvent mouseEvent) {
        if(Math.abs(mX - 150) <= 133 && Math.abs(mY - Config.SCREEN_HEIGHT + 45) <= 25) {
            Config.UI_CUR = Config.UI_START;
        }
    }

    public final void mouseController(MouseEvent mouseEvent) {
        MouseButton mouseButton = mouseEvent.getButton();
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();
        mX = x;
        mY = y;
    }

    private void saveFile() throws FileNotFoundException {
        FileOutputStream file = new FileOutputStream("src/application/loadData");
        PrintStream out = new PrintStream(file);
        System.setOut(out);
        if (gameField == null) {
            System.out.println("false");
            return;
        }

        System.out.println("true");
        int n = Waypoints.instance.size();
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            Vector2 pos = Waypoints.instance.getIndex(i);
            System.out.println(pos.getX() + " " + pos.getY());
        }

        System.out.println(gameField.getData());

        System.out.println(spawner.toString());

        Config.UI_CUR = Config.UI_START;
    }

    private void loadFile() throws FileNotFoundException {
        File file = new File("src/application/loadData");
        Scanner scanner = new Scanner(file);
        boolean state = scanner.nextBoolean();

        List<Integer> pX = new ArrayList<>();
        List<Integer> pY = new ArrayList<>();
        int n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            int x = (int)(scanner.nextDouble() - Config.TILE_SIZE / 2) / Config.TILE_SIZE;
            int y = (int)(scanner.nextDouble() - Config.TILE_SIZE / 2) / Config.TILE_SIZE;
            pX.add(x);
            pY.add(y);
        }

        gameField = new GameField(pX,pY);
        gameRenderer.setGameField(gameField);

        Player player = new Player();

        int money = scanner.nextInt();
        int health = scanner.nextInt();
        int score = scanner.nextInt();
        player.setScore(score);
        player.setHealth(health);
        player.setMoney(money);

        gameField.setPlayer(player);

        n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            int type = scanner.nextInt();
            AbstractEnemy enemy;
            switch (type) {
                default: enemy = new NormalEnemy();break;
                case 1: enemy = new TankerEnemy();break;
                case 2: enemy = new AssassinEnemy();break;
                case 3: enemy = new BossEnemy();break;
            }

            double posX = scanner.nextDouble();
            double posY = scanner.nextDouble();
            health = scanner.nextInt();
            int curWp = scanner.nextInt();

            enemy.setCurrentWaypoints(curWp);
            enemy.setHealth(health);
            enemy.setPos(new Vector2(posX,posY));

            gameField.addEnemy(enemy);
        }

        n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            double posX = scanner.nextDouble();
            double posY = scanner.nextDouble();
            health = scanner.nextInt();
            double targetX = scanner.nextDouble();
            double targetY = scanner.nextDouble();

            AbstractAircraft aircraft = new Aircraft();
            aircraft.setDes(new Vector2(posX,posY),new Vector2(targetX,targetY));
            aircraft.setHealth(health);
            gameField.addAircraft(aircraft);
        }

        n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            int type = scanner.nextInt();
            double posX = scanner.nextDouble();
            double posY = scanner.nextDouble();
            int level = scanner.nextInt();
            double rotation = scanner.nextDouble();
            double reloadTimer = scanner.nextDouble();

            AbstractTower tower = null;

            switch (type) {
                case 0: tower = new NormalTower(gameField,new Vector2(posX,posY)); break;
                case 1: tower = new RapidTower(gameField,new Vector2(posX,posY)); break;
                case 2: tower = new RangerTower(gameField,new Vector2(posX,posY)); break;
                case 3: tower = new FlakTower(gameField,new Vector2(posX,posY)); break;
            }

            tower.setRotation(rotation);
            tower.setReloadTimer(reloadTimer);

            int haveTarget = scanner.nextInt();

            if(haveTarget == 1) {
                int no = scanner.nextInt();
                tower.setTarget(gameField.getEnemies().get(no));
            }
            if(haveTarget == 2) {
                int no = scanner.nextInt();
                tower.setTarget(gameField.getAircraft().get(no));
            }

            gameField.addTurret((int)posX,(int)posY,tower);
        }

        n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            int damage = scanner.nextInt();
            double speed =scanner.nextDouble();
            int no = scanner.nextInt();

            Bullet bullet = new Bullet(new Vector2(x,y),null,damage,speed,gameField.getTowers().get(no),gameField);

            int have = scanner.nextInt();
            no = scanner.nextInt();

            if(have == 1) bullet.setTarget(gameField.getEnemies().get(no));
            if(have == 2) bullet.setTarget(gameField.getAircraft().get(no));

            gameField.addBullet(bullet);
        }

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
}
