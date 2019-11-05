package application.core.enemy;

import application.core.player.Player;

public class NormalEnemy extends AbstractEnemy {
    public NormalEnemy(Player player) {
        super(10000, 1,1, 3, player);
    }

    public NormalEnemy() {
        super(1000, 1,1, 3, null);
    }
}
