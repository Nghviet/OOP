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

    public String toString() {
        String data = money + " " + health + " " + score;
        return data;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
