package app;

import app.components.ApplicationWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Runtime {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(ApplicationWindow::new);
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
