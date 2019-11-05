package application.core.enemy;

import application.core.player.Player;

public class BossEnemy extends  AbstractEnemy {
    public BossEnemy() {
        super(1000000,  3, 10, 1, null);
    }

    public BossEnemy(Player player) {
        super(100000,  3, 10, 1, player);

    }
}
