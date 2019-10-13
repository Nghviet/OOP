package application.core.enemy;

import application.core.player.Player;

public class AssassinEnemy extends AbstractEnemy {
    public AssassinEnemy(Player player) {
        super(50, 1, 3, 10, player);
    }

    public AssassinEnemy() {
        super(50, 1, 3, 10, null);
    }
}
