import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alex on 17.2.17.
 */
public class MenuBar {
    private JFrame frame;
    private FileWorker fileWork;

    public MenuBar(MainWindow mainwindow) {
        this.frame=mainwindow.getFrame();
        fileWork=mainwindow.getFileWork();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        Font font = new Font("Verdana", Font.ITALIC, 12);
        fileMenu.setFont(font);

        JMenuItem newMenu = new JMenuItem("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
        newMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Delete all?",
                        "New",
                        JOptionPane.YES_NO_OPTION);
                if(answer==0)
                    fileWork.newFile();

            }
        });

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setFont(font);
        fileMenu.add(openItem);
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.openFile();
            }
        });

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setFont(font);
        fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.saveFile();
            }
        });

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showOptionDialog(frame,
                        "Would you like to save this document?",
                        "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(answer==2)
                    return;
                if(answer==0){
                    fileWork.saveFile();
                    return;
                }
                System.exit(0);

            }
        });


        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }
}
