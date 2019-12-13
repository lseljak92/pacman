package pacman;

import pacman.powerup.PowerUp;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Stack;

public class Enemy implements CollidableObject {
    private final int R = 2;
    private Random r = new Random();
    private int num;
    private int x, y, angle, vx, vy;
    private BufferedImage img;
    private BufferedImage ghostLeft;
    private BufferedImage ghostUp;
    private BufferedImage ghostRight;
    private BufferedImage ghostDown;
    private BufferedImage ghostDead;
    private BufferedImage eyes;
    private Rectangle rectangle;
    private static boolean dead = false;
    private static boolean move = false;
    private boolean collided = false;
    private boolean caught = false;
    private Stack <Integer> trackMoves = new Stack<>();

    Enemy(int x, int y, int vx, int vy, int angle, BufferedImage up, BufferedImage down, BufferedImage left, BufferedImage right, BufferedImage ghostDead, BufferedImage eyes) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = up;
        this.angle = angle;
        this.rectangle = new Rectangle(x, y, img.getWidth(), img.getHeight());
        num = r.nextInt(3);
        loadImages(up, down, left, right, ghostDead, eyes);
    }

    private void loadImages(BufferedImage up, BufferedImage down, BufferedImage left, BufferedImage right, BufferedImage ghostDead, BufferedImage eyes){
        this.ghostUp = up;
        this.ghostDown = down;
        this.ghostLeft = left;
        this.ghostRight = right;
        this.ghostDead = ghostDead;
        this.eyes = eyes;
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
    }

    public static void setStatus(boolean updateStatus) {
        dead = updateStatus;
    }

    public static boolean getStatus() {
        return dead;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void checkCollision(CollidableObject c) {
        if(this.getRectangle().intersects(c.getRectangle())){
            if(c instanceof PacMan){
                if(!dead)
                    ((PacMan) c).removeLife();
                else if(dead) {
                    collided = true;
                    caught = true;
                }
            }  else if (c instanceof Enemy || c instanceof PowerUp) {
                collided = false;
            } else {
                Rectangle intersection = this.getRectangle().intersection(c.getRectangle());
                if(intersection.height > intersection.width  && this.x < intersection.x){ //left
                    x-= intersection.width + 5;
                    collided = true;
                }
                else if(intersection.height > intersection.width  && this.x > c.getRectangle().x){ //right
                    x+= intersection.width + 5;
                    collided = true;
                }
                else if(intersection.height < intersection.width  && this.y < intersection.y){ //up
                    y-= intersection.height + 5;
                    collided = true;
                }
                else if(intersection.height < intersection.width  && this.y > c.getRectangle().y){ //down
                    y+= intersection.height + 5;
                    collided = true;
                } else {
                    collided = false;
                }
            }

        }
        if(collided) {
            decideDirection();
            collided = false;
        }
    }
//
//    private void backTrackMoves() {
//            this.currentMove = trackMoves.pop();
//            if(this.currentMove == 0)
//                this.num = 5;
//            else if(this.currentMove == 1)
//                this.num = 2;
//            else if(this.currentMove == 2)
//                this.num = 1;
//            else if(currentMove > 2)
//                this.num = 0;
//
//        //this.setImg(ghostUp);
//    }

    private void decideDirection() {
        this.num = r.nextInt(5);
        trackMoves.add(num);
    }

    private void moveGhosts() {
        if(num == 0) {
            if(!dead) { this.setImg(ghostRight); }
            else if(dead && caught) { this.setImg(eyes);}
            else { this.setImg(ghostDead); }
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x += vx;
            y += vy;
        } else if (num == 1) {
            if(!dead) { this.setImg(ghostDown); }
            else if(dead && caught) { this.setImg(eyes); }
            else { this.setImg(ghostDead); }
            vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
            x += vx;
            y += vy;
        } else if (num == 2) {
            if(!dead) { this.setImg(ghostUp); }
            else if(dead && caught) { this.setImg(eyes); }
            else { this.setImg(ghostDead); }
            vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
            x -= vx;
            y -= vy;
        } else {
            if(!dead) { this.setImg(ghostLeft); }
            else if(dead && caught) { this.setImg(eyes); }
            else { this.setImg(ghostDead); }
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x -= vx;
            y -= vy;
        }
    }

    public void update(){
        if(move) {
            moveGhosts();
        }
    }

    public static void setMoveStatus(boolean moveStatus) { move = moveStatus; }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }
}
