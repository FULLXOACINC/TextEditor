package Listeners;

import Window.TextPanel.TextPanel;
import Window.TextPanel.TextPanelModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 22.2.17.
 */
public class DeleteKeyListener implements KeyListener {
    private TextPanelModel textPanelModel;

    public DeleteKeyListener(TextPanel textPanel){
        this.textPanelModel = textPanel.getTextPanelModel();
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
            textPanelModel.backSpaceKey();
        } else if (KeyEvent.VK_DELETE == keyEvent.getKeyCode()) {
            textPanelModel.deleteKey();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
