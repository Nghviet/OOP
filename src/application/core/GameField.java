package application.core;

import application.Config;
import application.core.enemy.AbstractEnemy;
import application.core.enemy.NormalEnemy;
import application.utility.Vector2;
import application.utility.Waypoints;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private List<Enemy> enemies;
    private int[][] gameFieldStatus;

    public GameField() {
        this.enemies = new ArrayList<>();
        this.gameFieldStatus = new int[Config.COUNT_HORIZONTAL][Config.COUNT_VERTICAL];
        for(int i=0;i<Config.COUNT_HORIZONTAL;i++) for(int j=0;j<Config.COUNT_VERTICAL;j++) gameFieldStatus[i][j] = 0;

        List<Vector2> t = new ArrayList<>();
        t.add(new Vector2(Config.TILE_SIZE/2,Config.TILE_SIZE/2));
        t.add(new Vector2(0 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(7 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(7 * Config.TILE_SIZE + Config.TILE_SIZE/2,3 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(19 * Config.TILE_SIZE + Config.TILE_SIZE/2,3 * Config.TILE_SIZE + Config.TILE_SIZE/2));
        t.add(new Vector2(19 * Config.TILE_SIZE + Config.TILE_SIZE/2,11 * Config.TILE_SIZE + Config.TILE_SIZE/2));


        for(int i = 0;i < t.size()-1 ;i++) {
            Vector2 start = t.get(i);
            Vector2 end = t.get(i+1);

            int sX = (int) (Math.min(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eX = (int) (Math.max(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int sY = (int) (Math.min(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eY = (int) (Math.max(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;

            for(int x = sX;x<=eX;x++) for(int y = sY;y<=eY;y++) gameFieldStatus[x][y] = 1;

        }

        Waypoints.instance.setWaypoints(t);
    }

    public int getColor(int x, int y) {
        return gameFieldStatus[x][y];
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
        System.out.println("Spawning");
        enemies.add(new NormalEnemy(10,1,1,10));
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
