package application.graphic;

import application.Config;
import application.Controller;
import application.core.enemy.Enemy;
import application.core.GameField;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.core.tower.*;
import application.utility.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameRenderer {
    private GraphicsContext graphicsContext;
    private GameField gameField;
    private Color UIColor;
    private Controller gameController;
    public GameRenderer(GraphicsContext graphicsContext,Controller gameController) {
        this.graphicsContext = graphicsContext;
        this.gameField = null;
        this.gameController = gameController;
        UIColor = Color.rgb(96,96,96);
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public void render() {
        graphicsContext.clearRect(0,0,Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        switch(Config.UI_CUR) {
            case Config.UI_START: {
                startRender();
                break;
            }
            case Config.UI_PLAYING: {
                gameRender();
                if(gameField.isComplete()) {
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.fillRect(Config.SCREEN_WIDTH/2-100,Config.SCREEN_HEIGHT/2+20,200,100);
                }
                break;
            }
            case Config.UI_PAUSE: {
                pauseRender();
                break;
            }
        }
    }

    private void startRender() {
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(Config.SCREEN_WIDTH/2-100,Config.SCREEN_HEIGHT/2-50,200,100);
    }

    private void gameRender() {
        GameTile[][] map = gameField.getMap();

        for(int x = 0;x < Config.COUNT_HORIZONTAL; x++) for(int y = 0;y < Config.COUNT_VERTICAL; y++) {
            GameTile tile = map[x][y];
            if(tile instanceof Path) {
                graphicsContext.setFill(Color.BLACK);
            }
            else
            if(tile instanceof MapTile) {
                graphicsContext.setFill(Color.GREEN);
            }

            Vector2 p = tile.getPosition();

            graphicsContext.fillRect(p.getX()-Config.TILE_SIZE/2,p.getY()-Config.TILE_SIZE/2,Config.TILE_SIZE,Config.TILE_SIZE);
        }

        graphicsContext.setFill(Color.RED);
        List<Enemy> enemies = gameField.getEnemies();
        for(Enemy enemy:enemies) {
            Vector2 pos = enemy.getPosition();
            graphicsContext.fillRect(pos.getX()-8,pos.getY()-8,16,16);
        }

        List<Tower> towers = gameField.getTowers();

        if(towers !=null)
            for(Tower tower:towers) {
                if(tower instanceof NormalTower) {
                    graphicsContext.setFill(Color.BLUE);
                }
                if(tower instanceof RangerTower) {
                    graphicsContext.setFill(Color.YELLOW);
                }
                if(tower instanceof RapidTower) {
                    graphicsContext.setFill(Color.RED);
                }
                Vector2 pos = tower.getPosition();
                graphicsContext.fillRect(pos.getX()-8,pos.getY()-8,16,16);
            }

        List<Bullet> bullets = gameField.getBullets();
        for(Bullet bullet:bullets) {
            graphicsContext.setFill(bullet.getColor());
            Vector2 pos = bullet.getPosition();
            graphicsContext.fillRect(pos.getX()-3,pos.getY()-3,6,6);
        }

        //*UI
        graphicsContext.setFill(UIColor);
        graphicsContext.fillRect(Config.SCREEN_WIDTH-Config.UI_HORIZONTAL,0,Config.UI_HORIZONTAL,Config.SCREEN_HEIGHT);
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillText("Money:"+gameField.getPlayerMoney(),Config.GAME_WIDTH+1,Config.SCREEN_HEIGHT/2-50);
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText("Health:"+gameField.getPlayerHealth(),Config.GAME_WIDTH+1,Config.SCREEN_HEIGHT/2-20);

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(Config.GAME_WIDTH+Config.UI_HORIZONTAL/2 - Config.TILE_SIZE/2,20,Config.TILE_SIZE,Config.TILE_SIZE);

        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillRect(Config.GAME_WIDTH+Config.UI_HORIZONTAL/2 - Config.TILE_SIZE/2,60,Config.TILE_SIZE,Config.TILE_SIZE);

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(Config.GAME_WIDTH+Config.UI_HORIZONTAL/2  - Config.TILE_SIZE/2 + 40,20,Config.TILE_SIZE,Config.TILE_SIZE);

    //    graphicsContext.setFill(Color.BROWN);
    //    graphicsContext.fillRect(Config.GAME_WIDTH+Config.UI_HORIZONTAL/2  - Config.TILE_SIZE/2 + 40,60,Config.TILE_SIZE,Config.TILE_SIZE);

        //mouse
        int x = gameController.mX;
        int y = gameController.mY;
        if(gameController.curTower != null) {
            if(gameController.curTower instanceof NormalTower) {
                graphicsContext.setFill(Color.BLUE);
            }
            if(gameController.curTower instanceof RangerTower) {
                graphicsContext.setFill(Color.YELLOW);
            }
            if(gameController.curTower instanceof RapidTower) {
                graphicsContext.setFill(Color.RED);
            }
            graphicsContext.fillRect(x-8,y-8,16,16);
        }
    }

    private void pauseRender() {
        gameRender();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(Config.SCREEN_WIDTH/2-100,Config.SCREEN_HEIGHT/2-50,200,100);
    }
}
