package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    public int cursor = 0;
    public int maxCursor = 0;

    public UI(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        if (gp.gameState == gp.titleState){drawTitleScreen();maxCursor = 3;}
    }

    public void drawTitleScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont((float) gp.tileSize * 3));

        String text = "2D Platformer";
        int x = CenterText(text);
        int y = gp.tileSize * 3;

        g2.drawString(text, x, y);

        y += gp.tileSize * 5;drawOptions("Play Game", y, 0);
        y += gp.tileSize;drawOptions("Load Game", y, 1);
        y += gp.tileSize;drawOptions("Options", y, 2);
        y += gp.tileSize;drawOptions("Quit Game", y, 3);
    }
    public void drawOptions(String text, int y, int ifCursor){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont((float) gp.tileSize));
        g2.drawString(text, CenterText(text), y);
        if (cursor == ifCursor){
            g2.drawString(">", CenterText(text) - gp.tileSize, y);
        }
    }
    public int CenterText(String text){
        return (int)((gp.screenWidth2 / 2) - (g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2));
    }
}
