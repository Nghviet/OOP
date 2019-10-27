package application.core.tower;

import application.core.GameField;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class RapidTower extends AbstractTower {
    public static RapidTower instance = new RapidTower(null,null);
    public RapidTower(GameField gameField, Vector2 position) {
        super(gameField, position, 15,50, 1, 1, 3);
    }

    public void shoot() {
        for(Vector2 position:firingPoint)
        gameField.addBullet(new Bullet(position,target,damage,50, Color.LIGHTPINK));
    }

    public void setFiringPoint() {
        firingPoint = new Vector2[1];
        firingPoint[0] = new Vector2(position);
    }

    @Override
    public void upgrade() {

    }
}
