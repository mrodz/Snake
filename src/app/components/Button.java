package app.components;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;

public class Button extends JButton {
    public Button(String msg) {
        super(msg);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

    }
}
