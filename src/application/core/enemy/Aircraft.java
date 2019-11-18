package application.core.enemy;

import application.Config;
import application.core.player.Player;
import application.utility.Vector2;

import java.util.Random;

public class Aircraft extends AbstractAircraft {
    public Aircraft(Player player, Vector2 pos, Vector2 target) {
        super(10, 1, 1, 10, player);
    }

    public Aircraft() {
        super(10, 1, 1, 10, null);

    }

    @Override
    public int getDir() {
        return 0;
    }
}
