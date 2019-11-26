package application.core.tower;

import application.core.GameField;
import application.core.enemy.Enemy;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RangerTower extends AbstractTower {
    public static RangerTower instance = new RangerTower(null,null);

    public RangerTower(GameField gameField, Vector2 position) {
        super(gameField, position,10, 750 , 30, 3, 5);
    }

    @Override
    public void updateTarget() {
        double shiftRotation = 360;
        for(Enemy enemy: gameField.getEnemies()) if(position.distanceTo(enemy.getPos()) < range) {
            if(target == null) {
                target = enemy;
                shiftRotation = position.angleTo(target.getPos()) - rotation;
            }
            else {
                double angle = position.angleTo(enemy.getPos()) - rotation;
                if(Math.abs(shiftRotation) > Math.abs(angle)) {
                    target = enemy;
                    shiftRotation = angle;
                }
            }
        }
        for(Enemy enemy: gameField.getAircraft()) if(position.distanceTo(enemy.getPos()) < range) {
            if(target == null) {
                target = enemy;
                shiftRotation = position.angleTo(target.getPos()) - rotation;
            }
            else {
                double angle = position.angleTo(enemy.getPos()) - rotation;
                if(Math.abs(shiftRotation) > Math.abs(angle)) {
                    target = enemy;
                    shiftRotation = angle;
                }
            }
        }
    }

    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,30,this,gameField));
        }
    }

    public void setFiringPoint() {
        if(level == 1 || level == 2) {
            dist = new int[1];
            angle = new int[1];

            dist[0] = 10;

            angle[0] = 0;
        }
        if(level == 3) {
            dist = new int[2];
            angle = new int[2];

            dist[0] = 15;
            dist[1] = 15;

            angle[0] = 90;
            angle[1] = -90;
        }
    }

}
