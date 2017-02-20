import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by alex on 20.2.17.
 */
public class ControlKeyListener implements KeyListener {

    private TextPanel textPanel;

    public ControlKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void keyPressed(KeyEvent keyEvent) {

    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}