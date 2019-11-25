package pacman;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class PacMan implements CollidableObject {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private Rectangle r;

    private int R = 3;
    private int currentHealth = 100;
    private int lives = 3;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;


    PacMan(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.r = new Rectangle(x, y, img.getWidth(), img.getHeight());

    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setSpeed(int value) { this.R+=value; }

    public void collided(int value){
        if(currentHealth - value <= 0){
            currentHealth = 0;
            removeLife();
        }
        else
            currentHealth -= value;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void powerHealth(int value){
        if(currentHealth + value >= 100)
            currentHealth = 100;
        else
            currentHealth += value;
    }

    public int getCurrentHealth() { return currentHealth; }


    public void addLife() { this.lives += 1; }

    public int getLives() { return this.lives; }

    public void removeLife() {
        if(lives == 0){
            currentHealth = 0;
        } else {
            lives -= 1;
            powerHealth(100);
        }
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }




    public void update() {
        if (this.UpPressed) {
            this.moveUp();
        }
        if (this.DownPressed) {
            this.moveDown();
        }

        if (this.LeftPressed) {
            this.moveLeft();
        }
        if (this.RightPressed) {
            this.moveRight();
        }
    }

    private void moveLeft() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;

        checkBorder();
    }

    private void moveRight() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

        checkBorder();
    }

    private void moveDown() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void moveUp() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(90)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(90)));
        x -= vx;
        y -= vy;

        checkBorder();
    }

    @Override
    public void checkCollision(CollidableObject c) {
        if(this.getRectangle().intersects(c.getRectangle())){
            if(c instanceof Bullet){
                collided(10);
            } else {
                Rectangle intersection = this.getRectangle().intersection(c.getRectangle());
                if(intersection.height > intersection.width  && this.x < intersection.x){ //left
                    x-= intersection.width/2;
                }
                else if(intersection.height > intersection.width  && this.x > c.getRectangle().x){ //right
                    x+= intersection.width/2;
                }
                else if(intersection.height < intersection.width  && this.y < intersection.y){ //up
                    y-= intersection.height/2;
                }
                else if(intersection.height < intersection.width  && this.y > c.getRectangle().y){ //down
                    y+= intersection.height/2;
                }
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }


    private void checkBorder() {
        if (x < 0) {
            x = GameWorld.SCREEN_WIDTH - 15;
        }
        if (x >= GameWorld.SCREEN_WIDTH) {
            x = 0;
        }
        if (y < 30) {
            y = 30;
        }
        if (y >= GameWorld.SCREEN_HEIGHT - 30) {
            y = GameWorld.SCREEN_HEIGHT - 30;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
    }

}
