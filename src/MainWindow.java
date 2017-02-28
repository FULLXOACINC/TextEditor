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
    private MainPanel panel;
    private TextPanel textPanel;
    private FileWorker fileWork;

    public MainWindow() {
        frame = new JFrame("TextEditorPPVIS");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        textPanel = new TextPanel(this);
        scrollPanel = new JScrollPane(textPanel);
        fileWork = new FileWorker(this);
        panel = new MainPanel(this);
        textPanel.createInput();
        menuBar = new MenuBar(this);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.setSize(800,600);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setResizable(false);
        addListener();
    }
    private void addListener(){
        TextMouseHandler textmousehandler = new TextMouseHandler(this);
        textPanel.addMouseListener(textmousehandler);
        textPanel.addMouseMotionListener(textmousehandler);
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
