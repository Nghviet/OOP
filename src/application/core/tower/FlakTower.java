package application.core.tower;

import application.core.GameField;
import application.core.audio.Audio;
import application.core.enemy.Enemy;
import application.utility.Vector2;

public class FlakTower extends AbstractTower {
    public static FlakTower instance = new FlakTower(null,null);
    public FlakTower(GameField gameField, Vector2 position) {
        super(gameField, position, 15, 500, 5, 5, 8,3);
    }

    @Override
    public void updateTarget() {
        double shiftRotation = 360;
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

    @Override
    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,40,this,gameField));
        }
        Audio.instance.flak();
    }

    @Override
    public void setFiringPoint() {
        if(level == 1) {
            dist = new int[1];
            angle = new int[1];
            dist[0] = 32;
            angle[0] = 0;
        }
        if(level == 2 || level == 3) {
            dist = new int[2];
            angle = new int[2];
            dist[0] = 35;
            dist[1] = 35;
            angle[0] = 15;
            angle[0] = -15;
        }
    }
}
