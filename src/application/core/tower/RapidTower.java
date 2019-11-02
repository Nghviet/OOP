package application.core.tower;

import application.core.GameField;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RapidTower extends AbstractTower {
    public static RapidTower instance = new RapidTower(null,null);
    public RapidTower(GameField gameField, Vector2 position) {
        super(gameField, position, 15,150, 1, 1, 3);
    }

    public void shoot() {
        for(int i=0;i<dist.length;i++) {
            double a = Math.toRadians(angle[i]+rotation);
            Vector2 pos = position.minus(new Vector2(-dist[i]*Math.cos(a),-dist[i]*Math.sin(a)));
            gameField.addBullet(new Bullet(pos,target,damage,50,this));
        }
    }

    public void setFiringPoint() {

    }

}
