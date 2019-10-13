package application.core.tower;

import application.Config;
import application.core.enemy.Enemy;
import application.core.GameField;
import application.utility.Vector2;

import java.util.List;

public abstract class AbstractTower implements Tower {

    Enemy target;
    GameField gameField;
    Vector2 position;
    private double rotation;

    private double range;
    private double reloadTimer;
    private double reloadTime;
    int damage;
    private double lastCall;

    private int price;

    public AbstractTower(GameField gameField, Vector2 position, double range, double reloadTime, int damage, int price) {
        this.gameField = gameField;
        this.position = position;
        this.range = range;
        this.reloadTime = reloadTime;
        this.damage = damage;

        rotation = 0;
        reloadTimer = 0;
        target = null;
        lastCall = System.nanoTime()/(1e8);

        this.price = price;
    }

    @Override
    public void update() {
        double cur = System.nanoTime()/(1e8);
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(target == null || (target!=null && (target.getPos().distanceTo(position) > range || target.isDestroyed()))) {
            target = null;
            for(int i = gameField.getEnemies().size()-1;i>=0;i--) if(gameField.getEnemies().get(i).getPos().distanceTo(position)<range)
                target = gameField.getEnemies().get(i);
        }

        reloadTimer -= deltaTime;
        if(reloadTimer <=0) {
            shoot();
            reloadTimer = reloadTime;
        }

    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public void resetTimer() {
        lastCall = System.nanoTime()/ Config.timeDivide;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public abstract void shoot();

    public void setPosition(int x,int y) {
        this.position = new Vector2(x, y);
    }
}
