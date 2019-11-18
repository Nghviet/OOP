package application.core.enemy;

import application.core.GameEntity;
import application.core.player.Player;
import application.utility.Vector2;

public interface Enemy extends GameEntity {
    int getReward();

    void doDamage(int damage);
    void move();

    Vector2 getPos();

    boolean isDestroyed();

    void resetTimer();

    void setPlayer(Player player);

    int getDir();

    double getPercentage();
}
