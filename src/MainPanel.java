/**
 * Created by alex on 19.2.17.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

public class MainPanel extends JPanel {

    private FileWorker fileWork;
    private TextPanel textPanel;

    public MainPanel() {
        JToolBar toolBar = new JToolBar();
        setLayout(new BorderLayout());

        add(toolBar, BorderLayout.PAGE_START);
        toolBar.add(makeButton(new JButton(), "tool-save.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fileWork.saveFile();
            }
        }));
        toolBar.add(makeButton(new JButton(), "tool-open.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.openFile();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(makeButton(new JButton(), "B.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnBold();
            }
        }));
        toolBar.add(makeButton(new JButton(), "I.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnItalic();
            }
        }));
        toolBar.addSeparator();
        String[] sizeFont = {"12", "14", "16", "18", "22", "24", "32", "36", "40", "48"};
        JComboBox sizeBox = new JComboBox(sizeFont);
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeSizeFont(e);
            }
        });
        toolBar.add(sizeBox);
        toolBar.addSeparator();
        String [] fontType = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontBox = new JComboBox(fontType);
        fontBox.setMaximumSize(fontBox.getPreferredSize());
        fontBox.setSelectedIndex(Arrays.asList(fontType).indexOf(Font.MONOSPACED));
        fontBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeTypeFont(e);
            }
        });
        toolBar.add(fontBox);

    }
    private JButton makeButton(JButton button, String imgString, ActionListener action){
        button.addActionListener(action);
        ImageIcon img = new ImageIcon("img/"+imgString);
        button.setIcon(img);
        return button;
    }

}