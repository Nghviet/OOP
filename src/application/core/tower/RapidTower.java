package application.core.tower;

import application.core.GameField;
import application.core.enemy.Enemy;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RapidTower extends AbstractTower {
    public static RapidTower instance = new RapidTower(null,null);
    public RapidTower(GameField gameField, Vector2 position) {
        super(gameField, position, 15,150, 5, 1, 3);
    }

    @Override
    public void updateTarget() {
        for(Enemy enemy: gameField.getEnemies()) if(position.distanceTo(enemy.getPos()) < range) { target = enemy; return; }
    }

    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,50,this,gameField));
        }
    }

    public void setFiringPoint() {
        if(level == 2) {
            dist = new int[2];
            angle = new int[2];

            dist[0] = 34;
            dist[1] = 34;

            angle[0] = 20;
            angle[1] = -20;
        }
        if(level == 3) {
            dist = new int[3];
            angle = new int[3];

            dist[0] = 34;
            dist[1] = 32;
            dist[2] = 34;

            angle[0] = 20;
            angle[1] = 0;
            angle[2] = -20;
            range += 100;
        }
    }

}
