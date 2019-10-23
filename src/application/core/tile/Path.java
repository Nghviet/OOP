package application.core.tile;

import application.core.GameField;
import application.utility.Vector2;

public class Path implements GameTile {
    private Vector2 pos;
    public Path(Vector2 pos) {
        this.pos = pos;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }
}
