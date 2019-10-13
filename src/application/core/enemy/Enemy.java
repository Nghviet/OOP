package application.core.enemy;

import application.core.GameEntity;
import application.core.player.Player;
import application.utility.Vector2;

public interface Enemy extends GameEntity {
    public int getReward();

    public void doDamage(int damage);
    public void move();

    public Vector2 getPos();

    public boolean isDestroyed();

    public void resetTimer();

    public void setPlayer(Player player);
}
