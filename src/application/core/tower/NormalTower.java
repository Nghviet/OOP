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
        for(Vector2 position:firingPoint)
        gameField.addBullet(new Bullet(position,target,damage,10, Color.LIGHTBLUE,this));
    }

    @Override
    public void setFiringPoint() {
        firingPoint = new Vector2[1];
        firingPoint[0] = new Vector2(position);
    }

    @Override
    public void upgrade() {

    }
}
