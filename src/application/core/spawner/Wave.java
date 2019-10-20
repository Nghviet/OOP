package application.core.spawner;

import application.Config;
import application.core.GameField;
import application.core.enemy.*;

import java.util.ArrayList;
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

    public Wave(Wave wave) {
        this.gameField = wave.gameField;
        this.enemy = new ArrayList<>(wave.enemy);
        this.delay = wave.delay;
        this.no = wave.no;
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
        if(curIndex >= enemy.size())
        {
            if(gameField.getEnemies().isEmpty()) finished = true;
            return;
        }

        double cur = System.nanoTime()/Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        wait -= deltaTime;
        if(wait <=0 ) {
            Enemy newEnemy;
            switch(enemy.get(curIndex)) {
                default: newEnemy = new NormalEnemy();break;
                case 1: newEnemy = new TankerEnemy();break;
                case 2: newEnemy = new AssassinEnemy();break;
            }
            gameField.doSpawn(newEnemy);
            curIndex++;
            wait = delay;
        }
    }

    public void resetTimer() {
        lastCall = System.nanoTime()/Config.timeDivide;
    }
}
