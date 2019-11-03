package application.core.player;

public class Player {

    private int money;
    private int health;

    public Player() {
        money = 100000;
        health = 10;
    }

    public void doDamage() {
        health--;
    }

    public void addReward(int reward) {
        money = money + reward;
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
