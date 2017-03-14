package Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Window.TextPanel.TextPanel;
import Window.TextPanel.TextPanelModel;

/**
 * Created by alex on 19.2.17.
 */
public class CaretKeyListener implements KeyListener {
    private TextPanelModel textPanelModel;

    public CaretKeyListener(TextPanel textPanel){
        this.textPanelModel = textPanel.getTextPanelModel();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            textPanelModel.moveCaretToLeft();
            textPanelModel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            textPanelModel.moveCaretToRight();
            textPanelModel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            textPanelModel.moveCaretToUP();
            textPanelModel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            textPanelModel.moveCaretToDown();
            textPanelModel.falseAllSelection();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}
