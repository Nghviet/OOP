package application.core;

import application.Config;
import application.core.enemy.AbstractEnemy;
import application.core.enemy.NormalEnemy;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.utility.Vector2;
import application.utility.Waypoints;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private List<Enemy> enemies;
    private GameTile[][] map;
    private int[][] gameFieldStatus;

    public GameField() {
        this.enemies = new ArrayList<>();

        this.map = new GameTile[Config.COUNT_HORIZONTAL][Config.COUNT_VERTICAL];
        for(int x = 0;x < Config.COUNT_HORIZONTAL;x++) for(int y = 0;y < Config.COUNT_VERTICAL;y++) {
            map[x][y] = new MapTile(new Vector2(x*Config.TILE_SIZE+Config.TILE_SIZE/2,y*Config.TILE_SIZE+Config.TILE_SIZE/2));
        }

        List<Vector2> t = new ArrayList<>();
        t.add(new Vector2(Config.TILE_SIZE/2,Config.TILE_SIZE/2));
        t.add(new Vector2(0 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(7 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(7 * Config.TILE_SIZE + Config.TILE_SIZE/2,3 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(19 * Config.TILE_SIZE + Config.TILE_SIZE/2,3 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(19 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));

        Waypoints.instance.setWaypoints(t);

        for(int i = 0;i < t.size()-1 ;i++) {
            Vector2 start = t.get(i);
            Vector2 end = t.get(i+1);

            int sX = (int) (Math.min(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eX = (int) (Math.max(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int sY = (int) (Math.min(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eY = (int) (Math.max(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;

            for(int x = sX;x<=eX;x++) for(int y = sY;y<=eY;y++) {
                map[x][y] = new Path(new Vector2(x*Config.TILE_SIZE+Config.TILE_SIZE/2,y*Config.TILE_SIZE+Config.TILE_SIZE/2));
            }
        }

        //Debug
    }

    public void update() {
        for(Enemy enemy:enemies) enemy.move();

        int i = enemies.size() - 1;
        while(i>=0) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed()) enemies.remove(i);
            i--;
        }
    }

    public void doSpawn() {
        enemies.add(new NormalEnemy(10,1,1,10));
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public GameTile[][] getMap() {
        return map;
    }
}
