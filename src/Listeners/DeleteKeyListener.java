package Listeners;

import Window.MainWindow;
import Window.TextPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 22.2.17.
 */
public class DeleteKeyListener implements KeyListener {
    private TextPanel textPanel;

    public DeleteKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
            textPanel.backSpaceKey();
        } else if (KeyEvent.VK_DELETE == keyEvent.getKeyCode()) {
            textPanel.deleteKey();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
