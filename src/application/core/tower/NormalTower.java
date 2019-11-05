package application.core.tower;

import application.core.GameField;
import application.core.enemy.AbstractEnemy;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class NormalTower extends AbstractTower {
    public static NormalTower instance = new NormalTower(null,null);

    public NormalTower(GameField gameField, Vector2 position) {
        super(gameField, position,10, 550, 3, 1,1);
    }

    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,40,this));
        }
        //gameField.addBullet(new Bullet(position,target,10,10,this));
    }

    @Override
    public void setFiringPoint() {
        if(level > 1) {
            dist = new int[2];
            angle = new int[2];

            dist[0] = 34;
            dist[1] = 34;

            angle[0] = 20;
            angle[1] = -20;
        }
    }

}
