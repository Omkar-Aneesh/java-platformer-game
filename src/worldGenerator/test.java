package worldGenerator;

import main.GamePanel;

public class test {
    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        WorldGenerator worldGenerator = new WorldGenerator(gp);
        worldGenerator.generateWorld();
    }
}
