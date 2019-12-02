package application.core.enemy;

import application.core.player.Player;

public class NormalEnemy extends AbstractEnemy {
    public NormalEnemy(Player player) {
        super(10000, 1,1, 8, player,0);
    }

    public NormalEnemy() {
        super(1000, 1,1, 3, null,0);
    }
}
