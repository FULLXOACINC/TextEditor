import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 19.2.17.
 */
public class CaretKeyListener implements KeyListener {

    private Caret caret;
    private TextPanel textPanel;

    public CaretKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            caret.decrementCaretX();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            caret.incrementCaretX();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            caret.decrementCaretY();
            textPanel.falseAllSelection();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            caret.incrementCaretY();
            textPanel.falseAllSelection();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}
