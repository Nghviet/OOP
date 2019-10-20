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
            System.out.println("Finished");
            return;
        }

        if(curWave == null) {
            System.out.println("Incomming!!!");
            System.out.println(curWaveIndex);
            curWave = waves.get(curWaveIndex);
            System.out.println(curWave);
        }
    }

    public void update(){
        if(curWave != null) {
            if(curWave.isFinished()) {
                System.out.println(" Finished "+curWaveIndex);
                curWaveIndex++;
                curWave = null;
            }
            else curWave.update();
        }
    }


}
