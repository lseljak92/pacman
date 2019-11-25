/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

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
        BufferedImage t1img=null, backgroundImg;

        try {
            BufferedImage tmp;

            t1img = read(new File("resources/pac_left.png"));

            backgroundImg = read(new File("resources/background.png"));

            background = new Background(backgroundImg);

            UnbreakableWall.setImg(read(new File("resources/tile.png")));

            //Bullet.setImg(ImageIO.read(getClass().getResource("/resources/Weapon.gif")));

            ExtraLife.setImg(read(new File("resources/pear.png")));

            SpeedBoost.setImg(read(new File("resources/cherry.png")));

            //player1wins = ImageIO.read(getClass().getResource("/resources/player-1-wins.png"));



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        p1 = new PacMan(555, 473, 0, 0, 0, t1img);

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

        map.handleCollision(p1);

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

        int location;
        int placement;
        for(int i=1; i <= p1.getLives(); i++){
            location = (life.getImg().getWidth() + 40) * i;
            placement = location / 2;
            g2.drawImage(life.getImg(), placement, 10, null);
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(SCREEN_WIDTH / 4, 30, 2* p1.getCurrentHealth(), 20);

        if(p1.getLives() == 0){
            gameOver = true;
        }

        g2.drawImage(world,0,0,null);

        }


}
