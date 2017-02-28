import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 24.2.17.
 */
public class CtrlKeyListener implements KeyListener {
    private TextPanel textPanel;

    public CtrlKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            textPanel.copy();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
            textPanel.cut();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
            textPanel.paste();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
