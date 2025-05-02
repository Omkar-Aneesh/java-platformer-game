package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean wPressed, aPressed, sPressed, dPressed, enterPressed, spacePressed, shiftPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState){titleState(code);}
        if (gp.gameState == gp.playState){playState(code);}
    }
    public void playState(int code){
        if (code == KeyEvent.VK_A){
            aPressed = true;
            dPressed = false;
        }
        else if (code == KeyEvent.VK_D){
            aPressed = false;
            dPressed = true;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        else if (code == KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }
    }
    public void titleState(int code){
        if (code == KeyEvent.VK_W){
            if (gp.ui.cursor > 0){
                gp.ui.cursor --;
            } else {
                gp.ui.cursor = gp.ui.maxCursor;
            }
        }
        if (code == KeyEvent.VK_S){
            if (gp.ui.cursor < gp.ui.maxCursor){
                gp.ui.cursor ++;
            } else {
                gp.ui.cursor = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            int cursor = gp.ui.cursor;
            if (cursor == 0){gp.gameState = gp.playState;}
            if (cursor == 2){gp.gameState = gp.optionState;}
            if (cursor == 3){System.exit(0);}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_A){
                aPressed = false;
            }
            else if (code == KeyEvent.VK_D){
                dPressed = false;
            }
            if (code == KeyEvent.VK_SPACE){
                spacePressed = false;
            }
            else if (code == KeyEvent.VK_SHIFT){
                shiftPressed = false;
            }
        }
    }
}
