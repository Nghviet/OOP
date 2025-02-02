package application.core;

import application.Config;
import application.core.enemy.AbstractEnemy;
import application.core.enemy.Aircraft;
import application.core.enemy.Enemy;
import application.core.player.Player;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.core.tower.AbstractTower;
import application.core.tower.Bullet;
import application.core.tower.Missle;
import application.core.tower.Tower;
import application.utility.Vector2;
import application.core.tile.Waypoints;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private List<Enemy> enemies;
    private List<Tower> towers;
    private List<Bullet> bullets;
    private List<Enemy> aircraft;
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

        aircraft = new ArrayList<>();
        //Debug
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

        aircraft = new ArrayList<>();
    }

    public void update() {
        if(complete) return;
        if(player.getHealth() <=0) {
            complete = true;
            return;
        }
        for(Enemy enemy:enemies) enemy.move();
        for(Tower tower:towers) tower.update();
        for(Bullet bullet:bullets) bullet.update();
        for(Enemy air:aircraft) air.move();

        int i = enemies.size() - 1;
        while(i>=0) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed()) enemies.remove(i);
            i--;
        }

        i = bullets.size() - 1;
        while(i>=0) {
            Bullet bullet = bullets.get(i);
            if(bullet.isCompleted()) bullets.remove(i);
            i--;
        }

        i = aircraft.size() - 1;
        while(i>= 0) {
            Enemy enemy = aircraft.get(i);
            if(enemy.isDestroyed()) aircraft.remove(i);
            i--;
        }
    }

    public void doSpawn() {
        Vector2 start = new Vector2(0,0);
        Vector2 end = new Vector2(Config.SCREEN_WIDTH,Config.SCREEN_HEIGHT);
        aircraft.add(new Aircraft(player,start,end));
    }

    public void doSpawn(Enemy enemy) {
        enemy.setPlayer(player);
        if(enemy instanceof AbstractEnemy)
        enemies.add(enemy);
        else aircraft.add(enemy);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addTurret(int x,int y,Tower tower) {
        x = x / Config.TILE_SIZE;
        y = y / Config.TILE_SIZE;
        System.out.println(x+" "+y);
        tower.setPosition(x * Config.TILE_SIZE + Config.TILE_SIZE/2,y*Config.TILE_SIZE + Config.TILE_SIZE/2);
        tower.resetTimer();
        towers.add(tower);
        ((MapTile) map[x][y]).build(tower);
    }

    public boolean isEmpty(int x,int y) {
        x = x / Config.TILE_SIZE;
        y = y / Config.TILE_SIZE;
        return (map[x][y] instanceof MapTile) && ((MapTile) map[x][y]).isBuildable();
    }

    public Tower tower(int x,int y) {
        x = x / Config.TILE_SIZE;
        y = y / Config.TILE_SIZE;
        if(map[x][y] instanceof MapTile) {
            MapTile t = (MapTile) map[x][y];
            return t.getTower();
        }
        return null;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Enemy> getAircraft() { return aircraft; }

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

    public int getScore() { return player.getScore(); }

    public boolean isComplete() {
        return complete;
    }

    public void charge(int charge){
        player.buy(charge);
    }

    public void removeTower(Tower tower) {
        Vector2 pos = tower.getPosition();
        towers.remove(tower);
        int x = (int)(pos.getX() - Config.TILE_SIZE/2) / Config.TILE_SIZE;
        int y = (int)(pos.getY() - Config.TILE_SIZE/2) / Config.TILE_SIZE;
        map[x][y] = new MapTile(pos);
    }
//----------------------------------------------------------------------------------------
    public String getData() {
        StringBuffer data = new StringBuffer(0);
        data.append(player.toString() + "\n");

        data.append(enemies.size()+"\n");
        for(Enemy enemy:enemies) {
            String e = enemy.toString() + "\n";
            int p = data.capacity();
            data.append(e);
        }

        data.append(aircraft.size()+"\n");
        for(Enemy air:aircraft) {
            String e = air.toString() + "\n";
            data.append(e);
        }

        data.append(towers.size()+"\n");
        for(Tower tower:towers) {
            String e = tower.toString() + "\n";
            data.append(e);
        }

        data.append(bullets.size()+"\n");
        for(Bullet bullet:bullets) {
            String e = bullet.toString()+"\n";
            data.append(e);
        }
        return data.toString();
    }

    public void setPlayer(Player player) {this.player = player;}

    public void addEnemy(Enemy enemy) {
        enemy.setPlayer(player);
        enemies.add(enemy);
    }

    public void addAircraft(Enemy air) {
        air.setPlayer(player);
        aircraft.add(air);
    }
}
