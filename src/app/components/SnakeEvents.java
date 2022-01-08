package app.components;

import java.awt.event.KeyEvent;

public abstract class SnakeEvents {
    abstract void onMovementKeyPress(KeyEvent e);
    abstract void onNonMovementKeyPress(KeyEvent e);
}
