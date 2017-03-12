package TextObjects;


import Window.MainWindow;
import Window.TextPanel;

/**
 * Created by alex on 19.2.17.
 */
public class Caret {

    private int caretX;
    private int caretY;
    private int caretCoordinateX;
    private int caretCoordinateY;

    public Caret() {
        caretX = 0;
        caretY = 0;
    }

    public int getCaretX() {
        return caretX;
    }

    public void setCaretX(int x) {
        caretX = x;
    }

    public int getCaretY() {
        return caretY;
    }

    public void setCaretY(int y) {
        caretY = y;
    }

    public int getCaretCoordinateX() {
        return caretCoordinateX;
    }

    public void setCaretCoordinateX(int x) {
        caretCoordinateX = x;
    }

    public int getCaretCoordinateY() {
        return caretCoordinateY;
    }

    public void setCaretCoordinateY(int y) {
        caretCoordinateY = y;
    }






}