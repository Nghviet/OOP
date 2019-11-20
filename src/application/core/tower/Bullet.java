package application.core.tower;

import application.Config;
import application.core.GameEntity;
import application.core.GameField;
import application.core.enemy.Enemy;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class Bullet implements GameEntity {
    private Vector2 position;
    private Enemy target;
    private int damage;
    private double speed;

    private boolean isDestroyed;
    private boolean isCompleted;
    private double lastCall;
    private double effectTimer;

    private double AOE;

    private Color color;
    private Tower tower;

    private GameField gameField;

    public Bullet(Vector2 position, Enemy target, int damage, double speed,Tower tower,GameField gameField) {
        this.position = position;
        this.target = target;
        this.damage = damage;
        this.speed = speed;

        isDestroyed = false;
        isCompleted = false;
        lastCall = System.nanoTime()/ Config.timeDivide;
        this.tower = tower;
        this.gameField = gameField;

        if(tower instanceof RangerTower) {
            effectTimer = 15;
        }
        if(tower instanceof FlakTower) {
            effectTimer = 30;
            AOE = 70;
        }
    }

    public void update() {
        if(!isDestroyed) move();
        else {
            if(!isCompleted) {
                effect();
            }
        }
    }

    public void effect() {
        double cur = System.nanoTime()/ Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(effectTimer <= 0) {
            isCompleted = true;
            return;
        }

        effectTimer -= deltaTime;
    }

    public void move() {
        if(target == null || target.isDestroyed()) {
            isDestroyed = true;
            return;
        }

        double cur = System.nanoTime()/ Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(position.distanceTo(target.getPos()) <= 10) {
            isDestroyed = true;
            if(tower instanceof FlakTower) {
                for(Enemy enemy:gameField.getAircraft()) {
                    if(position.distanceTo(enemy.getPos()) <= 30) enemy.doDamage(damage);
                }
            }
            else
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

    public boolean isCompleted() {
        return  isCompleted;
    }

    public void resetTimer() {
        lastCall = System.nanoTime()/ Config.timeDivide;
    }

    public Tower getTower() {
        return tower;
    }

    public double getRotation() {
        Vector2 targetPosition = target.getPosition();
        double x = targetPosition.getX() - position.getX();
        double y = targetPosition.getY() - position.getY();

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
        return baseRotation;
    }
}
