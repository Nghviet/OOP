package application.core.enemy;

import application.Config;
import application.core.characteristic.Destroyable;
import application.core.characteristic.Updatable;
import application.utility.Vector2;
import application.utility.Waypoints;
public abstract class AbstractEnemy implements Enemy, Destroyable, Updatable {
    private Vector2 pos;
    private Vector2 target;
    private int health;
    private int armor;
    private int reward;
    private int speed;
    private int currentWaypoints;

    private double lastCall;
    private boolean destroyed = false;

    @Override
    public void doDamage(int damage) {

        health -= damage *(5 - armor);
        if(health<=0) {
            destroyed = true;
            doDestroy();
        }
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    public AbstractEnemy(int health, int armor, int reward, int speed) {
        this.health = health;
        this.armor = armor;
        this.reward = reward;
        this.speed = speed;

        this.pos = Waypoints.instance.getIndex(0);
        this.target = Waypoints.instance.getIndex(1);
        currentWaypoints = 1;
        lastCall = System.nanoTime()/Config.timeDivide;

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
    }

    private void changeWaypoints() {
        currentWaypoints++;
        if(currentWaypoints == Waypoints.instance.size()){
            destroyed = true;
            doDestroy();
            return;
        }
        target = Waypoints.instance.getIndex(currentWaypoints);
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
}
