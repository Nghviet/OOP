package application.core.enemy;

import application.Config;
import application.core.player.Player;
import application.core.tile.Waypoints;
import application.utility.Vector2;

public abstract class  AbstractAircraft implements Enemy {
    private Vector2 pos;
    private Vector2 target;
    private int health;
    private int armor;
    private int reward;
    private int speed;

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

    public AbstractAircraft(int health, int armor, int reward, int speed, Player player,
                            Vector2 pos , Vector2 target) {
        this.health = health;
        this.armor = armor;
        this.reward = reward;
        this.speed = speed;

        this.pos = pos;
        this.target = target;
        lastCall = System.nanoTime()/ Config.timeDivide;

        this.player = player;

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

}
