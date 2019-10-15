package application.core.spawner;

import application.Config;
import application.core.GameField;

import java.util.List;

public class Wave {
    private GameField gameField;
    private List<Integer> enemy;
    private int delay;

    private int no;
    private boolean isActive;
    private boolean finished;

    private double lastCall;
    private double wait;
    private int curIndex;


    public Wave(GameField gameField, List<Integer> enemy, int delay, int no) {
        this.gameField = gameField;
        this.enemy = enemy;
        this.delay = delay;
        this.no = no;
        this.isActive = false;
        curIndex = 0;
        finished = false;
    }

    public void start() {
        isActive = true;
        lastCall = System.nanoTime() / Config.timeDivide;
        wait = delay;
    }

    public boolean isStarted() {
        return isActive;
    }

    public boolean isFinished() {
        return finished;
    }

    public void update() {
        if(curIndex >= enemy.size()) finished = true;
        if(finished || !isActive) return;

        double cur = System.nanoTime()/Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        wait -= deltaTime;
        if(wait <=0 ) {
            switch (enemy.get(curIndex)) {

            }
        }

    }
}
