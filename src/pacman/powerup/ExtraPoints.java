package pacman.powerup;

import pacman.CollidableObject;
import pacman.PacMan;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExtraPoints extends PowerUp{

        private int x, y;
        private Rectangle r;
        private static BufferedImage img;
        private boolean collided = false;

        public ExtraPoints(int x, int y){
            this.x = x;
            this.y = y;
            this.r = new Rectangle(x, y, img.getWidth(), img.getHeight());
        }

        public static void setImg(BufferedImage img){
            ExtraPoints.img = img;
        }

        @Override
        public void checkCollision(CollidableObject c) {
            if(c instanceof PacMan){
                if(this.getRectangle().intersects(c.getRectangle())){
                    collided = true;
                    ((PacMan) c).addPoints(200);
                }
            }
        }

        public boolean hasCollided() { return collided; }

        @Override
        public Rectangle getRectangle() {
            return new Rectangle(x, y, img.getWidth(), img.getHeight());
        }

        public void drawImage(Graphics2D buffer){
            buffer.drawImage(img, x, y, null);
        }

}
