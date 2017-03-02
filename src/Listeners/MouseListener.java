package Listeners;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import Window.MainWindow;
import Window.TextPanel;

/**
 * Created by alex on 21.2.17.
 */
public class MouseListener extends MouseInputAdapter {

    private Point firstClick;
    private TextPanel textPanel;

    public MouseListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void mouseClicked(MouseEvent e) {
        textPanel.click(firstClick, e.getPoint());
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