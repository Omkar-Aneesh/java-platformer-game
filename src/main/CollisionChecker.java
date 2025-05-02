package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        String direction = entity.direction;

        try {
            switch (direction) {
                case "left" -> {
                    entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol - 1][entityTopRow - 1];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow - 1];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                case "right" -> {
                    entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol + 1][entityTopRow - 1];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow - 1];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
            }
        } catch (Exception ignored){}
    }
    public void addGravity(Entity entity){
        int entityRow = (entity.worldY + entity.solidArea.y) / gp.tileSize;
        int entityCol = (entity.worldX + entity.solidArea.x) / gp.tileSize;
        int entityRow2 = (entity.worldY + (entity.solidArea.y + entity.solidArea.height)) / gp.tileSize;
        int entityCol2 = (entity.worldX + (entity.solidArea.x + entity.solidArea.width)) / gp.tileSize;

        int tileNum1, tileNum2;
        try{
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityCol][entityRow2];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityCol2][entityRow2];
            if (!(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)){
                if (!entity.jumping) {
                    entity.worldY += entity.gravity;
                    entity.falling = true;
                }
            } else {
                entity.falling = false;
                if (!entity.jumping) {
                    if (entity.worldY > entityRow * gp.tileSize) {
                        entity.worldY = entityRow * gp.tileSize;
                    } else if(entity.worldY < entityRow * gp.tileSize){
                        entity.worldY = entityRow * gp.tileSize;
                    }
                }
            }
        } catch (Exception e){
            entity.worldY += entity.gravity;
            entity.falling = true;
        }
    }
}
