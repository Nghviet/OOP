package application.core.enemy;

import application.core.player.Player;

public class AssassinEnemy extends AbstractEnemy {
    public AssassinEnemy(Player player) {
        super(5000, 1, 3, 20, player,1);
    }

    public AssassinEnemy() {
        super(500, 1, 3, 10, null,1);
    }
}
