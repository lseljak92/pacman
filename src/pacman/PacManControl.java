/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class PacManControl implements KeyListener {

    private PacMan player;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    
    public PacManControl(PacMan player, int up, int down, int left, int right) {
        this.player = player;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.player.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.player.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.player.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.player.toggleRightPressed();
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.player.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.player.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.player.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.player.unToggleRightPressed();
        }

    }
}
