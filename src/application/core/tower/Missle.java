package application.core.tower;

import application.Config;
import application.core.GameEntity;
import application.core.GameField;
import application.utility.Vector2;

public class Missle implements GameEntity {

    Vector2 pos;
    Vector2 target;

    GameField gameField;

    int timerLunch;
    int timerETA;

    double lastCall;

    Missle(Vector2 pos,Vector2 target,GameField gameField) {
        this.pos = pos;
        this.target = target;
        this.gameField = gameField;
    }

    public void move() {
        double cur = System.nanoTime()/ Config.timeDivide;
        double deltaTime = cur - lastCall;

        if(timerLunch > 0) {

        }
    }

    public int getEta() {
        return timerETA;
    }

    public int getLunch() {
        return timerLunch;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }
}
