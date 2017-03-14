package Listeners;

import Window.TextPanel.TextPanel;
import Window.TextPanel.TextPanelModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 24.2.17.
 */
public class CtrlKeyListener implements KeyListener {
    private TextPanelModel textPanelModel;

    public CtrlKeyListener(TextPanel textPanel){
        this.textPanelModel = textPanel.getTextPanelModel();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            textPanelModel.copy();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
            textPanelModel.cut();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
            textPanelModel.paste();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
