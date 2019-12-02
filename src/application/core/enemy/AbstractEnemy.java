package application.core.enemy;

import application.Config;
import application.core.characteristic.Destroyable;
import application.core.characteristic.Updatable;
import application.core.player.Player;
import application.utility.Vector2;
import application.core.tile.Waypoints;
public abstract class AbstractEnemy implements Enemy, Destroyable, Updatable {
    private Vector2 pos;
    private Vector2 target;
    private int health;
    private int armor;
    private int reward;
    private int speed;
    private int currentWaypoints;
    private int dir;

    private int maxHP;
    private double distance;

    private int type;
    private double lastCall;
    private boolean destroyed = false;

    private Player player;

    @Override
    public void doDamage(int damage) {
        health -= Math.max(damage *(7 - armor),0);
        if(health<=0) {
            System.out.println("Add reward:"+reward);
            player.addReward(reward);
            destroyed = true;
            doDestroy();
        }
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    public AbstractEnemy(int health, int armor, int reward, int speed, Player player,int type) {
        this.health = health;
        this.armor = armor;
        this.reward = reward;
        this.speed = speed;
        this.maxHP = health;
        this.distance = 0;
        this.pos = Waypoints.instance.getIndex(0);
        this.target = Waypoints.instance.getIndex(1);
        currentWaypoints = 1;
        lastCall = System.nanoTime()/Config.timeDivide;

        this.player = player;

        Vector2 cur = Waypoints.instance.getIndex(currentWaypoints-1);
        if(target.getX() == cur.getX()) {
            if(target.getY() < cur.getY()) dir = 3;
            else dir = 1;
        }
        else {
            if(target.getX() > cur.getX()) dir = 0;
            else dir = 2;
        }

        this.type = type;

    }

    public void update() {
        move();
    }

    public void move() {
        Vector2 dir = target.minus(pos);
        dir.normalize();
        double currentTime = System.nanoTime()/ Config.timeDivide;
        double deltaTime = currentTime - lastCall;
        lastCall = currentTime;
        pos = new Vector2(  pos.getX() + dir.getX() * deltaTime * speed ,
                            pos.getY() + dir.getY() * deltaTime * speed  );
        if(pos.distanceTo(target) <= 0.1) changeWaypoints();
        distance += deltaTime * speed;
    }

    private void changeWaypoints() {
        currentWaypoints++;
        if(currentWaypoints == Waypoints.instance.size()){
            destroyed = true;
            player.doDamage();
            doDestroy();
            return;
        }
        target = Waypoints.instance.getIndex(currentWaypoints);
        Vector2 cur = Waypoints.instance.getIndex(currentWaypoints-1);
        if(target.getX() == cur.getX()) {
            if(target.getY() < cur.getY()) dir = 3;
            else dir = 1;
        }
        else {
            if(target.getX() > cur.getX()) dir = 0;
            else dir = 2;
        }
    }

    @Override
    public void doDestroy() {

    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public int getReward() {
        return 0;
    }

    @Override
    public Vector2 getPos() {
        return pos;
    }

    public void resetTimer() {
        lastCall = System.nanoTime()/ Config.timeDivide;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int getDir() {
        return dir;
    }

    @Override
    public double getPercentage() {
        return health * 100 / maxHP;
    }

    public String toString() {
        String data = type + " " + pos.getX() + " " + pos.getY() + " " + health + " " + currentWaypoints;
        return data;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void setCurrentWaypoints(int curWp) {
        System.out.println(curWp);
        this.currentWaypoints = curWp;
        target = Waypoints.instance.getIndex(currentWaypoints);
        Vector2 cur = Waypoints.instance.getIndex(currentWaypoints-1);
        if(target.getX() == cur.getX()) {
            if(target.getY() < cur.getY()) dir = 3;
            else dir = 1;
        }
        else {
            if(target.getX() > cur.getX()) dir = 0;
            else dir = 2;
        }
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
