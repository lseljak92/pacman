/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pacman.powerup.PacDots;
import pacman.powerup.SpeedBoost;
import pacman.walls.UnbreakableWall;
import pacman.powerup.ExtraLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;


public class GameWorld extends JPanel  {

    public static final int SCREEN_WIDTH = 1125;
    public static final int SCREEN_HEIGHT = 945;
    private static boolean gameOver = false;
    private static ExtraLife life;

    private BufferedImage world;
    private BufferedImage player1wins;
    private Graphics2D buffer;
    private JFrame jf;
    private PacMan p1;
    private Enemy ghost1;
    private Enemy ghost2;
    private Enemy ghost3;
    private Enemy ghost4;
    private GameMap map;
    private Background background;


    public static void main(String[] args) {
        Thread x;
        GameWorld trex = new GameWorld();
        trex.init();
        try {

            while (!gameOver) {
                trex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }
    }

    private void init() {
        this.jf = new JFrame("PacMan");

        this.world = new BufferedImage(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img=null, left = null, up = null, down = null, closed = null, backgroundImg;
        BufferedImage enemy1_up = null, enemy2_up = null, enemy3_up = null, enemy4_up = null;
        BufferedImage enemy1_down = null, enemy2_down = null, enemy3_down = null, enemy4_down = null;
        BufferedImage enemy1_left = null, enemy2_left = null, enemy3_left = null, enemy4_left = null;
        BufferedImage enemy1_right = null, enemy2_right = null, enemy3_right = null, enemy4_right = null;

        try {
            BufferedImage tmp;

            t1img = read(new File("resources/pac_right.png"));
            left = read(new File("resources/pac_left.png"));
            up = read(new File("resources/pac_up.png"));
            down = read(new File("resources/pac_down.png"));
            closed = read(new File("resources/closed_pac.png"));

            enemy1_up = read(new File("resources/green_up.png"));
            enemy1_down = read(new File("resources/green_down.png"));
            enemy1_right = read(new File("resources/green_right.png"));
            enemy1_left = read(new File("resources/green_left.png"));

            enemy2_up = read(new File("resources/red_up.png"));
            enemy2_down = read(new File("resources/red_down.png"));
            enemy2_left = read(new File("resources/red_left.png"));
            enemy2_right = read(new File("resources/red_right.png"));

            enemy3_up = read(new File("resources/blue_up.png"));
            enemy3_down = read(new File("resources/blue_down.png"));
            enemy3_left = read(new File("resources/blue_left.png"));
            enemy3_right = read(new File("resources/blue_right.png"));

            enemy4_up = read(new File("resources/purple_up.png"));
            enemy4_down = read(new File("resources/purple_down.png"));
            enemy4_left = read(new File("resources/purple_left.png"));
            enemy4_right = read(new File("resources/purple_right.png"));

            backgroundImg = read(new File("resources/background.png"));
            background = new Background(backgroundImg);
            UnbreakableWall.setImg(read(new File("resources/tile.png")));
            PacDots.setImg(read(new File("resources/collectible_small.png")));
            ExtraLife.setImg(read(new File("resources/life.png")));
            SpeedBoost.setImg(read(new File("resources/cherry.png")));

            //player1wins = ImageIO.read(getClass().getResource("/resources/player-1-wins.png"));



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        p1 = new PacMan(550, 440, 0, 0, 0, up, down, left, t1img, closed);

        ghost1 = new Enemy(SCREEN_WIDTH / 3 + 90, 350, 0, 0, 0, enemy1_up, enemy1_down, enemy1_left, enemy1_right);
        ghost2 = new Enemy(SCREEN_WIDTH / 3 + 150, 350, 0, 0, 0, enemy2_up, enemy2_down, enemy2_left, enemy2_right);
        ghost3 = new Enemy(SCREEN_WIDTH / 3 + 210, 350, 0, 0, 0, enemy3_up, enemy3_down, enemy3_left, enemy3_right);
        ghost4 = new Enemy(SCREEN_WIDTH / 3 + 270, 350, 0, 0, 0, enemy4_up, enemy4_down, enemy4_left, enemy4_right);

        map = new GameMap();

        life = new ExtraLife(0, 0);

        PacManControl tc1 = new PacManControl(p1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);

        this.jf.setSize(SCREEN_WIDTH, SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    private void update(){
        p1.update();
        ghost1.update();
        ghost2.update();
        ghost3.update();
        ghost4.update();
        p1.checkCollision(ghost1);
        p1.checkCollision(ghost2);
        p1.checkCollision(ghost3);
        p1.checkCollision(ghost4);

        map.handleCollision(p1);
        map.handleCollision(ghost1);
        map.handleCollision(ghost2);
        map.handleCollision(ghost3);
        map.handleCollision(ghost4);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        update();

        buffer = world.createGraphics();

        super.paintComponent(g2);

        this.setBackground(Color.black);

        this.background.drawImage(buffer);

        map.drawImage(buffer);

        this.p1.drawImage(buffer);

        this.ghost1.drawImage(buffer);
        this.ghost2.drawImage(buffer);
        this.ghost3.drawImage(buffer);
        this.ghost4.drawImage(buffer);

        if(p1.getLives() == 0){
            gameOver = true;
        }

        g2.drawImage(world,0,0,null);
        g2.setColor(Color.white);
        g2.setFont(new Font("Helvetica", Font.BOLD, 20));
        g2.drawString("SCORE: " + String.valueOf(p1.getScore()), SCREEN_WIDTH - 300, 30);

        /**
         * Display player's life count
         */
        int location;
        int placement;
        for(int i=1; i <= p1.getLives(); i++){
            location = (life.getImg().getWidth() + 50) * i;
            placement = location / 2;
            g2.drawImage(life.getImg(), placement + 70, 10, null);
        }

    }



}