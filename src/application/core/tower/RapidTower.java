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
        gameField.addBullet(new Bullet(position,target,damage,50, Color.LIGHTPINK));
    }
}
