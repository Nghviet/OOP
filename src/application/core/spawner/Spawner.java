package application.core.spawner;
import application.core.GameField;

import java.util.List;

public class Spawner {

    private GameField gameField;
    private List<Wave> waves;
    private Wave curWave;
    private int curWaveIndex;


    public Spawner(GameField gameField, List<Wave> waves) {
        this.gameField = gameField;
        this.waves = waves;
        curWave = null;
        curWaveIndex = 0;
    }

    public void nextWave() {
        if(curWaveIndex >= waves.size()) {
            return;
        }

        if(curWave == null) {
            System.out.println(curWaveIndex);
            curWave = waves.get(curWaveIndex);
            System.out.println(curWave);
        }
    }

    public void update(){
        if(curWave != null) {
            if(curWave.isFinished()) {
                curWaveIndex++;
                curWave = null;
                System.out.println("Next:" + curWaveIndex);
            }
            else curWave.update();
        }
    }

    public boolean gameComplete() {
        return (curWaveIndex == waves.size()) && (curWave == null);
    }

    public void resetTimer() {
        if(curWave!=null) curWave.resetTimer();
    }


}
