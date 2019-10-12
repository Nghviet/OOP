package application.graphic;

import application.Config;
import application.Controller;
import application.core.Enemy;
import application.core.GameField;
import application.core.enemy.AbstractEnemy;
import application.utility.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameRenderer {
    private final GraphicsContext graphicsContext;
    private final GameField gameField;
    public GameRenderer(GraphicsContext graphicsContext,GameField gameField) {
        this.graphicsContext = graphicsContext;
        this.gameField = gameField;
    }

    public void render() {
        for(int i=0;i< Config.COUNT_HORIZONTAL;i++) for(int j=0;j<Config.COUNT_VERTICAL;j++) {
            graphicsContext.setFill(Config.colorTiles[gameField.getColor(i,j)]);
            graphicsContext.fillRect(i*Config.TILE_SIZE,j*Config.TILE_SIZE,
                                          Config.TILE_SIZE,     Config.TILE_SIZE);
        }

        graphicsContext.setFill(Color.RED);

        List<Enemy> enemies = gameField.getEnemies();
        for(Enemy enemy:enemies) {
            Vector2 pos = enemy.getPosition();
            graphicsContext.fillRect(pos.getX()-8,pos.getY()-8,16,16);
        }
    }
}
