package pacman;

import java.awt.*;

public interface CollidableObject {
    void checkCollision(CollidableObject c);
    Rectangle getRectangle();
}
