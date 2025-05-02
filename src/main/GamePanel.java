package main;

import backgrounds.BackgroundManager;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import worldGenerator.WorldGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable{
    BufferedImage screen;
    Graphics2D g2;
    static int originalTileSize = 16;

    public static int crystaler = 1;
    static int tileSizeModifier = 3 * crystaler;
    public int tileSize = originalTileSize * tileSizeModifier;

    public int xAspectRatio = 16;
    public int yAspectRatio = 9;

    public int dimensionModifier = (int) ((tileSize * 1.4) / crystaler);

    public int screenWidth = xAspectRatio * dimensionModifier;
    public int screenHeight = yAspectRatio * dimensionModifier;

    public int screenWidth2 = xAspectRatio * (dimensionModifier * crystaler);
    public int screenHeight2 = yAspectRatio * (dimensionModifier * crystaler);

    public int updateModifier;

    int FPS = 60;
    int fps = 0;
    long lastTime;
    long lastTime2;
    double deltaTime;
    int frames = 0;

    Thread gameThread;

    public int maxMap = 1;
    public int existingColumnsInWorld = 0;
    public int maxWorldCol = 100;
    public int maxWorldRow = 100;

    public int currentMap = 0;

    public int titleState = 0;
    public int playState = 1;
    public int pauseState = 2;
    public int optionState = 3;
    public int deadState = 4;

    public int gameState;

    public UI ui = new UI(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Entity player = new Player(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public BackgroundManager bManager = new BackgroundManager(this);
    public WorldGenerator wGenerator = new WorldGenerator(this);
    public TileManager tileM = new TileManager(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.setBackground(Color.black);
    }

    public void setupGame(){
        screen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB);
        g2 = screen.createGraphics();
        lastTime = System.nanoTime();
        lastTime2 = System.nanoTime();
        gameState = titleState;
        loadWorldData();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null){

            double drawInterval = 1000000000 / FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;

            while (gameThread != null){
                update();
                draw();
                drawToScreen();

                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1000000;

                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }

                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void loadWorldData(){
        File tile_data = new File("res/data/platformer/world/world_data.txt");
        try {
            Scanner reader = new Scanner(tile_data);
            existingColumnsInWorld = Integer.parseInt(reader.nextLine().replace("existing_world_columns_in_world_map: ", ""));
            maxWorldCol = existingColumnsInWorld;
            tileM.set();
            wGenerator.set();
        } catch (IOException e){e.printStackTrace();}
    }

    public void update(){
        frames ++;

        long currentTime = System.nanoTime();

        deltaTime = (currentTime - lastTime2) / 1e9;
        lastTime2 = currentTime;

        if (currentTime - lastTime > 1000000000){
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }
        updateModifier = (int)(deltaTime * 100);
        if (gameState == playState) {
            player.update();
        }
    }

    public void draw(){
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth2, screenHeight2);
        bManager.draw(g2);
        if (gameState == playState) {
            tileM.draw(g2);
            player.draw(g2);
        }
        ui.draw(g2);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(45f));
        g2.drawString("FPS: " + fps + "  DT:" + deltaTime, 10, 46);
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(screen, 0, 0, screenWidth, screenHeight, null);
        g.dispose();
    }
    public int accelerate(int speed){
        return (int)((speed));
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);

        g2.dispose();

        return scaledImage;

    }

    public BufferedImage loadAsset(String path, int width, int height){
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
            image = scaleImage(image, width, height);

        } catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }
}
