package pacman;

import pacman.powerup.ExtraLife;
import pacman.powerup.PacDots;
import pacman.powerup.PowerUp;
import pacman.powerup.SpeedBoost;
import pacman.walls.UnbreakableWall;
import pacman.walls.Wall;

import java.awt.*;
import java.util.ArrayList;

public class GameMap {

    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    int width = GameWorld.SCREEN_WIDTH;
    int height = GameWorld.SCREEN_HEIGHT;

    public GameMap(){

        /**
         * Set up border walls of the maze
         */
        for(int i = 105; i < width - 105; i += 15){
            walls.add(new UnbreakableWall(i, 45));
            walls.add(new UnbreakableWall(i, height - 15));
        }

        for(int i = 45; i <=  height / 2 - 45; i += 15){
            walls.add(new UnbreakableWall(width - 120, i));
            walls.add(new UnbreakableWall(105, i));
        }

        for(int i = height / 2 + 30; i < height; i += 15){
            walls.add(new UnbreakableWall(width - 120, i));
            walls.add(new UnbreakableWall(105, i));
        }

        /**
         * Set up walls for connecting passage between left and right sides of the maze
         */
        for(int i = 0; i < 120; i+=15) {
            walls.add(new UnbreakableWall(i, height/2-45));
            walls.add(new UnbreakableWall(i, height/2+30));
        }
        for(int i = width - 120; i < width; i+=15){
            walls.add(new UnbreakableWall(i, height/2-45));
            walls.add(new UnbreakableWall(i, height/2+30));
        }

        /**
         * Set up middle area (enemies' starting point)
         */
        for(int i = width / 3; i < width/3 + 145; i+=15){
            walls.add(new UnbreakableWall(i, 300));
        }

        for(int i = width - width/3; i > width - width/3 - 145; i-=15){
            walls.add(new UnbreakableWall(i, 300));
        }

        for(int i = width / 3; i <= width - width/3; i+=15){
            walls.add(new UnbreakableWall(i, 420));
            walls.add(new UnbreakableWall(i, 235));
        }
        for(int i = 300; i <= 420; i+=15){
            walls.add(new UnbreakableWall(width/3, i));
            walls.add(new UnbreakableWall(width - width/3, i));
        }

        /**
         * Create inner maze walls
         */
        for(int i = 175; i < width/3; i+=15){
            walls.add(new UnbreakableWall(i, 115));
            walls.add(new UnbreakableWall(i, height - 90));
        }
        for(int i = 130; i < 240; i+=15){
            walls.add(new UnbreakableWall(175, i));
            walls.add(new UnbreakableWall(width - 195, i));
        }
        for(int i = 175; i < 230; i++){
            walls.add(new UnbreakableWall(i,240));
        }
        for(int i = width - 195; i > width - 400; i-=15){
            walls.add(new UnbreakableWall(i, 115));
            walls.add(new UnbreakableWall(i, height - 90));
        }
        for(int i = height - height/3 + 70; i < height - 90; i+=15){
            walls.add(new UnbreakableWall(175, i));
            walls.add(new UnbreakableWall(width - 195, i));
        }
        for(int i = height/3; i < height - height/3; i+=15){
            walls.add(new UnbreakableWall(175, i));
            walls.add(new UnbreakableWall(width - 195, i));
        }
        for(int i = width / 4 - 25; i <= width - width/4 + 25; i+=15){
            walls.add(new UnbreakableWall(i,175));
            walls.add(new UnbreakableWall(i, 495));
            walls.add(new UnbreakableWall(i, height-160));
        }
        for(int i = 175; i < 425; i+=15){
            walls.add(new UnbreakableWall(width/4 + 25, i));
            walls.add(new UnbreakableWall(width - width/4 - 25, i));
        }

        /**
         * Add collectible PacDots
         */
        for(int i = 180; i < width - 175; i+=50){
            powerUps.add(new PacDots(i, 80));
            powerUps.add(new PacDots(i, height - 55));
        }
        for(int i = 120; i < height - 70; i+=45) {
            powerUps.add(new PacDots(140, i));
            powerUps.add(new PacDots(width - 160, i));
        }

    }

    public void handleCollision(CollidableObject c) {
        for(int i = 0; i < walls.size(); i++){
            c.checkCollision(walls.get(i));
        }
        for(int i = 0; i < powerUps.size(); i++){
            powerUps.get(i).checkCollision(c);
            c.checkCollision(powerUps.get(i));
            if(powerUps.get(i).hasCollided()){
                powerUps.remove(i);
            }
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2g = (Graphics2D) g;
        for(int i = 0; i < walls.size(); i++) {
            walls.get(i).drawImage(g2g);
        }
        for(int i = 0; i < powerUps.size(); i++){
            powerUps.get(i).drawImage(g2g);
        }
    }
}

