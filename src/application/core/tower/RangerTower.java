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
        gameField.addBullet(new Bullet(position,target,damage,30, Color.LIGHTYELLOW));
    }
}
