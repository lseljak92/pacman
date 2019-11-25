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
        for(int i = width / 3; i < width/3 + 145; i++){
            walls.add(new UnbreakableWall(i, 300));
        }

        for(int i = width - width/3; i > width - width/3 - 145; i--){
            walls.add(new UnbreakableWall(i, 300));
        }

        for(int i = width / 3; i < width - width/3; i++){
            walls.add(new UnbreakableWall(i, 425));
        }

        for(int i = 300; i < 425; i++){
            walls.add(new UnbreakableWall(width/3, i));
            walls.add(new UnbreakableWall(width-width/3, i));
        }


        /**
         * Add collectible PacDots
         */
        for(int i = 150; i < width - 130; i+=45){
            powerUps.add(new PacDots(i, 80));
            powerUps.add(new PacDots(i, height - 45));
        }

        for(int i = 80; i < height - 70; i+=45) {
            powerUps.add(new PacDots(150, i));
            powerUps.add(new PacDots(width - 165, i));
        }

    }

    public void handleCollision(CollidableObject c) {
        for(int i = 0; i < walls.size(); i++){
            c.checkCollision(walls.get(i));
            walls.get(i).checkCollision(c);
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

