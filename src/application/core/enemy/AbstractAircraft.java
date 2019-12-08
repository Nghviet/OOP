package application.core.enemy;

import application.Config;
import application.core.player.Player;
import application.core.tile.Waypoints;
import application.utility.Vector2;

import java.util.Random;

public abstract class  AbstractAircraft implements Enemy {
    private Vector2 pos;
    private Vector2 target;
    private int health;
    private int armor;
    private int reward;
    private int speed;

    private int maxHP;
    private int distance;

    private double rotation;

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
        }
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    public AbstractAircraft(int health, int armor, int reward, int speed, Player player) {
        this.health = health;
        this.armor = armor;
        this.reward = reward;
        this.speed = speed;
        this.maxHP = health;
        this.distance = 0;

        lastCall = System.nanoTime()/ Config.timeDivide;

        this.player = player;

        Random r = new Random();
        int seed = r.nextInt(3);
        if(seed == 0) {
            setDes(new Vector2(0,0),new Vector2(Config.GAME_WIDTH,Config.GAME_HEIGHT));
        }
        if(seed == 1) {
            setDes(new Vector2(0,Config.GAME_HEIGHT/2),new Vector2(Config.GAME_WIDTH,Config.GAME_HEIGHT/2));
        }
        if(seed == 2) {
            setDes(new Vector2(0,Config.GAME_HEIGHT),new Vector2(Config.GAME_WIDTH,0));
        }
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
        distance += deltaTime * speed;

        if(pos.distanceTo(target) < 0.5) {
            destroyed = true;
            player.doDamage();
            return;
        }
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

    public double getRotation() {
        return rotation;
    }

    public void setDes(Vector2 pos,Vector2 target) {
        this.pos = pos;
        this.target = target;

        Vector2 targetPosition = target;
        double x = targetPosition.getX() - pos.getX();
        double y = targetPosition.getY() - pos.getY();

        double baseRotation = Math.abs(Math.toDegrees(Math.asin(y/Math.sqrt(x*x+y*y))));
        if(x<0 && y<0) baseRotation +=180;
        if(x<0 && y>0) baseRotation = 180 - baseRotation;
        if(x>0 && y<0) baseRotation = 360 - baseRotation;
        if(x==0) {
            if(y>0) baseRotation = 90;
            else baseRotation = 270;
        }
        if(y==0) {
            if(x>0) baseRotation = 0;
            else baseRotation = 180;
        }

        this.rotation = baseRotation;
    }

    @Override
    public double getPercentage() {
         return health * 100 / maxHP;
    }

    public String toString() {
        String data = pos.getX() + " " + pos.getY() + " " + health + " " + target.getX() + " "+ target.getY();
        return data;
    }

    @Override
    public int getDir() {
        return 0;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
