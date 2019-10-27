package application.core.tower;

import application.core.GameField;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RangerTower extends AbstractTower {
    public static RangerTower instance = new RangerTower(null,null);

    public RangerTower(GameField gameField, Vector2 position) {
        super(gameField, position,10, 250 , 10, 3, 5);
    }

    public void shoot() {
        for(Vector2 position:firingPoint)
        gameField.addBullet(new Bullet(position,target,damage,30, Color.LIGHTYELLOW));
    }

    public void setFiringPoint() {
        firingPoint = new Vector2[1];
        firingPoint[0] = new Vector2(position);
    }

    @Override
    public void upgrade() {

    }
}
