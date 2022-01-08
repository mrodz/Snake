package app.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static com.github.mrodz.jutils.Commons.*;

public class GameSpace extends JPanel {
    public enum Movement {
        UP, DOWN, LEFT, RIGHT;
    }

    public GameSpace() {
        this.setLayout(new GridLayout(Global.gameSize, Global.gameSize));

        int centerCoord = Global.gameSize / 2;

        Global.snakeObj = new SnakeObj(centerCoord, centerCoord);

        for (int i : range(0, Global.gameSize - 1)) {
            for (int j : range(0, Global.gameSize - 1)) {
                Global.GAME_TILES[i][j] = new SnakeTile(i, j, i == j && i == centerCoord ? SnakeTile.Colors.SNAKE : null);
                this.add(Global.GAME_TILES[i][j]);
            }
        }
    }

    public static Movement mapKeyEventToMovement(KeyEvent e) {
        switch (String.valueOf(e.getKeyChar()).toUpperCase()) {
            case "W": {
                return Movement.UP;
            }
            case "A": {
                return Movement.LEFT;
            }
            case "S": {
                return Movement.DOWN;
            }
            case "D": {
                return Movement.RIGHT;
            }
            default: {
                throw new IllegalArgumentException(e.getKeyChar() + " is not a movement character.");

            }
        }
    }

    private void fail() {
        System.out.println("Game Over!");
        gameLoop.stop();
    }

    public static int[] mapMovementToArr(Movement m) {
        switch (m) {
            case UP: {
                return new int[] {-1, 0};
            }
            case LEFT: {
                return new int[] {0, -1};
            }
            case DOWN: {
                return new int[] {+1, 0};
            }
            case RIGHT: {
                return new int[] {0, +1};
            }
        }
        throw new IllegalStateException("this should never be thrown");
    }

    boolean hasApple = false;

    Timer gameLoop = new Timer(150, (t) -> {
        if (Global.nextMovement != null) {
            System.out.println(Global.nextMovement);
            int[] planned = mapMovementToArr(Global.nextMovement);
            int newX = Global.snakeObj.headX + planned[0], newY = Global.snakeObj.headY + planned[1];

            SnakeTile collision = Global.GAME_TILES[newX][newY];

            boolean preserveTail = collision.isApple();

            Global.GAME_TILES[Global.snakeObj.tailX][Global.snakeObj.tailY].setSnake(preserveTail); // true if hits apple; else, false.

            Global.snakeObj.secondLastTailX = Global.snakeObj.headX;
            Global.snakeObj.secondLastTailY = Global.snakeObj.headY;

            Global.snakeObj.headX = newX;
            Global.snakeObj.headY = newY;



            if (collision.isApple()) {
                putApple();
            } else {
                Global.snakeObj.tailY = Global.snakeObj.secondLastTailY;
                Global.snakeObj.tailX = Global.snakeObj.secondLastTailX;
            }

            if (collision.isSnake()) fail();

            Global.GAME_TILES[newX][newY].setSnake(true);

            GameSpace.this.repaint();
//            incrementSnake(Global.snakeObj);
        } else {
            if (!hasApple) {
                putApple();
                hasApple = true;
            }
        }
    });

    public static void putApple() {
        Random r = new Random();
        int x, y;
        do {
            x = r.nextInt(Global.gameSize);
            y = r.nextInt(Global.gameSize);
        } while (!Global.GAME_TILES[x][y].isGrass());

        Global.GAME_TILES[x][y].setApple(true);

        Global.gameSpace.repaint();
    }

    public static void incrementSnake(SnakeObj snake) {
        Movement m = Global.nextMovement;
        int[] applied = mapMovementToArr(m);
        System.out.println(Arrays.toString(applied));
        snake.headX += applied[0];
        snake.headY += applied[1];
    }

    {
        gameLoop.start();
        handleKeyPresses(new SnakeEvents() {
            @Override
            void onMovementKeyPress(KeyEvent e) {
                Movement nextMove = mapKeyEventToMovement(e);

                System.out.println(nextMove);

                Global.nextMovement = nextMove;

            }

            @Override
            void onNonMovementKeyPress(KeyEvent e) {
                System.out.println("Non-movement key pressed >> " + e.getKeyChar());
            }
        });
    }

    public static boolean isMovementKey(KeyEvent e) {
        return String.valueOf(ApplicationWindow.supplyOnNullPredicate(e, KeyEvent::getKeyChar, () -> (char) 0)).matches("^[wWaAsSdD]$");
    }

    public void handleKeyPresses(SnakeEvents config) {
        Thread independentAction = new Thread(() -> {
            while (true) {
                synchronized (Global.keyListenerLock) {
                    if (Objects.isNull(Global.keyPress)) continue;

                    if (isMovementKey(Global.keyPress)) {
                        config.onMovementKeyPress(Global.keyPress);
                    } else {
                        config.onNonMovementKeyPress(Global.keyPress);
                    }

                    try {
                        Global.keyListenerLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        independentAction.setName("Thread-SnakeKeyListener");
        independentAction.start();
    }
}
