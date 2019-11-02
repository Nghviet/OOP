package application.core.tower;

import application.core.GameField;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RangerTower extends AbstractTower {
    public static RangerTower instance = new RangerTower(null,null);

    public RangerTower(GameField gameField, Vector2 position) {
        super(gameField, position,10, 750 , 50, 3, 5);
    }

    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,30,this));
        }
    }

    public void setFiringPoint() {

    }

}
