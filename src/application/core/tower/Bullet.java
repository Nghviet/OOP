package application.core.tower;

import application.Config;
import application.core.GameEntity;
import application.core.enemy.Enemy;
import application.utility.Vector2;

public class Bullet implements GameEntity {
    private Vector2 position;
    private Enemy target;
    private int damage;
    private double speed;

    private boolean isDestroyed;
    private double lastCall;

    public Bullet(Vector2 position, Enemy target, int damage, double speed) {
        this.position = position;
        this.target = target;
        this.damage = damage;
        this.speed = speed;

        isDestroyed = false;
        lastCall = System.nanoTime()/ Config.timeDivide;
    }

    public void move() {
        if(target == null || target.isDestroyed()) {
            isDestroyed = true;
            return;
        }

        double cur = System.nanoTime()/ Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(position.distanceTo(target.getPos()) <= 3) {
            isDestroyed = true;
            target.doDamage(damage);
            return;
        }

        Vector2 dir= target.getPos().minus(position);
        dir.normalize();
        position = new Vector2(position.getX() + dir.getX() * deltaTime * speed ,
                               position.getY() + dir.getY() * deltaTime * speed  );

    }


    @Override
    public Vector2 getPosition() {
        return position;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

}
