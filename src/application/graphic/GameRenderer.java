package application.graphic;

import application.Config;
import application.Controller;
import application.core.enemy.*;
import application.core.GameField;
import application.core.tile.GameTile;
import application.core.tile.MapTile;
import application.core.tile.Path;
import application.core.tower.*;
import application.utility.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRenderer {
    private GraphicsContext graphicsContext;
    private GameField gameField;
    private Controller gameController;

    public GameRenderer(GraphicsContext graphicsContext, Controller gameController) {
        this.graphicsContext = graphicsContext;
        this.gameField = null;
        this.gameController = gameController;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public void render() {
        graphicsContext.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        switch (Config.UI_CUR) {
            case Config.UI_START: {
                startRender();
                break;
            }
            case Config.UI_STAGE_CHOOSING: {
                stageRender();
                break;
            }
            case Config.UI_PLAYING: {
                gameRender();
                if (gameField.isComplete()) {
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.fillRect(Config.SCREEN_WIDTH / 2 - 100, Config.SCREEN_HEIGHT / 2 + 20, 200, 100);
                }
                break;
            }
            case Config.UI_GAME_COMPLETE: {
                gameRender();
                graphicsContext.drawImage(ImageHolder.instance.buttons[2], Config.SCREEN_WIDTH / 2 - 60, Config.SCREEN_HEIGHT / 2 - 17);
                break;
            }
            case Config.UI_PAUSE: {
                pauseRender();
                break;
            }
            case Config.UI_HIGHSCORE: {
                highscoreRender();
                break;
            }
        }
    }

    private void startRender() {
        graphicsContext.drawImage(ImageHolder.instance.backgrounds[0], Config.SCREEN_WIDTH / 2 - 300, Config.SCREEN_HEIGHT / 2 - 180);
        graphicsContext.drawImage(ImageHolder.instance.buttons[0], Config.SCREEN_WIDTH / 2 - 133, Config.SCREEN_HEIGHT / 2 - 75, 266, 50);
        graphicsContext.drawImage(ImageHolder.instance.buttons[6],Config.SCREEN_WIDTH / 2 - 133,Config.SCREEN_HEIGHT / 2 - 10, 266, 50);
    }

    private void stageRender() {

        graphicsContext.setFill(Color.BROWN);
        graphicsContext.fillRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        graphicsContext.setFill(Color.SANDYBROWN);
        graphicsContext.fillRect(Config.SCREEN_WIDTH / 2 - 250, Config.SCREEN_HEIGHT / 2 - 145, 220, 130);
        graphicsContext.fillRect(Config.SCREEN_WIDTH / 2 - 250, Config.SCREEN_HEIGHT / 2 + 15, 220, 130);
        graphicsContext.fillRect(Config.SCREEN_WIDTH / 2 + 30, Config.SCREEN_HEIGHT / 2 - 145, 220, 130);
        graphicsContext.fillRect(Config.SCREEN_WIDTH / 2 + 30, Config.SCREEN_HEIGHT / 2 + 15, 220, 130);


        graphicsContext.drawImage(ImageHolder.instance.stages[0], Config.SCREEN_WIDTH / 2 - 240, Config.SCREEN_HEIGHT / 2 - 140, 200, 120);
        graphicsContext.drawImage(ImageHolder.instance.stages[1], Config.SCREEN_WIDTH / 2 + 40, Config.SCREEN_HEIGHT / 2 - 140, 200, 120);
        graphicsContext.drawImage(ImageHolder.instance.stages[2], Config.SCREEN_WIDTH / 2 - 240, Config.SCREEN_HEIGHT / 2 + 20, 200, 120);
        graphicsContext.drawImage(ImageHolder.instance.stages[3], Config.SCREEN_WIDTH / 2 + 40, Config.SCREEN_HEIGHT / 2 + 20, 200, 120);
    }

    private void gameRender() {
        GameTile[][] map = gameField.getMap();

        for (int x = 0; x < Config.COUNT_HORIZONTAL; x++)
            for (int y = 0; y < Config.COUNT_VERTICAL; y++) {
                GameTile tile = map[x][y];
                Vector2 p = tile.getPosition();

                if (tile instanceof Path) {
                    boolean[][] near = new boolean[3][3];

                    if (x - 1 < 0) {
                        for (int i = 0; i < 3; i++) near[0][i] = true;
                    }
                    if (x + 1 >= Config.COUNT_HORIZONTAL) {
                        for (int i = 0; i < 3; i++) near[2][i] = true;
                    }

                    if (y - 1 < 0) {
                        for (int i = 0; i < 3; i++) near[i][0] = true;
                    }
                    if (y + 1 >= Config.COUNT_VERTICAL) {
                        for (int i = 0; i < 3; i++) near[i][2] = true;
                    }

                    if (x - 1 >= 0) {
                        if (y - 1 >= 0 && map[x - 1][y - 1] instanceof MapTile) near[0][0] = true;
                        if (map[x - 1][y] instanceof MapTile) near[0][1] = true;
                        if (y + 1 < Config.COUNT_VERTICAL && map[x - 1][y + 1] instanceof MapTile) near[0][2] = true;
                    }
                    if (y - 1 >= 0 && map[x][y - 1] instanceof MapTile) near[1][0] = true;
                    if (y + 1 < Config.COUNT_VERTICAL && map[x][y + 1] instanceof MapTile) near[1][2] = true;
                    if (x + 1 < Config.COUNT_HORIZONTAL) {
                        if (y - 1 >= 0 && map[x + 1][y - 1] instanceof MapTile) near[2][0] = true;
                        if (map[x + 1][y] instanceof MapTile) near[2][1] = true;
                        if (y + 1 < Config.COUNT_VERTICAL && map[x + 1][y + 1] instanceof MapTile) near[2][2] = true;
                    }

                    if (near[0][0]) {
                        if (near[0][1]) {
                            if (near[1][0]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[9],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[12],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        } else {
                            if (near[1][0]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[10],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[8],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        }
                    } else {
                        if (near[0][1]) graphicsContext.drawImage(ImageHolder.instance.textiles[12],
                                p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        else graphicsContext.drawImage(ImageHolder.instance.textiles[10],
                                p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                    }

                    if (near[0][2]) {
                        if (near[1][2]) {
                            if (near[0][1]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[15],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[16],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        } else {
                            if (near[0][1]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[12],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[2],
                                        p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        }
                    } else {
                        if (near[0][1]) graphicsContext.drawImage(ImageHolder.instance.textiles[12],
                                p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        else graphicsContext.drawImage(ImageHolder.instance.textiles[16],
                                p.getX() - Config.TILE_SIZE / 2, p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                    }

                    if (near[2][0]) {
                        if (near[1][0]) {
                            if (near[2][1]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[11],
                                        p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[10],
                                        p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        } else {
                            if (near[2][1]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[14],
                                        p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[6],
                                        p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        }
                    } else {
                        if (near[2][1]) {
                            graphicsContext.drawImage(ImageHolder.instance.textiles[14],
                                    p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        } else {
                            graphicsContext.drawImage(ImageHolder.instance.textiles[10],
                                    p.getX(), p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        }
                    }

                    if (near[2][2]) {
                        if (near[2][1]) {
                            if (near[1][2]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[17],
                                        p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[14],
                                        p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        } else {
                            if (near[1][2]) {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[16],
                                        p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            } else {
                                graphicsContext.drawImage(ImageHolder.instance.textiles[0],
                                        p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                            }
                        }
                    } else {
                        if (near[1][2]) {
                            graphicsContext.drawImage(ImageHolder.instance.textiles[16],
                                    p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        } else {
                            graphicsContext.drawImage(ImageHolder.instance.textiles[14],
                                    p.getX(), p.getY(), Config.TILE_SIZE / 2, Config.TILE_SIZE / 2);
                        }
                    }

                } else {
                    if (tile instanceof MapTile) {
                        graphicsContext.drawImage(ImageHolder.instance.textiles[4],
                                p.getX() - Config.TILE_SIZE / 2, p.getY() - Config.TILE_SIZE / 2, Config.TILE_SIZE, Config.TILE_SIZE);
                    }
                }
            }

        List<Tower> towers = gameField.getTowers();
        if (towers != null) {
            for (Tower tower : towers) {
                Vector2 pos = tower.getPosition();
                int lv = tower.getLevel();
                graphicsContext.drawImage(ImageHolder.instance.towers[0],
                        pos.getX() - Config.TILE_SIZE / 2, pos.getY() - Config.TILE_SIZE / 2,
                        Config.TILE_SIZE, Config.TILE_SIZE);

                double rotation = tower.getRotation();
                graphicsContext.save();
                graphicsContext.rotate(rotation);

                double dist = Math.sqrt((pos.getX()) * (pos.getX()) + pos.getY() * pos.getY());
                double rad = Math.toRadians(rotation);
                double base = Math.acos(pos.getX() / dist);

                if (tower instanceof NormalTower) {
                    Image r = null;
                    if (lv == 1) r = ImageHolder.instance.towers[1];
                    if (lv == 2) r = ImageHolder.instance.towers[8];
                    if (lv == 3) r = ImageHolder.instance.towers[9];
                    graphicsContext.drawImage(r, dist * Math.cos(-rad + base) + 10 - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                            Config.TILE_SIZE, Config.TILE_SIZE);
                }

                if (tower instanceof RapidTower) {
                    Image r = null;
                    if (lv == 1) r = ImageHolder.instance.towers[2];
                    if (lv == 2) r = ImageHolder.instance.towers[10];
                    if (lv == 3) r = ImageHolder.instance.towers[11];
                    graphicsContext.drawImage(r, dist * Math.cos(-rad + base) + 10 - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                            Config.TILE_SIZE, Config.TILE_SIZE);
                }

                if (tower instanceof RangerTower) {
                    if (lv == 1) {
                        graphicsContext.drawImage(ImageHolder.instance.towers[4],
                                dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                                Config.TILE_SIZE, Config.TILE_SIZE);
                        if (tower.isReloaded()) {
                            graphicsContext.drawImage(ImageHolder.instance.towers[13],
                                    dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                                    Config.TILE_SIZE, Config.TILE_SIZE);
                        }
                    }

                    if (lv == 2) {
                        graphicsContext.drawImage(ImageHolder.instance.towers[4],
                                dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                                Config.TILE_SIZE, Config.TILE_SIZE);
                        if (tower.isReloaded()) {
                            graphicsContext.drawImage(ImageHolder.instance.towers[7],
                                    dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                                    Config.TILE_SIZE, Config.TILE_SIZE);
                        }
                    }

                    if (lv == 3) {
                        graphicsContext.drawImage(ImageHolder.instance.towers[12],
                                dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                                Config.TILE_SIZE, Config.TILE_SIZE);

                        if (tower.isReloaded()) {
                            graphicsContext.drawImage(ImageHolder.instance.towers[7],
                                    dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2 - 20,
                                    Config.TILE_SIZE, Config.TILE_SIZE);
                            graphicsContext.drawImage(ImageHolder.instance.towers[7],
                                    dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2 + 20,
                                    Config.TILE_SIZE, Config.TILE_SIZE);
                        }
                    }

                }


                graphicsContext.restore();

            }
        }

        graphicsContext.setFill(Color.RED);
        List<Enemy> enemies = gameField.getEnemies();
        for (Enemy enemy : enemies) {
            Vector2 pos = enemy.getPosition();
            int dir = enemy.getDir();
            if (enemy instanceof NormalEnemy) {
                graphicsContext.drawImage(ImageHolder.instance.normalEnemy[dir], pos.getX() - Config.TILE_SIZE / 2, pos.getY() - Config.TILE_SIZE / 2,
                        Config.TILE_SIZE, Config.TILE_SIZE);
            }
            if (enemy instanceof AssassinEnemy) {
                graphicsContext.drawImage(ImageHolder.instance.assassinEnemy[dir], pos.getX() - Config.TILE_SIZE / 2, pos.getY() - Config.TILE_SIZE / 2,
                        Config.TILE_SIZE, Config.TILE_SIZE);
            }
            if (enemy instanceof TankerEnemy) {
                graphicsContext.drawImage(ImageHolder.instance.tankerEnemy[dir], pos.getX() - Config.TILE_SIZE / 2, pos.getY() - Config.TILE_SIZE / 2,
                        Config.TILE_SIZE, Config.TILE_SIZE);
            }
            if (enemy instanceof BossEnemy) {
                graphicsContext.drawImage(ImageHolder.instance.bossEnemy[dir],
                        pos.getX() - Config.TILE_SIZE / 2 - 10, pos.getY() - Config.TILE_SIZE / 2 -10,
                        Config.TILE_SIZE+20, Config.TILE_SIZE+20);
            }

            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(pos.getX() - Config.TILE_SIZE/2,pos.getY() - Config.TILE_SIZE/2,
                    Config.TILE_SIZE * enemy.getPercentage() / 100,3);

        }

        List<Enemy> aircrafts = gameField.getAircraft();
        for (Enemy enemy : aircrafts) {
            Vector2 pos = enemy.getPos();
            double rotation = ((Aircraft) enemy).getRotation();
            double dist = Math.sqrt((pos.getX()) * (pos.getX()) + pos.getY() * pos.getY());
            double rad = Math.toRadians(rotation);
            double base = Math.acos(pos.getX() / dist);
            graphicsContext.save();
            graphicsContext.rotate(rotation);
            graphicsContext.drawImage(ImageHolder.instance.airEnemy[0], dist * Math.cos(-rad + base) + 10 - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                    Config.TILE_SIZE, Config.TILE_SIZE);
            graphicsContext.restore();

            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(pos.getX() - Config.TILE_SIZE/2,pos.getY() - Config.TILE_SIZE/2,
                    Config.TILE_SIZE * enemy.getPercentage() / 100,3);
        }

        List<Bullet> bullets = gameField.getBullets();
        for (Bullet bullet : bullets)
        {
            Tower tower = bullet.getTower();
            Vector2 pos = bullet.getPosition();
            if(!bullet.isDestroyed()) {

                double x = pos.getX();
                double y = pos.getY();
                double rotation = bullet.getRotation();
                double dist = Math.sqrt((pos.getX()) * (pos.getX()) + pos.getY() * pos.getY());
                double rad = Math.toRadians(rotation);
                double base = Math.acos(pos.getX() / dist);

                graphicsContext.save();
                graphicsContext.rotate(rotation);

                double rX = dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2;
                double rY = dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2;

                int level = tower.getLevel();

                if (tower instanceof NormalTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[5], rX, rY, Config.TILE_SIZE, Config.TILE_SIZE);
                }
                if (tower instanceof RapidTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[6], rX, rY, Config.TILE_SIZE, Config.TILE_SIZE);
                }
                if (tower instanceof RangerTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[7], rX, rY, Config.TILE_SIZE, Config.TILE_SIZE);
                }

                graphicsContext.restore();
            }
            else if(!bullet.isCompleted()) {
                graphicsContext.setFill(Color.AZURE);
                graphicsContext.fillRect(pos.getX()-Config.TILE_SIZE/4,pos.getY() - Config.TILE_SIZE/4,Config.TILE_SIZE/2,Config.TILE_SIZE/2);
            }
        }

        playingMouseRender();

        //*UI
        graphicsContext.drawImage(ImageHolder.instance.backgrounds[1], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL, 0, Config.UI_HORIZONTAL, Config.SCREEN_HEIGHT);

        graphicsContext.drawImage(ImageHolder.instance.backgrounds[3], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 5, 150);
        graphicsContext.drawImage(ImageHolder.instance.backgrounds[2], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 5, 200);
        graphicsContext.drawImage(ImageHolder.instance.backgrounds[4], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 35, 150);
        graphicsContext.drawImage(ImageHolder.instance.backgrounds[4], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 35, 200);

        List<Integer> health = convert(gameField.getPlayerHealth());
        int curMoney = gameController.gameField.getPlayerMoney();
        for (int i = 0; i < health.size(); i++) {
            graphicsContext.drawImage(ImageHolder.instance.numbers[health.get(i)], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 69 + 32 * i, 150);

        }

        List<Integer> money = convert(gameField.getPlayerMoney());
        for (int i = 0; i < money.size(); i++) {
            graphicsContext.drawImage(ImageHolder.instance.numbers[money.get(i)], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 69 + 32 * i, 200);
        }

        graphicsContext.drawImage(ImageHolder.instance.buttons[3], Config.SCREEN_WIDTH - Config.UI_HORIZONTAL + 5, 300);








        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 20 - 2 * Config.TILE_SIZE, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[0],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 20 - 2 * Config.TILE_SIZE, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[1],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - 2 * Config.TILE_SIZE, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        if(curMoney < NormalTower.instance.getPrice()) graphicsContext.drawImage(ImageHolder.instance.grayout,
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 20 - 2 * Config.TILE_SIZE, 10, Config.TILE_SIZE, Config.TILE_SIZE);

        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + 10, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[0],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + 10, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[2],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + 20, 10, Config.TILE_SIZE, Config.TILE_SIZE);
        if(curMoney < RapidTower.instance.getPrice()) graphicsContext.drawImage(ImageHolder.instance.grayout,
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 + 10, 10, Config.TILE_SIZE, Config.TILE_SIZE);

        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillRect(Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - Config.TILE_SIZE, 90, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[0],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - Config.TILE_SIZE, 90, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[4],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - Config.TILE_SIZE, 90, Config.TILE_SIZE, Config.TILE_SIZE);
        graphicsContext.drawImage(ImageHolder.instance.towers[13],
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 5 - Config.TILE_SIZE, 90, Config.TILE_SIZE, Config.TILE_SIZE);
        if(curMoney < RangerTower.instance.getPrice()) graphicsContext.drawImage(ImageHolder.instance.grayout,
                Config.GAME_WIDTH + Config.UI_HORIZONTAL / 2 - 10 - Config.TILE_SIZE, 90, Config.TILE_SIZE, Config.TILE_SIZE);


        Tower showTower = gameController.showTower;
        if (showTower != null) {
            graphicsContext.drawImage(ImageHolder.instance.buttons[4], Config.GAME_WIDTH + 10, Config.SCREEN_HEIGHT - 150);
            graphicsContext.drawImage(ImageHolder.instance.buttons[5], Config.SCREEN_WIDTH - 64, Config.SCREEN_HEIGHT - 150);


            List<Integer> damage = convert(showTower.getDamage());
            graphicsContext.drawImage(ImageHolder.instance.backgrounds[6], Config.GAME_WIDTH + 10, Config.GAME_HEIGHT - 350);
            for (int i = 0; i < damage.size(); i++) {
                graphicsContext.drawImage(ImageHolder.instance.numbers[damage.get(i)],
                        Config.GAME_WIDTH + 10 + Config.TILE_SIZE * (i + 1), Config.GAME_HEIGHT - 350);
            }

            List<Integer> level = convert(showTower.getLevel());
            for (int i = 0; i < level.size(); i++)
                graphicsContext.drawImage(ImageHolder.instance.numbers[level.get(i)],
                        Config.GAME_WIDTH + 10 + Config.TILE_SIZE * (i + 1), Config.GAME_HEIGHT - 300);

            List<Integer> price = convert(showTower.getPrice());
            graphicsContext.drawImage(ImageHolder.instance.backgrounds[2], Config.GAME_WIDTH + 10, Config.GAME_HEIGHT - 250);
            for (int i = 0; i < price.size(); i++) {
                graphicsContext.drawImage(ImageHolder.instance.numbers[price.get(i)],
                        Config.GAME_WIDTH + 10 + Config.TILE_SIZE * (i + 1), Config.GAME_HEIGHT - 250);
            }
        }


    }

    private void playingMouseRender() {
        int x = gameController.mX;
        int y = gameController.mY;
        if (gameController.curTower != null) {
            if (0 <= x && x < Config.GAME_WIDTH && 0 <= y && y < Config.GAME_HEIGHT && gameField.isEmpty(x, y)) {
                int mX = x / Config.TILE_SIZE;
                int mY = y / Config.TILE_SIZE;
                graphicsContext.drawImage(ImageHolder.instance.textiles[18],
                        mX * Config.TILE_SIZE, mY * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
                graphicsContext.drawImage(ImageHolder.instance.backgrounds[5],
                        mX * Config.TILE_SIZE + Config.TILE_SIZE / 2 - gameController.curTower.getRange(), mY * Config.TILE_SIZE + Config.TILE_SIZE / 2 - gameController.curTower.getRange(),
                        2 * gameController.curTower.getRange(), 2 * gameController.curTower.getRange());

                graphicsContext.drawImage(ImageHolder.instance.towers[0],
                        mX * Config.TILE_SIZE, mY * Config.TILE_SIZE,
                        Config.TILE_SIZE, Config.TILE_SIZE);

                x = mX * Config.TILE_SIZE + Config.TILE_SIZE / 2;
                y = mY * Config.TILE_SIZE + Config.TILE_SIZE / 2;

                double rotation = gameController.curTower.getRotation();

                graphicsContext.save();
                graphicsContext.rotate(rotation);

                double dist = Math.sqrt(x * x + y * y);
                double rad = Math.toRadians(rotation);
                double base = Math.acos(x / dist);

                graphicsContext.setFill(Color.GRAY);
                graphicsContext.fillRect(dist * Math.cos(-rad + base) - 6, dist * Math.sin(-rad + base) - 6, 20, 12);
                if (gameController.curTower instanceof NormalTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[1],
                            dist * Math.cos(-rad + base) + 10 - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                            Config.TILE_SIZE, Config.TILE_SIZE);
                }

                if (gameController.curTower instanceof RapidTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[2],
                            dist * Math.cos(-rad + base) + 10 - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                            Config.TILE_SIZE, Config.TILE_SIZE);

                }

                if (gameController.curTower instanceof RangerTower) {
                    graphicsContext.drawImage(ImageHolder.instance.towers[3],
                            dist * Math.cos(-rad + base) - Config.TILE_SIZE / 2, dist * Math.sin(-rad + base) - Config.TILE_SIZE / 2,
                            Config.TILE_SIZE, Config.TILE_SIZE);
                }


                graphicsContext.restore();
            }
        }

        if (gameController.showTower != null) {
            double range = gameController.showTower.getRange();
            Vector2 pos = gameController.showTower.getPosition();
            graphicsContext.drawImage(ImageHolder.instance.backgrounds[5], pos.getX() - range, pos.getY() - range, range * 2, range * 2);
        }
    }

    private void pauseRender() {
        gameRender();
        graphicsContext.drawImage(ImageHolder.instance.buttons[1], Config.SCREEN_WIDTH / 2 - 81, Config.SCREEN_HEIGHT / 2 - 20, 162, 40);
        graphicsContext.drawImage(ImageHolder.instance.buttons[2], Config.SCREEN_WIDTH / 2 - 81, Config.SCREEN_HEIGHT / 2 + 30, 162, 40);
    }

    private void highscoreRender() {
        graphicsContext.drawImage(ImageHolder.instance.buttons[7],35,Config.SCREEN_HEIGHT - 70,266,50);


        graphicsContext.setFill(Color.BLACK);
        if(Config.connected)
        for(int i=0;i<10;i++) {
                graphicsContext.fillText(gameController.net.name[i],Config.SCREEN_WIDTH/2 - 450,10 + 40 * i, 450);
                graphicsContext.fillText(String.valueOf(gameController.net.score[i]),Config.SCREEN_WIDTH/2,10+40 * i ,200);
        }
        else {
            graphicsContext.fillText("No internet connection",Config.SCREEN_WIDTH/2,100,200);
        }
    }

    private List<Integer> convert(int n) {
        List<Integer> number = new ArrayList<>();
        while (n != 0) {
            number.add(n % 10);
            n /= 10;
        }
        if (number.isEmpty()) number.add(n);
        Collections.reverse(number);
        return number;
    }
}
