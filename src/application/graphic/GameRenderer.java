package application.graphic;

import application.Config;
import application.Controller;
import application.core.Enemy;
import application.core.GameField;
import application.core.enemy.AbstractEnemy;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.utility.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameRenderer {
    private final GraphicsContext graphicsContext;
    private final GameField gameField;
    private Color UIColor;

    public GameRenderer(GraphicsContext graphicsContext,GameField gameField) {
        this.graphicsContext = graphicsContext;
        this.gameField = gameField;
        UIColor = Color.rgb(96,96,96);
    }

    public void render() {
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

        //*UI

        graphicsContext.setFill();
    }
}
