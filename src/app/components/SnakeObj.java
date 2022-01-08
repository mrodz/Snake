package app.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SnakeObj {
    int headX;
    int headY;
    int tailX;
    int tailY;

    int secondLastTailX;
    int secondLastTailY;

    List<SnakeTile> tiles = new ArrayList<>();

    public SnakeObj(int headX, int headY) {
        this.headX = headX;
        this.headY = headY;
        this.tailX = headX;
        this.tailY = headY;
        this.secondLastTailX = headX;
        this.secondLastTailY = headY;
    }
}
