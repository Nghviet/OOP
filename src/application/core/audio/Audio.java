package application.core.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Audio {
    private final List<Media> mediaList;
    private final List<MediaPlayer> mediaPlayers;

    public static Audio instance;

    static {
        try {
            instance = new Audio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Audio() throws FileNotFoundException {
        mediaList = new ArrayList<>();
        mediaPlayers = new ArrayList<>();

        File list = new File("audio/audioList");
        Scanner scanner = new Scanner(list);
    }

    public void backgroundPlay() {

    }


}
