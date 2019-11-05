package application.core.player;

public class Player {

    private int money;
    private int health;

    public Player() {
        money = 10;
        health = 10;
    }

    public void doDamage() {
        health--;
    }

    public void addReward(int reward) {
        money = money + reward * 10;
    }

    public void buy(int b) {
        money -= b;
    }

    public int getMoney() {
        return money;
    }

    public int getHealth() {
        return health;
    }
}
