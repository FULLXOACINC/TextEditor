package Window;

import Listeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;


/**
 * Created by alex on 17.2.17.
 */
public class MainWindow {
    private JMenuBar menuBar;
    private JScrollPane scrollPanel;
    private JFrame frame;
    private JToolBar menuPanel;
    private TextPanel textPanel;
    private FileWorker fileWork;

    public MainWindow() {
        frame = new JFrame("TextEditor");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        textPanel = new TextPanel(this);
        scrollPanel = new JScrollPane(textPanel);
        fileWork = new FileWorker(this);
        menuPanel = createMenuPanel();
        textPanel.createInput(this);

        menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
        frame.add(menuPanel,BorderLayout.PAGE_START);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setResizable(true);
        TextMouseListener textMouseListener = new TextMouseListener(this);
        textPanel.addMouseListener(textMouseListener);
        textPanel.addMouseMotionListener(textMouseListener);
        scrollPanel.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        frame.addWindowListener(new MainWindowListener(this));
        frame.addKeyListener(new CaretKeyListener(this));
        frame.addKeyListener(new CtrlKeyListener(this));
        frame.addKeyListener(new DeleteKeyListener(this));
        frame.addKeyListener(new TextKeyListener(this));
    }

    private JToolBar createMenuPanel() {
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
                updateWindow();

            }
        }));
        toolBar.add(makeButton(new JButton(), "I.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                textPanel.changeStyleOnItalic();
                updateWindow();
            }
        }));



        String [] allFontTypes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontBox = new JComboBox(allFontTypes);
        fontBox.setSelectedIndex(Arrays.asList(allFontTypes).indexOf("Liberation Serif"));
        fontBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String fontType = (String) cb.getSelectedItem();
                textPanel.changeTypeFont(fontType);

                updateWindow();
            }
        });
        toolBar.add(fontBox);
        String[] sizeFont = {"10", "20", "30", "40", "50"};
        JComboBox sizeBox = new JComboBox(sizeFont);
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String change = (String) cb.getSelectedItem();
                int size=Integer.parseInt(change);
                textPanel.changeSizeFont(size);

                updateWindow();
            }
        });
        toolBar.add(sizeBox);
        return toolBar;


    }
    private JButton makeButton(JButton button, String imgString, ActionListener action){
        button.addActionListener(action);
        ImageIcon img = new ImageIcon("img/"+imgString);
        button.setIcon(img);
        return button;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        Font font = new Font("Verdana", Font.ITALIC, 12);
        fileMenu.setFont(font);

        JMenuItem newMenu = new JMenuItem("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
        newMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Del all?",
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

        JMenu edit = new JMenu("Edit");
        edit.setFont(font);

        JMenuItem copy = new JMenuItem("Copy");
        copy.setFont(font);
        edit.add(copy);
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.copy();
                updateWindow();

            }
        });

        JMenuItem paste = new JMenuItem("Paste");
        paste.setFont(font);
        edit.add(paste);
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.paste();
                updateWindow();

            }
        });

        JMenuItem cut = new JMenuItem("Cut");
        cut.setFont(font);
        edit.add(cut);
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.cut();
                updateWindow();

            }
        });

        menuBar.add(edit);

        JMenu formatter = new JMenu("Format");
        formatter.setFont(font);

        JMenuItem bold = new JMenuItem("Bold");
        bold.setFont(font);
        formatter.add(bold);
        bold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnBold();
                updateWindow();
            }
        });

        JMenuItem italic = new JMenuItem("Italic");
        italic.setFont(font);
        formatter.add(italic);
        italic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnItalic();
                updateWindow();
            }
        });

        JMenu size = new JMenu("Size");
        size.setFont(font);
        formatter.add(size);

        JMenuItem tenSize = new JMenuItem("10");
        tenSize.setFont(font);
        size.add(tenSize);
        tenSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeSizeFont(10);
                updateWindow();
            }
        });

        JMenuItem fiftySize = new JMenuItem("50");
        fiftySize.setFont(font);
        size.add(fiftySize);
        fiftySize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeSizeFont(50);
                updateWindow();
            }
        });

        JMenu textFont = new JMenu("Font");
        textFont.setFont(font);
        formatter.add(textFont);

        JMenuItem liberSerif = new JMenuItem("Liberation Serif");
        liberSerif.setFont(font);
        textFont.add(liberSerif);
        liberSerif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeTypeFont("Liberation Serif");
                updateWindow();
            }
        });

        JMenuItem monospaned = new JMenuItem("Monospaned");
        monospaned.setFont(font);
        textFont.add(monospaned);
        monospaned.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeTypeFont("Monospaned");
                updateWindow();
            }
        });

        menuBar.add(formatter);

        return menuBar;
    }

    public void updateWindow() {
        scrollPanel.revalidate();
        scrollPanel.repaint();
        frame.requestFocus();
    }

    public TextPanel getTextPanel(){
        return textPanel;
    }
    public JScrollPane getScrollPanel(){
        return scrollPanel;
    }
    public FileWorker getFileWork() {
        return fileWork;
    }
    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args){
        new MainWindow();
    }

}
