package application.core.tower;

import application.core.GameField;
import application.core.enemy.AbstractEnemy;
import application.utility.Vector2;
import javafx.scene.paint.Color;

public class NormalTower extends AbstractTower {
    public static NormalTower instance = new NormalTower(null,null);

    public NormalTower(GameField gameField, Vector2 position) {
        super(gameField, position, 100, 3, 1,1);
    }

    public void shoot() {
        gameField.addBullet(new Bullet(position,target,damage,10, Color.LIGHTBLUE));
    }
}
