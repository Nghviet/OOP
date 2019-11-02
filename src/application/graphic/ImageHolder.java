package application.graphic;

import javafx.application.Platform;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageHolder {

    public static ImageHolder instance;

    public Image[] buttons;
    public Image[] backgrounds;
    public Image[] numbers;
    public Image[] textiles;

    public Image[] normalEnemy;
    public Image[] tankerEnemy;
    public Image[] assassinEnemy;
    public Image[] bossEnemy;

    public Image[] stages;
    public Image[] towers;

    static {
        try {
            instance = new ImageHolder();
            System.out.println("Complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ImageHolder() throws IOException {

        File fileList = new File("src/assets/fileList.txt").getAbsoluteFile();
        Scanner scanner = new Scanner(fileList);

        int button = scanner.nextInt();
        buttons = new Image[button];
        for(int i=0;i<button;i++) {
            String path = scanner.next();
            buttons[i] = new Image(path);
        }

        int background = scanner.nextInt();
        backgrounds = new Image[background];
        for(int i=0;i<background;i++) {
            String path = scanner.next();
            backgrounds[i] = new Image(path);
        }

        int number = scanner.nextInt();
        numbers = new Image[number];
        for(int i=0;i<number;i++) {
            String path = scanner.next();
            numbers[i] = new Image(path);
        }

        int textile = scanner.nextInt();
        textiles = new Image[textile];
        for(int i=0;i<textile;i++) {
            String path = scanner.next();
            textiles[i] = new Image(path);
        }

        int enemySprite = scanner.nextInt();
        normalEnemy = new Image[4];
        for(int i=0;i<4;i++) {
            String path = scanner.next();
            normalEnemy[i] = new Image(path);
        }

        tankerEnemy = new Image[4];
        for(int i=0;i<4;i++) {
            String path = scanner.next();
            tankerEnemy[i] = new Image(path);
        }

        assassinEnemy = new Image[4];
        for(int i=0;i<4;i++) {
            String path = scanner.next();
            assassinEnemy[i] = new Image(path);
        }

        bossEnemy = new Image[4];
        for(int i=0;i<4;i++) {
            String path = scanner.next();
            bossEnemy[i] = new Image(path);
        }

        int stage = scanner.nextInt();
        stages = new Image[stage];
        for(int i=0;i<stage;i++) {
            String path = scanner.next();
            stages[i] = new Image(path);
        }

        int tower = scanner.nextInt();
        towers = new Image[tower];
        for(int i=0;i<tower;i++) {
            String path = scanner.next();
            try {towers[i] = new Image(path);}
            catch (IllegalArgumentException t){
                System.out.println("ERR");
            }
        }

    }
}
