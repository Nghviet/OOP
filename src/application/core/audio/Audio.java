package application.core.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.net.*;

public class Audio {
    private final List<Media> background;
    private final List<MediaPlayer> backgroundPlayers;

    private MediaPlayer curBackgrond = null;

    public static Audio instance;

    private boolean muted = false;
    private boolean backgroundMuted = false;

    private final List<Media> soundEffect;
    private final List<MediaPlayer> effectPlayer;

    static {
        try {
            instance = new Audio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Audio() throws FileNotFoundException {
        background = new ArrayList<>();
        backgroundPlayers = new ArrayList<>();

        File list = new File("src/audio/audioList").getAbsoluteFile();
        Scanner scanner = new Scanner(list);

        int n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            String path = scanner.next();
            Media tmp = new Media(new File(path).toURI().toString());
            MediaPlayer player = new MediaPlayer(tmp);

            background.add(tmp);
            backgroundPlayers.add(player);
        }

        soundEffect = new ArrayList<>();
        effectPlayer = new ArrayList<>();

        n = scanner.nextInt();
        for(int i=0;i<n;i++) {
            String path = scanner.next();
            Media tmp = new Media(new File(path).toURI().toString());
            MediaPlayer player = new MediaPlayer(tmp);

            soundEffect.add(tmp);
            effectPlayer.add(player);
        }
    }

    public void backgroundPlay() {
        if(curBackgrond == null) {
            int n = randomIntGenerator(backgroundPlayers.size());
            curBackgrond = backgroundPlayers.get(n);
            curBackgrond.seek(Duration.ZERO);
            curBackgrond.play();
        }

        if(curBackgrond.getCurrentTime().greaterThanOrEqualTo(curBackgrond.getTotalDuration())) {
            int n = randomIntGenerator(backgroundPlayers.size());
            curBackgrond = backgroundPlayers.get(n);
            curBackgrond.seek(Duration.ZERO);
            curBackgrond.play();
        }

        curBackgrond.setMute(backgroundMuted);
    }

    private int randomIntGenerator(int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound);
    }

    public void muteBackground() {
        backgroundMuted = true;
    }

    public void unmuteBackground() {
        backgroundMuted = false;
    }

    public void muteSFX() {
        muted = true;
    }

    public void unmuteSFX() {
        muted = false;
    }

    public boolean isMuted() {
        return muted;
    }

    public boolean isBackgroundMuted() {
        return backgroundMuted;
    }

    public void onClick() {
        MediaPlayer player = effectPlayer.get(0);
        resetTimer(player);
        player.play();
        player.setVolume(250);
        player.setMute(muted);
    }
    public void upgrade() {
        MediaPlayer player = effectPlayer.get(8);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void sell() {
        MediaPlayer player = effectPlayer.get(7);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void lunch() {
        MediaPlayer player = effectPlayer.get(3);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void explode() {
        MediaPlayer player = effectPlayer.get(4);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void machine() {
        MediaPlayer player = effectPlayer.get(1);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void normal() {
        MediaPlayer player = effectPlayer.get(2);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }
    public void flak() {
        MediaPlayer player = effectPlayer.get(5);
        resetTimer(player);
        player.play();
        player.setMute(muted);
    }

    private void resetTimer(MediaPlayer player) {
        if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) player.seek(Duration.ZERO);
    }
}
