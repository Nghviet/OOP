package application.graphic;

import application.Config;
import application.core.enemy.Enemy;
import application.core.GameField;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.core.tower.AbstractTower;
import application.core.tower.Bullet;
import application.core.tower.Tower;
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

        List<Tower> towers = gameField.getTowers();
        graphicsContext.setFill(Color.BLUE);
        if(towers !=null)
        for(Tower tower:towers) {
            Vector2 pos = tower.getPosition();
            graphicsContext.fillRect(pos.getX()-8,pos.getY()-8,16,16);
        }

        graphicsContext.setFill(Color.YELLOW);
        List<Bullet> bullets = gameField.getBullets();
        for(Bullet bullet:bullets) {
            Vector2 pos = bullet.getPosition();
            graphicsContext.fillRect(pos.getX()-3,pos.getY()-3,6,6);
        }

        //*UI
        graphicsContext.setFill(UIColor);
        graphicsContext.fillRect(Config.SCREEN_WIDTH-Config.UI_HORIZONTAL,0,Config.UI_HORIZONTAL,Config.SCREEN_HEIGHT);
    }
}
