package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gp;
    public BufferedImage RightImage, LeftImage, RightImage2, LeftImage2, Image;
    public int speed;
    public int Speed;
    public int defaultSpeed;
    public int screenX;
    public int screenY;
    public int worldX;
    public int worldY;
    public int gravity;
    public int Gravity = 8;

    public Rectangle solidArea;

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public String direction;
    public int animationState;
    public int spriteCounter;

    public int collX = 0;
    public int collY = 0;

    public int jumpCounter = 0;
    public int jumpLoss = 0;
    public int JumpLoss = 0;

    public int JumpStrength = 0;
    public int defaultJumpStrength;
    public int DefaultJumpStrength;
    public int jumpStrength;

    public boolean falling = false;
    public boolean jumping = false;
    public boolean collisionOn = false;
    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void draw(Graphics2D g2){

    }
    public void update(){
        gp.cChecker.addGravity(this);
        gravity = gp.accelerate(Gravity);
        speed = gp.accelerate(Speed);
        jumpLoss = gp.accelerate(JumpLoss);
        defaultJumpStrength = gp.accelerate(DefaultJumpStrength);

        if (jumping){
            jumpStrength -= jumpLoss;
            int jumpNum = jumpStrength - gravity;
            falling = true;
            if (jumpNum <= 0){
                jumping = false;
                jumpStrength = defaultJumpStrength;
            } else {
                worldY -= jumpNum;
            }
        } else {
            jumpStrength = gp.accelerate(JumpStrength);
        }
    }
}
