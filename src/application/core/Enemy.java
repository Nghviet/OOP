package application.core;

public interface Enemy extends GameEntity {
    public int getReward();

    public void doDamage(int damage);
    public void move();

    public boolean isDestroyed();
}
