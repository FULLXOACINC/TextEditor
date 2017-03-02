import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * Created by alex on 19.2.17.
 */
public class Line {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;
    private int maxHight;
    private int maxLength;
    private int coordinateY;
    private int numberLine;
    private final int START_COORDINATE=10;
    private LinkedList<Char> chars = new LinkedList<Char>();

    public Line(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
        maxHight = 15;
        maxLength = 0;
    }

    public LinkedList<Char> getChars(){
        return chars;
    }

    public void add(char ch) {
        chars.add(new Char(ch, mainWindow));
    }

    public void add(Char ch) {
        chars.add(new Char(ch, mainWindow));
    }

    public void add(int i, char ch) {
        chars.add(i, new Char(ch, mainWindow));
    }

    public void remove(int caretX) {
        chars.remove(caretX-1);
    }

    public int indexOf(Char ch) {
        return chars.indexOf(ch);
    }

    public Line copySubLine(int x1, int x2){
        Line newLine = new Line(mainWindow);
        for (int index = x1; index < x2; index++){
            newLine.add(this.chars.get(index));
        }
        return newLine;
    }

    public void removeBack(int x){
        int size = this.size();
        for (int index = size; index > x; index--){
            this.remove(index);
        }
    }

    public void setMaxHight(int now) {
        if (now > maxHight) maxHight = now;
    }

    public int getMaxHight() {
        return maxHight;
    }

    public int size() {
        return chars.size();
    }

    public void setMaxLength(int length) { maxLength = length; }

    public void setCoordinateY(int coordinate) { coordinateY = coordinate; }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setNumberLine(int number) {
        this.numberLine = number;
    }

    public void checkEndLine(Point2D point) {
        if (coordinateY -maxHight <= point.getY()){
            caret.setCaretX(chars.size());
            caret.setCaretY(numberLine);
            if (START_COORDINATE >= point.getX()){
                caret.setCaretX(0);
            }
        }
    }

    public void setMaxHightNumber(int value) { maxHight = value;}

    public void add(String text, String font, String style, String size) {
        chars.add(new Char(text.charAt(0), font, style, size, mainWindow));
    }
}
