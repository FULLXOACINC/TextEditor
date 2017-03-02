package Listeners;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import Window.MainWindow;
import Window.TextPanel;

/**
 * Created by alex on 23.2.17.
 */
public class TextMouseListener extends MouseInputAdapter {

    private Point click;
    private TextPanel textPanel;

    public TextMouseListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void mouseClicked(MouseEvent e) {
        textPanel.click(e.getPoint());
    }

    public void mousePressed(MouseEvent e){
        click = e.getPoint();
    }

    public void mouseReleased(MouseEvent e){
        textPanel.click(click, e.getPoint());
    }

    public void mouseDragged(MouseEvent e) {
        textPanel.click(click, e.getPoint());
    }

}