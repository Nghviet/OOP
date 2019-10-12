package application.core.tile;

import application.utility.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapTile implements GameTile {

    private Vector2 pos; // Center
    private boolean buildable;

    private Object object;

    public MapTile(Vector2 pos) {
        this.pos = pos;
        buildable = true;
        object = null;
    }

    @Override
    public boolean buildable() {
        return buildable;
    }

    @Override
    public void build() {

    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
}
