import javax.swing.*;
import java.awt.*;

/**
 * Created by alex on 19.2.17.
 */
public class Caret {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private JFrame frame;
    private JScrollPane scrollPanel;
    private int caretX;
    private int caretY;
    private int caretCoordinateX;
    private int caretCoordinateY;

    public Caret(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        frame = mainWindow.getFrame();
        scrollPanel = mainWindow.getScrollPanel();
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

    public void moveCaretToRight() {
        boolean isTextEnd = textPanel.getLines().get(getCaretY()).size() == caretX && textPanel.getLines().size() == caretY + 1;
        boolean isLineEnd =caretY < textPanel.getLines().size() - 1;

        if (isTextEnd)
            return;
        else if (isLineEnd) {
            caretY++;
            setCaretX(0);
        }
        else
            caretX++;

    }

    public void moveCaretToDown() {
        boolean isLineNotEnds =caretY < textPanel.getLines().size() - 1;
        boolean isNextLineMoreThenThisLine =textPanel.getLines().get(getCaretY()).size() < caretX;

        if (isLineNotEnds) {
            caretY++;
            if (isNextLineMoreThenThisLine) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void moveCaretToLeft() {
        boolean isTextDocStart = caretX == 0 && caretY == 0;

        if (isTextDocStart)
            return;
        else
            if (caretX != 0)
            caretX--;
            else {
                moveCaretToUP();
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
    }

    public void moveCaretToUP() {
        boolean b =textPanel.getLines().get(getCaretY()).size() < caretX;

        if (caretY != 0) {
            caretY--;
            if (b)
                setCaretX(textPanel.getLines().get(getCaretY()).size());
        }
    }

    public void drawCaret() {
        int caretCoordinateX = getCaretCoordinateX();
        int caretCoordinateY = getCaretCoordinateY();
        Graphics2D graphics2d = (Graphics2D) textPanel.getGraphics();
        Font font = textPanel.getFont();
        graphics2d.setFont(font);
        FontMetrics fm =  graphics2d.getFontMetrics();
        graphics2d.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fm.getHeight()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2d.setColor(textPanel.getBackground());
        graphics2d.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fm.getHeight()));
    }

    public void followCaret() {
        int x = 0;
        boolean isCaretCoordinateXMoreThenDocWidth =textPanel.getCaret().getCaretCoordinateX() > frame.getWidth();

        if (isCaretCoordinateXMoreThenDocWidth) {
            x = textPanel.getCaret().getCaretCoordinateX();
        }
        int y = textPanel.getCaret().getCaretCoordinateY() -
                textPanel.getLines().get(textPanel.getCaret().getCaretY()).getMaxHight();
        JViewport scrollP = scrollPanel.getViewport();
        scrollP.setViewPosition(new Point(x, y));
        scrollPanel.setViewport(scrollP);
    }


}