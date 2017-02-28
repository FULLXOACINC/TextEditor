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

    public int getCaretCordinateX() {
        return caretCoordinateX;
    }

    public void setCaretCordinateX(int x) {
        caretCoordinateX = x;
    }

    public int getCaretCordinateY() {
        return caretCoordinateY;
    }

    public void setCaretCordinateY(int y) {
        caretCoordinateY = y;
    }

    public void incrementCaretX() {
        if (textPanel.getLines().get(getCaretY()).size() == caretX && textPanel.getLines().size() == caretY + 1) {
        } else if (textPanel.getLines().get(getCaretY()).size() > caretX) {
            caretX++;
        } else if (caretY < textPanel.getLines().size() - 1) {
            caretY++;
            setCaretX(0);
        }
    }

    public void incrementCaretY() {
        if (caretY < textPanel.getLines().size() - 1) {
            caretY++;
            if (textPanel.getLines().get(getCaretY()).size() < caretX) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void decrementCaretX() {
        if (caretX == 0 && caretY == 0) {
        } else if (caretX != 0) {
            caretX--;
        } else {
            decrementCaretY();
            setCaretX(textPanel.getLines().get(getCaretY()).size());
        }
    }

    public void decrementCaretY() {
        if (caretY != 0) {
            caretY--;
            if (textPanel.getLines().get(getCaretY()).size() < caretX) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void drawCaret() {
        int caretCordinateX = getCaretCordinateX();
        int caretCordinateY = getCaretCordinateY();
        Graphics2D graphics2d = (Graphics2D) textPanel.getGraphics();
        Font font = textPanel.getFont();
        graphics2d.setFont(font);
        FontMetrics fm =  graphics2d.getFontMetrics();
        graphics2d.drawLine (caretCordinateX, caretCordinateY, caretCordinateX,caretCordinateY-(int)(0.6*fm.getHeight()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2d.setColor(textPanel.getBackground());
        graphics2d.drawLine (caretCordinateX, caretCordinateY, caretCordinateX,caretCordinateY-(int)(0.6*fm.getHeight()));
    }



    void followCaret() {
        int x = 0;
        if (textPanel.getCaret().getCaretCordinateX() > frame.getWidth()) {
            x = textPanel.getCaret().getCaretCordinateX();
        }
        int y = textPanel.getCaret().getCaretCordinateY() -
                textPanel.getLines().get(textPanel.getCaret().getCaretY()).getMaxHight();
        JViewport scrollP = scrollPanel.getViewport();
        scrollP.setViewPosition(new Point(x, y));
        scrollPanel.setViewport(scrollP);
    }

    public int returnSelectionCordinateCaretX() {
        if (isFirstChar()) {
            return 10;
        } else {
            return textPanel.getLines().get(getCaretY()).getChars().get(getCaretX() - 1).getX() + 1;
        }
    }

    public int returnSelectionCordinateCaretY() {
        final Line line = textPanel.getLines().get(getCaretY());
        if (isLineEmpty()) {
            return line.getCordinateY();
        } else {
            return line.getChars().get(getCaretX() - 1).getY() - 1;
        }
    }

    private boolean isFirstChar() {
        return getCaretX() == 0 || textPanel.getLines().get(getCaretY()).getChars().size() == 1;
    }

    private boolean isLineEmpty() {
        return getCaretX() == 0 || textPanel.getLines().get(getCaretY()).getChars().size() == 0;
    }

}