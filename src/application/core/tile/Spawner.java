package application.core.tile;
import application.Config;
import application.core.GameField;
import application.core.enemy.*;
import application.core.player.Player;
import application.utility.Vector2;

import java.util.List;

public class Spawner implements GameTile {

    private Vector2 pos;

    private List<Integer> enemy;
    private List<Integer> delay;

    private double lastCall;
    private double wait;
    private int curIndex = 0;

    private GameField gameField;

    public Spawner(List<Integer> enemy, List<Integer> delay, GameField gameField) {
        this.pos = Waypoints.instance.getIndex(0);
        this.enemy = enemy;
        this.delay = delay;
        this.lastCall = System.nanoTime()/ Config.timeDivide;
        this.wait = 0;
        this.gameField = gameField;
    }

    public void update() {
        double cur = System.nanoTime()/Config.timeDivide;
        double deltaTime = cur - lastCall;
        lastCall = cur;

        if(curIndex >= delay.size()) return;

        wait+=deltaTime;
        if(wait >= delay.get(curIndex) * 5){
            Enemy newEnemy;
            switch(enemy.get(curIndex)) {
                default: newEnemy = new NormalEnemy();break;
                case 1: newEnemy = new TankerEnemy();break;
                case 2: newEnemy = new AssassinEnemy();break;
            }
            gameField.doSpawn(newEnemy);
            curIndex++;
            wait = 0;
        }
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }
}
