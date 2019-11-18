package application.core.tower;

import application.core.GameField;
import application.core.enemy.Enemy;
import application.utility.Vector2;

public class FlakTower extends AbstractTower {

    public FlakTower(GameField gameField, Vector2 position) {
        super(gameField, position, 15, 500, 5, 5, 8);
    }

    @Override
    public void updateTarget() {
        for(Enemy air:gameField.getAircraft()) if(position.distanceTo(air.getPos()) <= range) {
            target = air;
            return;
        }
    }

    @Override
    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,40,this));
        }
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

        }
    }
}
