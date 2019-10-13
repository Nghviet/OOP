package application.core.enemy;

import application.core.player.Player;

public class TankerEnemy extends AbstractEnemy {
    public TankerEnemy(Player player) {
        super(1000,  4, 5, 2, player);
    }

    public TankerEnemy() {
        super(1000,  4, 5, 2, null);
    }
}
