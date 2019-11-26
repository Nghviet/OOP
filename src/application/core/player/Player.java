package application.core.player;

public class Player {

    private int money;
    private int health;
    private int score;
    public Player() {
        money = 10;
        health = 10;
        score = 0;
    }

    public void doDamage() {
        health--;
    }

    public void addReward(int reward) {
        money = money + reward * 10;
        score += reward * 10000;
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

    public int getScore() {
        return score;
    }
}
