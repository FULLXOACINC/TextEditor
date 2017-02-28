import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


/**
 * Created by alex on 17.2.17.
 */
public class MainWindow {
    private MenuBar menuBar;
    private JScrollPane scrollPanel;
    private JFrame frame;
    private MenuPanel menuPanel;
    private TextPanel textPanel;
    private FileWorker fileWork;

    public MainWindow() {
        frame = new JFrame("TextEditor");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        textPanel = new TextPanel(this);
        scrollPanel = new JScrollPane(textPanel);
        fileWork = new FileWorker(this);
        menuPanel = new MenuPanel(this);
        textPanel.createInput();
        menuBar = new MenuBar(this);
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
