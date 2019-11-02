package application.core.tower;

import application.core.GameEntity;
import application.core.GameField;
import application.core.tile.GameTile;

public interface Tower extends GameTile {
    void update();

    void resetTimer();

    int getPrice();

    void setPosition(int x, int y);

    double getRange();

    double getRotation();

    boolean isReloaded();

    int getLevel();

    int getDamage();

    void upgrade();
}
