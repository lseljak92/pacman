package pacman;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet implements CollidableObject {
    private static BufferedImage bulletImg;
    private BufferedImage explosion;
    private final int R = 5;
    private int x, y, angle, vx, vy;
    private Rectangle r;
    private boolean collided = false;
    int waitTime = 0;


    public Bullet(int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        this.angle =  angle;
        this.r = new Rectangle(x, y, bulletImg.getWidth(), bulletImg.getHeight());
    }

    public void drawImage(Graphics2D buffer){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bulletImg.getWidth() / 2.0, this.bulletImg.getHeight() / 2.0);
        buffer.drawImage(bulletImg, x, y, null);

    }

    public static void setImg(BufferedImage img){
        bulletImg = img;
    }

    public boolean hasCollided() {
        return collided;
    }

    @Override
    public void checkCollision(CollidableObject c) {
        if(this.getRectangle().intersects(c.getRectangle())){
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
        return new Rectangle(x, y, bulletImg.getWidth(), bulletImg.getHeight());
    }
}
