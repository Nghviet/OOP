package application.core.tile;

import application.utility.Vector2;

public class Path extends MapTile {
    public Path(Vector2 pos) {
        super(pos);
        setBuildable(false);
    }
}
