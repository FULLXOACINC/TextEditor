package Listeners;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import Window.MainWindow;
import Window.TextPanel.TextPanel;
import Window.TextPanel.TextPanelModel;

/**
 * Created by alex on 23.2.17.
 */
public class TextMouseListener extends MouseInputAdapter {

    private Point click;
    private MainWindow mainWindow;
    private TextPanelModel textPanelModel;

    public TextMouseListener(MainWindow mainWindow){
        this.mainWindow=mainWindow;
        this.textPanelModel = mainWindow.getTextPanel().getTextPanelModel();
    }

    public void mouseClicked(MouseEvent e) {
        textPanelModel.click(e.getPoint());
        mainWindow.updateWindow();
    }

    public void mousePressed(MouseEvent e){
        click = e.getPoint();
    }

    public void mouseReleased(MouseEvent e){
        textPanelModel.click(click, e.getPoint());
        mainWindow.updateWindow();
    }

    public void mouseDragged(MouseEvent e) {
        textPanelModel.click(click, e.getPoint());
        mainWindow.updateWindow();
    }

}