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

    public void openFile() {
        //TODO write open fun
    }

    public void saveFile() {
        //TODO write save fun
    }
}
