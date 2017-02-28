import javax.swing.*;

/**
 * Created by alex on 20.2.17.
 */
public class FileWorker {
    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;

    public FileWorker(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
    }

    public void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //TODO open file method
        }
    }

    public void saveFile(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                //TODO save file method
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }



    public void newFile() {
        textPanel.deleteAllText();
        mainWindow.updateWindow();
    }

}
