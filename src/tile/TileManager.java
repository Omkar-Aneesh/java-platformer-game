package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[40];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("res/data/platformer/world/worldmap.txt", 0);
    }
    public void set(){
        tile = new Tile[40];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        gp.wGenerator.loadMTN();
    }
    public void getTileImage(){
        int i = 0;

        setup(i, "000", false); i ++;
        setup(i, "001", true); i ++;
        setup(i, "002", true); i ++;
        setup(i, "003", true); i ++;
        setup(i, "004", false); i ++;
        setup(i, "005", true); i ++;
    }
    public void setup(int index, String imagePath, boolean collision){
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read((getClass().getResourceAsStream("/tiles/" + imagePath + ".png")));
            tile[index].image = gp.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String path, int map){
        try {
//            InputStream is = getClass().getResourceAsStream(path);
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            File file = new File(path);

            Scanner reader = new Scanner(file);

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = reader.nextLine();

                while (col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col ++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row ++;
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol ++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow ++;
            }
        }
    }
}
