package application.core.tower;

import application.core.GameEntity;
import application.core.GameField;
import application.core.tile.GameTile;

public interface Tower extends GameTile {
    public void update();

    public void resetTimer();

    public int getPrice();

    public void setPosition(int x,int y);
}
