package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    Graphics2D g2;
    public Player(GamePanel gp){
        super(gp);
        this.gp = gp;

        getPlayerImage();
        setDefaultValues();
    }
    public void getPlayerImage(){
        LeftImage = gp.loadAsset("/entity/left/playerLeft1", gp.tileSize, gp.tileSize);
        RightImage = gp.loadAsset("/entity/right/playerRight1", gp.tileSize, gp.tileSize);
        LeftImage2 = gp.loadAsset("/entity/left/playerLeft2", gp.tileSize, gp.tileSize);
        RightImage2 = gp.loadAsset("/entity/right/playerRight2", gp.tileSize, gp.tileSize);
    }
    public void setDefaultValues(){
        solidArea = new Rectangle();

        solidArea.x = (40 / 3) * gp.crystaler;
        solidArea.y = (16 / 3) * gp.crystaler;

        defaultSpeed = 8;
        Speed = defaultSpeed;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = gp.tileSize - ((72 / 3) * gp.crystaler);
        solidArea.height = gp.tileSize - ((16 / 3) * gp.crystaler);

        JumpLoss = 1;
        JumpStrength = 20;
        DefaultJumpStrength = JumpStrength;

        direction = "left";
        screenX = (gp.screenWidth2 / 2)  - (gp.tileSize / 2);
        screenY = (gp.screenHeight2 / 2) - (gp.tileSize / 2);
        worldX = gp.tileSize * 26;
        worldY = gp.tileSize * 0;
    }
    public void move(){
        if (gp.keyH.aPressed || gp.keyH.dPressed || gp.keyH.spacePressed){
            if (gp.keyH.aPressed){
                direction = "left";
            }
            if (gp.keyH.dPressed){
                direction = "right";
            }
            if (gp.keyH.spacePressed){
                if (!jumping && !falling) {
                    jumping = true;
                }
            }
            collisionOn = false;

            gp.cChecker.checkTile(this);

            if (!collisionOn && !gp.keyH.enterPressed && (gp.keyH.aPressed || gp.keyH.dPressed)){
                switch (direction){
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                }
            }
            spriteCounter ++;
            if (spriteCounter > 12){
                if (animationState == 0){
                    animationState = 1;
                } else {
                    animationState = 0;
                }
                spriteCounter = 0;
            }
        }
        switch (direction){
            case "left":
                Image = LeftImage;
                if (animationState == 1){
                    Image = LeftImage2;
                }
                break;
            case "right":
                Image = RightImage;
                if (animationState == 1){
                    Image = RightImage2;
                }
                break;
        }
    }
    public void update(){
        super.update();
        move();

        if (worldX / gp.tileSize == gp.existingColumnsInWorld - 20){
            gp.wGenerator.generateWorld();
            gp.loadWorldData();
        }
        if (worldX / gp.tileSize == 20){
            gp.wGenerator.generateLeftWorld();
            gp.loadWorldData();
            worldX += gp.tileSize * 100;
        }
//        System.out.println(worldY / gp.tileSize + " " + worldX / gp.tileSize + " " + gp.maxWorldCol);
    }

    public void draw(Graphics2D g2){
        g2.drawImage(Image, screenX, screenY, null);
//        g2.setColor(Color.RED);
//        g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//        System.out.println(solidArea.x);
//        g2.setColor(Color.RED);
//        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
    }
}
