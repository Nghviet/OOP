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
    protected double rotation;

    protected double rotationSpeed;
    protected double range;
    protected double reloadTimer;
    protected double reloadTime;
    int damage;
    protected double lastCall;

    protected int price;

    protected int[] dist;
    protected int[] angle;
    protected int level;

    public AbstractTower(GameField gameField, Vector2 position, double rotationSpeed, double range, double reloadTime, int damage,
                         int price) {
        this.gameField = gameField;
        this.position = position;
        this.range = range;
        this.reloadTime = reloadTime;
        this.damage = damage;
        this.rotationSpeed = rotationSpeed;
        rotation = 0;
        reloadTimer = 0;
        target = null;
        lastCall = System.nanoTime()/(1e8);

        this.price = price;
        rotation = 0;

        dist = new int[1];
        angle = new int[1];

        dist[0] = Config.TILE_SIZE/2;
        angle[0] = 0;
        level = 1;
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
        if(reloadTimer<0) reloadTimer = 0;
        if(target!=null) {
            double rotateTo = getTargetRotation();
            double rotate = deltaTime * rotationSpeed;
            if(rotation > 180) {
                if(rotation > rotateTo && rotateTo > rotation - 180) {
                    if(Math.abs(rotation - rotateTo) < rotate) rotation = rotateTo;
                    rotation -= rotate;
                }
                else {
                    if(Math.abs(rotation - rotateTo) < rotate) rotation = rotateTo;
                    rotation += rotate;
                }
            }
            else {
                if(rotation < rotateTo && rotateTo < rotation + 180) {
                    if(Math.abs(rotation - rotateTo) < rotate) rotation = rotateTo;
                    rotation += rotate;
                }
                else {
                    if(Math.abs(rotation - rotateTo) < rotate) rotation = rotateTo;
                    rotation -= rotate;
                }
            }

            if(rotation > 360) rotation -= 360;
            if(rotation < 0) rotation += 360;

            if(reloadTimer <=0 && Math.abs(rotation - rotateTo) <= 5) {
                shoot();
                reloadTimer = reloadTime;
            }
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
        setFiringPoint();
    }

    @Override
    public double getRange() {
        return range;
    }

    @Override
    public double getRotation() { return rotation; }

    private double getTargetRotation() {
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


    @Override
    public void upgrade() {
        if(level < 3) {
            level ++;
            setFiringPoint();
            price += 2;
            damage++;
        }
    }

    public abstract void setFiringPoint();

    @Override
    public boolean isReloaded() {
        return (reloadTimer==0);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
