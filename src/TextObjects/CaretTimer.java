package TextObjects;

import TextObjects.Caret;
import Window.TextPanel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alex on 19.2.17.
 */
public class CaretTimer {

    private Caret caret;

    public CaretTimer(TextPanel textPanel) {
        caret = textPanel.getCaret();
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                caret.drawCaret();
            }
        }, 500, 1000);
    }

}