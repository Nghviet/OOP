package application;

import javafx.scene.paint.Color;

public final class Config {
    public static final String NAME = "Roon";

    public static final int TILE_SIZE = 64;
    public static final int COUNT_HORIZONTAL = 20;
    public static final int COUNT_VERTICAL = 12;

    public static final int UI_HORIZONTAL = 250;

    public static final int GAME_WIDTH = TILE_SIZE * COUNT_HORIZONTAL;
    public static final int GAME_HEIGHT = TILE_SIZE * COUNT_VERTICAL;

    public static final int SCREEN_WIDTH = GAME_WIDTH + UI_HORIZONTAL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * COUNT_VERTICAL;

    public static Color[] colorTiles = new Color[] {Color.GREEN, Color.BLACK};
    public static double timeDivide = 1e8;

    //UI CONTROL
    public static volatile int UI_CUR = 1;
    public static final int UI_START = 1;
    public static final int UI_STAGE_CHOOSING = 2;
    public static final int UI_PLAYING = 3;
    public static final int UI_PAUSE = 4;
    public static final int UI_GAME_COMPLETE = 5;



    public static final int UI_BASE = 0;
}
