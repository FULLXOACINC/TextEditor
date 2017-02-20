import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by alex on 19.2.17.
 */
public class TextPanel extends JComponent {
    private Caret caret;

    public Caret getCaret() {
        return caret;
    }

    public void changeStyleOnItalic() {
        //Italic -это курсив
    }

    public void changeStyleOnBold() {
        //Bold-жирный текст
    }

    public void changeSizeFont(ActionEvent e) {
    }

    public void changeTypeFont(ActionEvent e) {
    }
}
