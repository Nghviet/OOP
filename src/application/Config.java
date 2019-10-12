package application;

import javafx.scene.paint.Color;

public final class Config {
    public static final String NAME = "Roon";

    public static final int TILE_SIZE = 32;
    public static final int COUNT_HORIZONTAL = 20;
    public static final int COUNT_VERTICAL = 12;

    public static final int SCREEN_WIDTH = TILE_SIZE * COUNT_HORIZONTAL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * COUNT_VERTICAL;

    public static final Color[] colorTiles = new Color[] {Color.GREEN, Color.BLACK};

}
