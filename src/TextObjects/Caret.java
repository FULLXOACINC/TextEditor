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

    public Caret(MainWindow mainWindow) {
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

    public void moveCaretToRight(TextPanel textPanel) {
        boolean isTextEnd = textPanel.getLines().get(getCaretY()).size() == caretX && textPanel.getLines().size() == caretY + 1;
        boolean isLineNotEnd = textPanel.getLines().get(getCaretY()).size() > caretX;
        boolean isCanMoveDown= caretY < textPanel.getLines().size() - 1;

        if (isTextEnd) {
            return;
        } else if (isLineNotEnd) {
            caretX++;
        } else if (isCanMoveDown) {
            caretY++;
            setCaretX(0);
        }
    }

    public void moveCaretToDown(TextPanel textPanel) {
        boolean isLinesNotEnds=caretY < textPanel.getLines().size() - 1;
        boolean isNextLineLess=textPanel.getLines().get(getCaretY()).size() < caretX;

        if (isLinesNotEnds) {
            caretY++;
            if (isNextLineLess) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void moveCaretToLeft(TextPanel textPanel) {
        if (caretX == 0 && caretY == 0)
            return;
        else
            if (caretX != 0)
            caretX--;
            else {
                moveCaretToUP(textPanel);
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
    }

    public void moveCaretToUP(TextPanel textPanel) {
        if (caretY != 0) {
            caretY--;
            if (textPanel.getLines().get(getCaretY()).size() < caretX)
                setCaretX(textPanel.getLines().get(getCaretY()).size());
        }
    }





}