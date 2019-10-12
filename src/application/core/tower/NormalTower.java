package application.core.tower;

import application.core.GameField;
import application.core.enemy.AbstractEnemy;
import application.utility.Vector2;

public class NormalTower extends AbstractTower {
    public NormalTower(GameField gameField, Vector2 position, double range, double reloadTime, double damage) {
        super(gameField, position, range, reloadTime, damage);
    }
}
