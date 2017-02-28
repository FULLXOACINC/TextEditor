import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by alex on 25.2.17.
 */
public class MainWindowListener implements WindowListener {
    private JFrame frame;
    private FileWorker fileWork;

    public MainWindowListener(MainWindow mainWindow){
        frame=mainWindow.getFrame();
        fileWork=mainWindow.getFileWork();
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
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

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
