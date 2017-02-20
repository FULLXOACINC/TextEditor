import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Collections;

import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;

/**
 * Created by alex on 17.2.17.
 */
public class MainWindow {
    private JFrame frame;
    private MainPanel panel;
    private TextPanel textPanel;

    public MainWindow() {
        frame= new JFrame("Test frame");
        panel = new MainPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Verdana", Font.ITALIC, 12);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Menu");
        fileMenu.setFont(font);

        JMenuItem newMenu = new JMenuItem("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
        newMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click new");
            }
        });

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setFont(font);
        fileMenu.add(openItem);
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click open");
            }
        });

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setFont(font);
        fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("click save");
            }
        });

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.add(panel);
        JPanel textArea=new JPanel();
        textArea.setBackground(new Color(160,160,160));


        JScrollPane scrollPanel = new JScrollPane(textArea);
        scrollPanel.setPreferredSize(new Dimension(100, 500));
        frame.add(scrollPanel, BorderLayout.AFTER_LAST_LINE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static void main(String[] args){
        new MainWindow();
    }


    public TextPanel getTextPanel() {
        return textPanel;
    }
}

