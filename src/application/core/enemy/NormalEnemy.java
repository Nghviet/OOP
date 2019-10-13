package application.core.enemy;

import application.core.player.Player;
import application.utility.Vector2;

public class NormalEnemy extends AbstractEnemy {
    public NormalEnemy(Player player) {
        super(100, 1,1, 3, player);
    }

    public NormalEnemy() {
        super(100, 1,1, 3, null);
    }
}
