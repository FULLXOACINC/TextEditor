/**
 * Created by alex on 19.2.17.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
/**
 * Created by alex on 17.2.17.
 */
public class MenuPanel {

    private JFrame frame;
    private FileWorker fileWork;
    private TextPanel textPanel;

    public MenuPanel(MainWindow mainwindow) {
        frame=mainwindow.getFrame();
        fileWork=mainwindow.getFileWork();
        textPanel= mainwindow.getTextPanel();

        JToolBar toolBar = new JToolBar();

        toolBar.add(makeButton(new JButton(), "new.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Delete all?",
                        "New",
                        JOptionPane.YES_NO_OPTION);
                if(answer==0)
                    fileWork.newFile();
            }
        }));
        toolBar.add(makeButton(new JButton(), "save.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fileWork.saveFile();
            }
        }));
        toolBar.add(makeButton(new JButton(), "open.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.openFile();
            }
        }));

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



        String [] allFontTypes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontBox = new JComboBox(allFontTypes);
        fontBox.setSelectedIndex(Arrays.asList(allFontTypes).indexOf("Liberation Serif"));
        fontBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeTypeFont(e);
            }
        });
        toolBar.add(fontBox);
        String[] sizeFont = {"10", "20", "30", "40", "50"};
        JComboBox sizeBox = new JComboBox(sizeFont);
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeSizeFont(e);
            }
        });
        toolBar.add(sizeBox);
        frame.add(toolBar,BorderLayout.PAGE_START);


    }
    private JButton makeButton(JButton button, String imgString, ActionListener action){
        button.addActionListener(action);
        ImageIcon img = new ImageIcon("img/"+imgString);
        button.setIcon(img);
        return button;
    }

}