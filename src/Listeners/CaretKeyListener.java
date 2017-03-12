package Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import TextObjects.Caret;
import Window.MainWindow;
import Window.TextPanel;

/**
 * Created by alex on 19.2.17.
 */
public class CaretKeyListener implements KeyListener {

    private TextPanel textPanel;

    public CaretKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            textPanel.moveCaretToLeft();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            textPanel.moveCaretToRight();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            textPanel.moveCaretToUP();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            textPanel.moveCaretToDown();
            textPanel.falseAllSelection();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}
