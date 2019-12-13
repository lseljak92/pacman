package pacman;

import pacman.powerup.*;
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
        for(int i = 540; i < width - 525; i+=15){
            walls.add(new UnbreakableWall(i, 300));
        }

        for(int i = 475; i <= width - 475; i+=15){
            walls.add(new UnbreakableWall(i, 420));
        }

        for(int i = 300; i <= 420; i+=15){
            walls.add(new UnbreakableWall(475, i));
            walls.add(new UnbreakableWall(width - 475, i));
        }

        /**
         * Create inner maze walls
         */
        for(int i = 335; i < 400; i+=15){
            walls.add(new UnbreakableWall(i, 300));
            walls.add(new UnbreakableWall(i, 420));
        }

        for(int i = 300; i < 420; i+=15){
            walls.add(new UnbreakableWall(335, i));
        }

        for(int i = 475; i > 410; i-=15){
            walls.add(new UnbreakableWall(i, 360));
        }

        for(int i = width - 345; i > width - 400; i-=15){
            walls.add(new UnbreakableWall(i, 300));
            walls.add(new UnbreakableWall(i, 420));
        }

        for(int i = 300; i < 420; i+=15){
            walls.add(new UnbreakableWall(width - 345, i));
        }

        for(int i = width - 475; i < width - 400; i+=15){
            walls.add(new UnbreakableWall(i, 360));
        }

        for(int i = 175; i < width/3; i+=15){
            walls.add(new UnbreakableWall(i, 110));
            walls.add(new UnbreakableWall(i, height - 90));
        }
        for(int i = width / 3 + 80; i < width - width / 3 - 90; i+=15){
            walls.add(new UnbreakableWall(i, 110));
            walls.add(new UnbreakableWall(i, height - 90));
        }
        for(int i = 110; i < 240; i+=15){
            walls.add(new UnbreakableWall(175, i));
            walls.add(new UnbreakableWall(width - 195, i));
        }
        for(int i = width - 195; i > width - 400; i-=15){
            walls.add(new UnbreakableWall(i, 110));
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
        for(int i = width / 4 - 25; i <= width - width/4 + 10; i+=15){
            walls.add(new UnbreakableWall(i,175));
            walls.add(new UnbreakableWall(i, 495));
            walls.add(new UnbreakableWall(i, height-160));
        }
        for(int i = 570; i < height - 160; i += 15) {
            walls.add(new UnbreakableWall(width / 4 - 25, i));
            walls.add(new UnbreakableWall(width - width/4, i));
        }
        for(int i = 175; i < 425; i+=15){
            walls.add(new UnbreakableWall(width/4-25, i));
            walls.add(new UnbreakableWall(width - width/4 +12, i));
        }
        for(int i = 335; i <= width - 350; i+=15){
            walls.add(new UnbreakableWall(i, 235));
            walls.add(new UnbreakableWall(i, 570));
            walls.add(new UnbreakableWall(i, height - 235));
        }
        for(int i = 420; i <= width - 430; i+=15){
            walls.add(new UnbreakableWall(i, height - 305));
        }
        for(int i = 570; i < height - 305; i+=15) {
            walls.add(new UnbreakableWall(335, i));
        }
        for(int i = 570; i < height - 305; i+=15) {
            walls.add(new UnbreakableWall(width - 350, i));
        }

        /**
         * Add collectible PacDots
         */
        for(int i = 180; i < width - 175; i+=50){
            powerUps.add(new PacDots(i, 80));
            powerUps.add(new PacDots(i, height - 55));
        }
        /**
         * Add power ball on screen corners
         */
        powerUps.add(new PowerBall(138, 78));                    // upper left
        powerUps.add(new PowerBall(width - 164, 78));            // upper right
        powerUps.add(new PowerBall(138, height - 60));           // lower left
        powerUps.add(new PowerBall(width - 164, height - 60));   // lower right


        for(int i = 120; i < height - 70; i+=45) {
            powerUps.add(new PacDots(140, i));
            powerUps.add(new PacDots(width - 160, i));
        }
        for (int i = 250; i < 525; i+=50) {
            powerUps.add(new PacDots(i, 455));
        }
        for(int i = 250; i < width - 250; i+=50){
            powerUps.add(new PacDots(i, 140));
            powerUps.add(new PacDots(i, height - 125));
        }
        for(int i = 140; i < height - 120; i+=50){
            powerUps.add(new PacDots(215, i));
            powerUps.add(new PacDots(width - 230, i));
        }
        for (int i = 625; i < width - 250; i+=50) {
            powerUps.add(new PacDots(i, 455));
        }
        for(int i = 214; i < 430; i+=50){
            powerUps.add(new PacDots(300,i));
            powerUps.add(new PacDots(width - 305, i));
        }
        for(int i = 350; i < width-325; i+=50){
            powerUps.add(new PacDots(i,205));
            powerUps.add(new PacDots(i,265));
        }
        for(int i = 250; i < width -250; i +=50) {
            powerUps.add(new PacDots(i, 530));
        }
        for(int i = 300; i < width -300; i +=50) {
            powerUps.add(new PacDots(i, 745));
        }
        for(int i = 375; i < 450; i+=50){
            powerUps.add(new PacDots(i, 330));
            powerUps.add(new PacDots(i, 390));
        }

        for(int i = width - 430; i < width - 360; i+=50){
            powerUps.add(new PacDots(i, 330));
            powerUps.add(new PacDots(i, 390));
        }

        /**
         * Add power ball bonus
         */
        powerUps.add(new PowerBall(210, height - 128));
        powerUps.add(new PowerBall(width - 233, height - 128));

        for(int i = 580; i < 715; i += 50) {
            powerUps.add(new PacDots(300, i));
            powerUps.add(new PacDots(width - 320, i));
        }
        for(int i = 380; i < width - 350; i += 50) {
            powerUps.add(new PacDots(i, height - 340));
            powerUps.add(new PacDots(i, height - 270));
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

