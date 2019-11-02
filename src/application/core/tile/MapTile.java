package application.core.tile;

import application.core.tower.Tower;
import application.utility.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapTile implements GameTile {

    private Vector2 pos; // Center
    private boolean buildable;

    private Tower tower;

    public MapTile(Vector2 pos) {
        this.pos = pos;
        buildable = true;
        tower = null;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public void build(Tower tower) {
        buildable = false;
        this.tower = tower;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }

    public Tower getTower() {
        return tower;
    }
}
