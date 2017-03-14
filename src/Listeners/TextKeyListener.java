package Listeners;


import Window.MainWindow;
import Window.TextPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 22.2.17.
 */
public class TextKeyListener implements KeyListener {

    private MainWindow mainWindow;
    private TextPanel textPanel;

    private final int KEY_BACK_SPACE = 8;
    private final int KEY_ENTER = 10;
    private final int KEY_ESC = 27;
    private final int KEY_DELETE = 127;

    public TextKeyListener(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
            textPanel.enterKey();
        }
        int width=mainWindow.getFrame().getWidth();
        Point point=textPanel.followCaret(width);
        JViewport scrollP = mainWindow.getScrollPanel().getViewport();
        scrollP.setViewPosition(point);
        mainWindow.getScrollPanel().setViewport(scrollP);
        mainWindow.updateWindow();
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
        if (!keyEvent.isControlDown() && !checkSystemKey(keyEvent)) {
            textPanel.inputCharKey(keyEvent.getKeyChar());
        }
    }

    private boolean checkSystemKey(KeyEvent keyEvent){
        return ((int)keyEvent.getKeyChar() == KEY_ESC ||
                (int)keyEvent.getKeyChar() == KEY_BACK_SPACE ||
                (int)keyEvent.getKeyChar() == KEY_DELETE ||
                (int)keyEvent.getKeyChar() == KEY_ENTER);
    }
}