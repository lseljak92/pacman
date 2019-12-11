package pacman;

import pacman.powerup.PacDots;
import pacman.walls.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy implements CollidableObject {
    private final int R = 2;
    private Random r = new Random();
    private int num;
    private int movetries;
    private int x, y, angle, vx, vy;
    private BufferedImage img;
    private BufferedImage ghostLeft;
    private BufferedImage ghostUp;
    private BufferedImage ghostRight;
    private BufferedImage ghostDown;
    private Rectangle rectangle;
    private boolean collided = false;

    Enemy(int x, int y, int vx, int vy, int angle, BufferedImage up, BufferedImage down, BufferedImage left, BufferedImage right) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = up;
        this.angle = angle;
        this.rectangle = new Rectangle(x, y, img.getWidth(), img.getHeight());
        num = r.nextInt(3);
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

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void checkCollision(CollidableObject c) {
        if(this.getRectangle().intersects(c.getRectangle())){
            if(c instanceof PacMan){
                ((PacMan) c).removeLife();
            } else if (c instanceof Enemy || c instanceof PacDots) {
                collided = false;
            } else {
                Rectangle intersection = this.getRectangle().intersection(c.getRectangle());
                if(intersection.height > intersection.width  && this.x < intersection.x){ //left
                    x-= intersection.width/2;
                    movetries = r.nextInt(2);
                    if(movetries == 0)
                    num = 3;
                }
                else if(intersection.height > intersection.width  && this.x > c.getRectangle().x){ //right
                    x+= intersection.width/2;
                    num = 0;
                }
                else if(intersection.height < intersection.width  && this.y < intersection.y){ //up
                    y-= intersection.height/2;
                    num = 2;
                }
                else if(intersection.height < intersection.width  && this.y > c.getRectangle().y){ //down
                    y+= intersection.height/2;
                    num = 1;
                }
            }

        }
    }

    private void moveGhosts() {
        if(num == 0) {
            this.setImg(ghostRight);
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x += vx;
            y += vy;
        } else if (num == 1) {
            this.setImg(ghostDown);
            vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
            x += vx;
            y += vy;
        } else if (num == 2) {
            this.setImg(ghostUp);
            vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
            x -= vx;
            y -= vy;
        } else {
            this.setImg(ghostLeft);
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x -= vx;
            y -= vy;
        }
    }

    public void update(){
        moveGhosts();
    }


    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }
}
