package pacman;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage background;

    public Background(BufferedImage img) {
        initBackground(img);
    }

    private void initBackground(BufferedImage img) {
        loadImage(img);
    }

    private void loadImage(BufferedImage img) {
        this.background = img;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < GameWorld.SCREEN_WIDTH; i+=background.getWidth()){
            for(int j = 0; j < GameWorld.SCREEN_HEIGHT; j+=background.getHeight()){
                g2d.drawImage(this.background, i,j, null);
            }
        }
    }
}
