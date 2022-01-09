package app.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.github.mrodz.jutils.Commons.cast;

public class SnakeTile extends JPanel {
    enum Colors {
        TILE_1(new Color(0x70A523)),
        TILE_2(new Color(0x498745)),
        APPLE(new Color(0xAA1616)),
        SNAKE(new Color(0x06FFFF)),
        SNAKE_HEAD(new Color(0x1C606F));

        Color color;

        Colors(Color color) {
            this.color = color;
        }

        static Colors fromColor(Color c) throws IllegalArgumentException {
            if (c.equals(TILE_1.color)) return TILE_1;
            if (c.equals(TILE_2.color)) return TILE_2;
            if (c.equals(APPLE.color)) return APPLE;
            if (c.equals(SNAKE.color)) return SNAKE;
            if (c.equals(SNAKE_HEAD.color)) return SNAKE_HEAD;
            throw new IllegalArgumentException("Color " + c + " does not exist in this enum.");
        }
    }

    boolean snake = false;
    boolean apple = false;
    boolean grass = true;
    boolean head = false;
    volatile Colors currentColor;

    final Colors initializedState;

    final int x;
    final int y;

    public SnakeTile(int x, int y, Colors initializedState) {
        this.x = x;
        this.y = y;
        this.initializedState = initializedState;
    }

    public boolean isSnake() {
        return snake;
    }

    public boolean isApple() {
        return apple;
    }

    public boolean isGrass() {
        return grass;
    }

    public void setSnake(boolean snake) {
        this.snake = snake;
    }

    public void setApple(boolean apple) {
        this.apple = apple;
    }

    public void setGrass(boolean grass) {
        this.grass = grass;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = cast(g, Graphics2D.class);

        if (Objects.nonNull(this.initializedState) && Objects.isNull(this.currentColor)) {
            assert g2d != null;
            g2d.setPaint(this.initializedState.color);
        } else {
            if (isHead()) {
                g2d.setPaint(Colors.SNAKE_HEAD.color);
            } else if (isSnake()) {
                g2d.setPaint(Colors.SNAKE.color);
            } else if (isApple()) {
                g2d.setPaint(Colors.APPLE.color);
            } else {
                g2d.setPaint(this.x % 2 == 0 ^ this.y % 2 == 0 ? Colors.TILE_1.color : Colors.TILE_2.color);
            }
        }

        this.currentColor = Colors.fromColor((Color) g2d.getPaint());

        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public String toString() {
        return "SnakeTile{" +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
