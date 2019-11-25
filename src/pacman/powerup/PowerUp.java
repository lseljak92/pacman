package pacman.powerup;

import pacman.CollidableObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp implements CollidableObject{
    @Override
    public void checkCollision(CollidableObject c) { }

    @Override
    public Rectangle getRectangle() { return null; }

    public static void setImg(BufferedImage powerUp){ }

    public void drawImage(Graphics2D buffer){ }

    public abstract boolean hasCollided();
}
