package pacman;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Enemy implements CollidableObject {
    private final int R = 5;
    private int x, y, angle, vx, vy;
    private BufferedImage img;
    private BufferedImage ghostLeft;
    private BufferedImage ghostUp;
    private BufferedImage ghostRight;
    private BufferedImage ghostDown;
    private Rectangle r;
    private boolean collided = false;

    Enemy(int x, int y, int vx, int vy, int angle, BufferedImage up, BufferedImage down, BufferedImage left, BufferedImage right) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = up;
        this.angle = angle;
        this.r = new Rectangle(x, y, img.getWidth(), img.getHeight());
        loadImages(up, down, left, right);
    }

    private void loadImages(BufferedImage up, BufferedImage down, BufferedImage left, BufferedImage right){
        this.ghostUp = up;
        this.ghostDown = down;
        this.ghostLeft = left;
        this.ghostRight = right;
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);

    }

    public boolean hasCollided() {
        return collided;
    }

    @Override
    public void checkCollision(CollidableObject c) {
        if(this.getRectangle().intersects(c.getRectangle())){
            if(c instanceof PacMan){
                ((PacMan) c).removeLife();
            }
            collided = true;
        }
    }

    public void update(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }


    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }
}
