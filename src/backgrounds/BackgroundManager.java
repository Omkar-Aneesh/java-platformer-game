package backgrounds;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundManager {
    GamePanel gp;
    public BufferedImage background;

    public BackgroundManager(GamePanel gp){
        this.gp = gp;

        background = gp.loadAsset("/backgrounds/brightSky", gp.screenWidth2, gp.screenHeight2);
    }
    public void draw(Graphics2D g2){
        if (gp.gameState == gp.playState){
            g2.drawImage(background, 0, 0, null);
        }
    }
}
