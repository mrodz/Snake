package app.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ApplicationWindow extends JFrame {
    public ApplicationWindow() {
        this.setLayout(new BorderLayout());
        this.add(Global.gameSpace, BorderLayout.CENTER);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("SnakeObj Game");
        this.setSize(Global.newScreenSize);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                synchronized (Global.keyListenerLock) {
                    if (e.getKeyChar() != supplyOnNullPredicate(Global.keyPress, KeyEvent::getKeyChar, () -> (char) 0)) {
                        Global.keyPress = e;
                        Global.keyListenerLock.notifyAll();
                    }
                }
            }
        });
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static <T, R> R supplyOnNullPredicate(T obj, Function<T, R> nonNullOperator, Supplier<R> nullSupplier) {
        if (Objects.isNull(obj)) return nullSupplier.get();
        return nonNullOperator.apply(obj);
    }
}
