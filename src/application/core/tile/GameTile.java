package application.core.tile;

import application.core.GameEntity;
import javafx.scene.image.Image;

public interface GameTile  extends GameEntity {
    public boolean buildable();

    public void build();
}
