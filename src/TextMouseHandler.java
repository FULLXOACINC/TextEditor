import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by alex on 23.2.17.
 */
public class TextMouseHandler extends MouseInputAdapter {

    private Point firstClick;
    private TextPanel textPanel;

    public TextMouseHandler(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void mouseClicked(MouseEvent e) {
        textPanel.click(e.getPoint());
    }

    public void mousePressed(java.awt.event.MouseEvent e){
        firstClick = e.getPoint();
    }

    public void mouseReleased(java.awt.event.MouseEvent e){
        textPanel.click(firstClick, e.getPoint());
    }

    public void mouseDragged(MouseEvent e) {
        textPanel.click(firstClick, e.getPoint());
    }

}