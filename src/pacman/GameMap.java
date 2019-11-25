package pacman;

import pacman.powerup.ExtraLife;
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

    }

    public void handleCollision(CollidableObject c) {
        for(int i = 0; i < walls.size(); i++){
            c.checkCollision(walls.get(i));
            walls.get(i).checkCollision(c);
            if(walls.get(i).hasCollided()){
                walls.remove(i);
            }
        }
        for(int i = 0; i < powerUps.size(); i++){
            powerUps.get(i).checkCollision(c);
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

