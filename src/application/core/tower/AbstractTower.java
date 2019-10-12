package application.core.tower;

import application.core.enemy.Enemy;
import application.core.GameField;
import application.utility.Vector2;

import java.util.List;

public abstract class AbstractTower implements Tower {

    private Enemy target;
    private GameField gameField;
    private Vector2 position;
    private double rotation;

    private double range;
    private double reloadTimer;
    private double reloadTime;
    private double damage;
    private double lastCall;

    public AbstractTower(GameField gameField, Vector2 position, double range, double reloadTime, double damage) {
        this.gameField = gameField;
        this.position = position;
        this.range = range;
        this.reloadTime = reloadTime;
        this.damage = damage;

        rotation = 0;
        reloadTimer = 0;
        target = null;
        lastCall = System.nanoTime()/(1e8);
    }

    @Override
    public void update() {
        double cur = System.nanoTime()/(1e8);
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(target == null || (target!=null && (target.getPos().distanceTo(position) > range || target.isDestroyed()))) {
            for(int i = gameField.getEnemies().size()-1;i>=0;i--) if(gameField.getEnemies().get(i).getPos().distanceTo(position)<range)
                target = gameField.getEnemies().get(i);
        }

        reloadTimer -= deltaTime;
        if(reloadTimer <=0) {
            gameField.addBullet(new Bullet(position,target,10,10));
            reloadTimer = reloadTime;
        }

    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
