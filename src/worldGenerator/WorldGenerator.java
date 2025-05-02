package worldGenerator;

import main.GamePanel;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class WorldGenerator {
    GamePanel gp;

    public int existingColumnsInWorld = 0;
    public int existingCols = existingColumnsInWorld;
    public int maxColumnsPerCall = 100;
    public int limitCol = 0;

    public int maxMountains = 8;

    public int stoneLevel = 0;
    public int dirtSecondLayer = 0;
    public int dirtLayer = 0;

    public int[][] mapTileNum;

    public WorldGenerator(GamePanel gp){
        this.gp = gp;
        File tile_data = new File("res/data/platformer/world/world_data.txt");
        try {
            Scanner reader = new Scanner(tile_data);
            existingColumnsInWorld = Integer.parseInt(reader.nextLine().replace("existing_world_columns_in_world_map: ", ""));
            limitCol = existingColumnsInWorld + maxColumnsPerCall;
            mapTileNum = new int[existingColumnsInWorld + maxColumnsPerCall][gp.maxWorldRow];
        } catch (IOException e){e.printStackTrace();}
        loadWorldMap();
    }
    public void getExistingColumnsInWorld(){
        File tile_data = new File("res/data/platformer/world/world_data.txt");
        try {
            Scanner reader = new Scanner(tile_data);
            existingColumnsInWorld = Integer.parseInt(reader.nextLine().replace("existing_world_columns_in_world_map: ", ""));
            existingCols = existingColumnsInWorld;
        } catch (IOException e){e.printStackTrace();}
    }
    public void set(){
        File tile_data = new File("res/data/platformer/world/world_data.txt");
        try {
            Scanner reader = new Scanner(tile_data);
            existingColumnsInWorld = Integer.parseInt(reader.nextLine().replace("existing_world_columns_in_world_map: ", ""));
            limitCol = existingColumnsInWorld + maxColumnsPerCall;
            mapTileNum = new int[existingColumnsInWorld + maxColumnsPerCall][gp.maxWorldRow];
        } catch (IOException e){e.printStackTrace();}
        setMTN();

    }
    public void setMTN(){
        int col = 0;
        int row = 0;
        while (row < gp.maxWorldRow){
            mapTileNum[col][row] = gp.tileM.mapTileNum[gp.currentMap][col][row];
            col ++;
            if (col >= existingColumnsInWorld){
                row ++;
                col = 0;
            }
        }
    }
    public void loadMTN(){
        getExistingColumnsInWorld();
        int col = 0;
        int row = 0;
        while (row < gp.maxWorldRow){
            gp.tileM.mapTileNum[gp.currentMap][col][row] = mapTileNum[col][row];
            System.out.print(gp.tileM.mapTileNum[gp.currentMap][col][row]);
            col ++;
            if (col >= existingColumnsInWorld){
                row ++;
                System.out.println();
                col = 0;
            }
        }
    }
    public void loadWorldMap(){
        try {
            File file = new File("res/data/platformer/world/worldMap.txt");

            Scanner reader = new Scanner(file);

            int col = 0;
            int row = 0;

            while (col < existingColumnsInWorld && row < gp.maxWorldRow){

                String line = reader.nextLine();

                while (col < existingColumnsInWorld){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col ++;
                }
                if (col == existingColumnsInWorld){
                    col = 0;
                    row ++;
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void generateLeftWorld(){
        existingColumnsInWorld = 0;
        generateWorld();
        pushExistingDataToList();
    }
    public void pushExistingDataToList(){
        getExistingColumnsInWorld();
        int col = 100;
        int row = 0;
        while (row < gp.maxWorldRow){
            mapTileNum[col][row] = gp.tileM.mapTileNum[gp.currentMap][col - 100][row];
            col ++;
            if (col >= existingColumnsInWorld){
                row ++;
                col = 0;
            }
        }
    }
    public void generateWorld(){
        generateLastLayer();
        generateStoneLayer();
        generateDirtLayer();
        generateTransparentLayer();
        generateMountains();
        generateGrassLayer();
        saveWorldMap();
    }
    public void generateLastLayer(){
        int col = existingColumnsInWorld;
        int row = gp.maxWorldRow - 1;
        while (col < limitCol) {
            mapTileNum[col][row] = 5;
            col++;
        }
        row = gp.maxWorldRow - 2;
        col = existingColumnsInWorld;
        while (col < limitCol){
            int randomTile = new Random().nextInt(3, 6);
            if (randomTile == 4){
                randomTile = 3;
            }
            mapTileNum[col][row] = randomTile;
            col ++;
        }
        col = existingColumnsInWorld;
        row = gp.maxWorldRow - 2;
        while (row < gp.maxWorldRow){

            col ++;
            if (col >= limitCol){
                row ++;
                col = existingColumnsInWorld;
            }
        }

    }
    public void generateStoneLayer(){
        int randomYLevel = new Random().nextInt(gp.maxWorldRow - 12, gp.maxWorldRow - 3);
        stoneLevel = randomYLevel;
        int col = existingColumnsInWorld;
        int row = randomYLevel;

        while (row < gp.maxWorldRow - 2){
            int randomTile = new Random().nextInt(2, 50);
            if (randomTile >= 48){
                randomTile = 2;
            } else {
                randomTile = 3;
            }
            mapTileNum[col][row] = randomTile;
            col ++;
            if (col >= limitCol){
                row ++;
                col = existingColumnsInWorld;
            }
        }
    }
    public void generateDirtLayer(){
        int randomYLevel = new Random().nextInt(gp.maxWorldRow - 13, gp.maxWorldRow - 11);
        dirtSecondLayer = randomYLevel;
        int col = existingColumnsInWorld;
        int row = randomYLevel;
        while (row < stoneLevel){
            mapTileNum[col][row] = 2;
            col ++;
            if (col >= limitCol){
                row ++;
                col = existingColumnsInWorld;
            }
        }
        randomYLevel = new Random().nextInt(gp.maxWorldRow - 15, gp.maxWorldRow - 14);
        dirtLayer = randomYLevel;
        row = randomYLevel;
        int rowToSee = dirtLayer;
        boolean firstRowOver = false;
        while (row < dirtSecondLayer){
            int randomTile = new Random().nextInt(0, 20);
            if (randomTile >= 14){
                randomTile = 4;
            }
            if(randomTile >= 10 && randomTile < 13){
                randomTile = 2;
                if (!firstRowOver) {
                    randomTile = 1;
                } else {
                    if (mapTileNum[col][rowToSee] == 4){
                        randomTile = 1;
                    }
                }
            } else {
                randomTile = 2;
            }
            mapTileNum[col][row] = randomTile;
            col ++;
            if (col >= limitCol){
                row ++;
                if (!firstRowOver){
                    firstRowOver = true;
                }else {
                    rowToSee ++;
                }
                col = existingColumnsInWorld;
            }
        }

    }
    public void generateGrassLayer(){
        int col = existingColumnsInWorld;
        int row = dirtLayer - 1;
        while (row < dirtLayer){
            if (mapTileNum[col][dirtLayer] == 2){
                if (mapTileNum[col][dirtLayer - 2] == 4) {
                    mapTileNum[col][row] = 1;
                } else {
                    mapTileNum[col][row] = 2;
                }
            } else {
                if (!(mapTileNum[col][dirtLayer - 2] == 4)){
                    mapTileNum[col][dirtLayer] = 2;
                    mapTileNum[col][dirtLayer - 1] = 2;
                } else {
                    mapTileNum[col][row] = 4;
                }
            }
            col ++;
            if (col >= limitCol){
                row ++;
                col = existingColumnsInWorld;
            }
        }
    }
    public void generateMountainGrassLayer(){
        int col = existingColumnsInWorld;
        int row = 1;
        while (col < limitCol){
            if (mapTileNum[col][row] == 2){
                mapTileNum[col][row - 1] = 1;
                col ++;
                row = 1;
            } else if(mapTileNum[col][row] != 4){
                col ++;
                row = 1;
            }
            row ++;
        }
    }
    public void generateTransparentLayer(){
        int col = existingColumnsInWorld;
        int row = 0;
        while (row < dirtLayer - 1){
            mapTileNum[col][row] = 4;
            col ++;
            if (col >= limitCol){
                row ++;
                col = existingColumnsInWorld;
            }
        }
    }
    public void generateMountains(){
        int mountainCount = 0;
        int mountainLimit = new Random().nextInt(0, maxMountains);
        while (mountainCount < mountainLimit){
            int randomYLevel = new Random().nextInt(74, 83);
            int randomXLevel = new Random().nextInt(existingColumnsInWorld + 20, existingColumnsInWorld + 80);
            int col = randomXLevel;
            int row = randomYLevel;
            int valueLeftXCounter = 1;
            int valueRightXCounter = 1;
            int addBlocksToLeftValue = new Random().nextInt(valueLeftXCounter, valueLeftXCounter + 2);
            int addBlocksToRightValue = new Random().nextInt(valueRightXCounter, valueRightXCounter + 2);
            mapTileNum[col][row] = 2;

            while (row < dirtLayer - 1){
                if (mapTileNum[randomXLevel][randomYLevel] == 2){
                    mapTileNum[col][row] = 2;
                    row ++;
                }
            }
            row = randomYLevel + 1;
            col = randomXLevel - addBlocksToLeftValue;
            while (row < dirtLayer - 1){
                if (!(mapTileNum[randomXLevel][row] == 4)){
                    int randomTile = new Random().nextInt(0, 17);
                    if (randomTile >= 15){
                        if (randomYLevel > 78) {
                            mapTileNum[col][row] = 3;
                        } else {
                            mapTileNum[col][row] = 2;
                        }
                    } else {
                        mapTileNum[col][row] = 2;
                    }
                }
                col ++;
                if (col >= randomXLevel + addBlocksToRightValue){
                    row ++;
                    valueLeftXCounter ++;
                    valueRightXCounter ++;
                    addBlocksToLeftValue = new Random().nextInt(valueLeftXCounter, valueLeftXCounter + 2);
                    addBlocksToRightValue = new Random().nextInt(valueRightXCounter, valueRightXCounter + 2);
                    col = randomXLevel - addBlocksToLeftValue;
                }
            }
            mountainCount ++;
        }

        generateMountainGrassLayer();
    }
    public void showWorldMap(){
        int col = 0;
        int row = 0;
        int num = 0;
        while (row < gp.maxWorldRow){
            System.out.print(mapTileNum[col][row] + " ");
            col ++;
            if (col >= limitCol){
                row ++;
                col = 0;
                num ++;
            }
        }
    }
    public void saveWorldMap(){
        int col = 0;
        int row = 0;
        int num = 0;
        String[] valueList = new String[gp.maxWorldRow];
        String fileCache = "";
        String saveCache = "";
        String modifier;
        while (row < gp.maxWorldRow){
            if (col == limitCol - 1){
                modifier = "";
            } else {
                modifier = " ";
            }
            valueList[row] += mapTileNum[col][row] + modifier;
            col ++;
            if (col >= limitCol){
                row ++;
                col = 0;
                num ++;
            }
        }
        try {
            String cache = "";
            FileWriter file = new FileWriter("res/data/platformer/world/worldMap.txt");
            file.write("");
            file.close();
            File file3 = new File("res/data/platformer/world/worldMap.txt");
            Scanner fileRead = new Scanner(file3);
            while ((fileRead.hasNextLine())) {
                fileCache += fileRead.nextLine() + "\n";
            }
            FileWriter file2 = new FileWriter("res/data/platformer/world/worldMap.txt");
            for(String line: valueList){
                line = line.replace("null", "");
                saveCache += line + "\n";

            }
//            file2.write(fileCache + saveCache);
//            String[] v2lst = fileCache.split("\n");
//            System.out.println(Arrays.toString(valueList));
//            for (int i = 0; i < valueList.length; i ++){
//                cache = valueList[i].replace("null", "") + v2lst[i].replace("null", "") + "\n";
//                System.out.println(cache);
//                i ++;
//            }
            cache = fileCache + saveCache;
            file2.write(cache);

            FileWriter fwriter = new FileWriter("res/data/platformer/world/world_data.txt");
            int colsInWorld = existingColumnsInWorld + maxColumnsPerCall;
            fwriter.write("existing_world_columns_in_world_map: " + colsInWorld);
            file2.close();
            fwriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}