package application.core;

import application.Config;
import application.core.enemy.Enemy;
import application.core.enemy.NormalEnemy;
import application.core.enemy.TankerEnemy;
import application.core.player.Player;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.core.tower.Bullet;
import application.core.tower.NormalTower;
import application.core.tower.Tower;
import application.utility.Vector2;
import application.core.tile.Waypoints;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameField {
    private List<Enemy> enemies;
    private List<Tower> towers;
    private List<Bullet> bullets;
    private GameTile[][] map;
    private Player player;

    private boolean complete;

    public GameField() {
        this.enemies = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.bullets = new ArrayList<>();
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

        for(int i = 0;i < Waypoints.instance.size()-1 ;i++) {
            Vector2 start = Waypoints.instance.getIndex(i);
            Vector2 end = Waypoints.instance.getIndex(i+1);

            int sX = (int) (Math.min(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eX = (int) (Math.max(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int sY = (int) (Math.min(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eY = (int) (Math.max(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;

            for(int x = sX;x<=eX;x++) for(int y = sY;y<=eY;y++) {
                map[x][y] = new Path(new Vector2(x*Config.TILE_SIZE+Config.TILE_SIZE/2,y*Config.TILE_SIZE+Config.TILE_SIZE/2));
            }
        }

        player = new Player();

        //Debug
    }

    public void update() {
        if(complete) return;
        if(player.getHealth() <=0) {
            complete = true;
            return;
        }
        for(Enemy enemy:enemies) enemy.move();
        for(Tower tower:towers) tower.update();
        for(Bullet bullet:bullets) bullet.move();

        int i = enemies.size() - 1;
        while(i>=0) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed()) enemies.remove(i);
            i--;
        }

        i = bullets.size() - 1;
        while(i>=0) {
            Bullet bullet = bullets.get(i);
            if(bullet.isDestroyed()) bullets.remove(i);
            i--;
        }
    }

    public void doSpawn() {
        enemies.add(new TankerEnemy(player));
    }

    public void doSpawn(Enemy enemy) {
        enemy.setPlayer(player);
        enemies.add(enemy);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addTurret(int x,int y,Tower tower) {
        x = x / Config.TILE_SIZE;
        y = y / Config.TILE_SIZE;
        System.out.println(x+" "+y);
        tower.setPosition(x * Config.TILE_SIZE + Config.TILE_SIZE/2,y*Config.TILE_SIZE + Config.TILE_SIZE/2);
        towers.add(tower);
        ((MapTile) map[x][y]).build(tower);
    }

    public boolean isEmpty(int x,int y) {
        x = x / Config.TILE_SIZE;
        y = y / Config.TILE_SIZE;
        return (map[x][y] instanceof MapTile) && ((MapTile) map[x][y]).isBuildable();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public GameTile[][] getMap() {
        return map;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void resetTimer() {
        for(Enemy enemy:enemies) enemy.resetTimer();
        for(Tower tower:towers) tower.resetTimer();
        for(Bullet bullet:bullets) bullet.resetTimer();
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getPlayerMoney() {
        return player.getMoney();
    }

    public boolean isComplete() {
        return complete;
    }

    public void charge(int charge){
        player.buy(charge);
    }

    public GameField(List<Integer> pX,List<Integer> pY) {
        this.enemies = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.map = new GameTile[Config.COUNT_HORIZONTAL][Config.COUNT_VERTICAL];
        for(int x = 0;x < Config.COUNT_HORIZONTAL;x++) for(int y = 0;y < Config.COUNT_VERTICAL;y++) {
            map[x][y] = new MapTile(new Vector2(x*Config.TILE_SIZE+Config.TILE_SIZE/2,y*Config.TILE_SIZE+Config.TILE_SIZE/2));
        }
        player = new Player();
        List<Vector2> waypoints = new ArrayList<>();

        for(int i=0;i<pX.size();i++) {
            int x = pX.get(i) * Config.TILE_SIZE + Config.TILE_SIZE/2;
            int y = pY.get(i) * Config.TILE_SIZE + Config.TILE_SIZE/2;
            waypoints.add(new Vector2(x,y));
        }

        Waypoints.instance.setWaypoints(waypoints);

        for(int i = 0;i < Waypoints.instance.size()-1 ;i++) {
            Vector2 start = Waypoints.instance.getIndex(i);
            Vector2 end = Waypoints.instance.getIndex(i+1);

            int sX = (int) (Math.min(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eX = (int) (Math.max(start.getX(),end.getX()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int sY = (int) (Math.min(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;
            int eY = (int) (Math.max(start.getY(),end.getY()) - Config.TILE_SIZE/2 ) / Config.TILE_SIZE;

            for(int x = sX;x<=eX;x++) for(int y = sY;y<=eY;y++) {
                map[x][y] = new Path(new Vector2(x*Config.TILE_SIZE+Config.TILE_SIZE/2,y*Config.TILE_SIZE+Config.TILE_SIZE/2));
            }
        }
    }

}
