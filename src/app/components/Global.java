package app.components;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public final class Global {

    public static final int gameSize = 41;

    public static final SnakeTile[][] GAME_TILES;

    static {
        GAME_TILES = new SnakeTile[gameSize][gameSize];
        System.out.println(":)");
    }

    public static SnakeObj snakeObj;
    public static GameSpace.Movement nextMovement = null;

    public static final GameSpace gameSpace = new GameSpace();
    public static final Dimension viewportSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension newScreenSize = new Dimension((int) (viewportSize.height * 0.5),
            (int) (viewportSize.height * 0.5));
    public static KeyEvent keyPress = null;


    public static final Object keyListenerLock = new Object();

    public static <T> T[] removeImmediateRepeatingElements(final T[] array) {
        ArrayList<T> copy = new ArrayList<>();
        T latest = null;
        for (T element : array) {
            if (!element.equals(latest)) {
                copy.add(element);
                latest = element;
            }
        }
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), 0);
        return copy.toArray(result);
    }
}
