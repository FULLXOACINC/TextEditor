package Window;

import TextObjects.Caret;
import TextObjects.Char;
import TextObjects.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * Created by alex on 19.2.17.
 */
public class TextPanel  extends JComponent{
    private final int START_COORDINATE=10;
    private Caret caret;
    private int fontStyle;
    private Font panelFont;

    private List<Line> lines = new ArrayList<Line>();

    public TextPanel(){
        fontStyle=Font.PLAIN;
        panelFont = new Font("Liberation Serif",fontStyle,10);
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        for (Line line: lines) {
            line.setMaxHightNumber(0);
            for (Char ch : line.getChars()) {
                Font font = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(font);
                FontMetrics fm = graphics2d.getFontMetrics();
                line.setMaxHight(fm.getHeight());
            }
            if (line.getMaxHight() == 0) line.setMaxHightNumber(15);
        }
        int y=START_COORDINATE;
        int xMax = 0;
        int lineY=-1;
        for (Line line: lines) {
            y += line.getMaxHight();
            int x=START_COORDINATE;
            lineY++;
            int letterX=0;
            for (Char ch: line.getChars()) {
                letterX++;
                graphics2d.setColor(Color.BLACK);
                Font font = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(font);
                FontMetrics fm =  graphics2d.getFontMetrics();
                if (ch.getIsSelect()) {
                    graphics2d.setColor(Color.BLACK);
                    Rectangle2D rect = new Rectangle
                            (x-2, y-line.getMaxHight()+2, fm.stringWidth(ch.getStringCh())+3, line.getMaxHight());
                    graphics2d.fill(rect);
                    graphics2d.setColor(Color.WHITE);
                }
                graphics2d.drawString(ch.getStringCh(),x,y);
                ch.setHeight(fm.getHeight());
                ch.setWight(fm.stringWidth(ch.getStringCh()));
                ch.setX(x);
                ch.setY(y);
                ch.setNumberLine(lineY);
                x += fm.stringWidth(ch.getStringCh())+1;
                if (caret.getCaretX() == letterX && caret.getCaretY()  == lineY){
                    caret.setCaretCoordinateX(x);
                    caret.setCaretCoordinateY(y);
                }
            }
            line.setMaxLength(x);
            line.setCoordinateY(y);
            line.setNumberLine(lineY);
            xMax = xMax < x ? x : xMax;
            if (caret.getCaretX() == 0 && caret.getCaretY() == lineY){
                caret.setCaretCoordinateX(START_COORDINATE);
                caret.setCaretCoordinateY(y);
            }
        }
        setPreferredSize(new Dimension(xMax + 50, y + 50));
    }

    public Font getFont() {
        return panelFont;
    }

    private String getFontType(){
        return panelFont.getFontName();
    }

    private int getFontStyles(){
        return fontStyle;
    }

    private int getFontSize(){
        return panelFont.getSize();
    }

    private void setFontSize(int size){
        panelFont = new Font(getFontType(), getFontStyles(), size);
    }

    private void setFontType(String type){
        panelFont = new Font(type, getFontStyles(), getFontSize());
    }

    public Caret getCaret(){
        return caret;
    }

    public List<Line> getLines(){
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    void createInput(){
        caret = new Caret();
        CaretTimer(this);
        Line newLine = new Line();
        lines.add(newLine);
    }

    private void newLine() {
        int lost = caret.getCaretX();
        Line newLine = lines.get(caret.getCaretY()).copySubLine
                (caret.getCaretX(), lines.get(caret.getCaretY()).size());
        lines.get(caret.getCaretY()).removeBack(lost);
        lines.add(caret.getCaretY()+1, newLine);
        caret.setCaretX(0);
        moveCaretToDown();
    }

    private void deleteChar() {
        if (caret.getCaretX()==0 && caret.getCaretY()==0){
        } else if (caret.getCaretX()==0){
            caret.setCaretX(lines.get(caret.getCaretY()-1).size());
            if (lines.get(caret.getCaretY()).size() != 0){
                for (Char ch: lines.get(caret.getCaretY()).getChars()){
                    lines.get(caret.getCaretY()-1).getChars().add(ch);
                }
            }
            lines.remove(caret.getCaretY());
            moveCaretToUP();
        } else{
            lines.get(caret.getCaretY()).remove(caret.getCaretX());
            moveCaretToLeft();
        }
    }

    private void deleteNextChar() {
        if (caret.getCaretX()==lines.get(caret.getCaretY()).getChars().size() &&
                caret.getCaretY()==lines.size()-1){
        } else if (caret.getCaretX()==lines.get(caret.getCaretY()).getChars().size()){
            if (lines.get(caret.getCaretY()+1).size() != 0){
                for (Char ch: lines.get(caret.getCaretY()+1).getChars()){
                    lines.get(caret.getCaretY()).getChars().add(ch);
                }
            }
            lines.remove(caret.getCaretY()+1);
        } else{
            lines.get(caret.getCaretY()).remove(caret.getCaretX()+1);
        }
    }

    void changeSizeFont(int size){

        setFontSize(size);
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontSize(size);
                }
            }
        }

    }

    void changeTypeFont(String type){

        setFontType(type);
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontType(type);
                }
            }
        }
    }

    void changeStyleOnBold(){
        boolean bold = false, notBold = false;

        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    if (ch.getFontStyles() == Font.PLAIN || ch.getFontStyles() == Font.ITALIC) {
                        notBold = true;
                    }
                    if (ch.getFontStyles() == Font.BOLD || ch.getFontStyles() == Font.BOLD + Font.ITALIC){
                        bold = true;
                    }
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    if (bold && notBold)
                        ch.setNormalizationBold();
                    else
                        ch.setFontStyles(Font.BOLD);
                }
            }
        }

    }

    void changeStyleOnItalic(){
        boolean italic = false, notItalic = false;
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    if (ch.getFontStyles() == Font.PLAIN || ch.getFontStyles() == Font.BOLD) {
                        notItalic = true;
                    }
                    if (ch.getFontStyles() == Font.ITALIC || ch.getFontStyles() == Font.ITALIC + Font.BOLD) {
                        italic = true;
                    }
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    if (italic && notItalic) ch.setNormalizationItalic();
                    else ch.setFontStyles(Font.ITALIC);
                }
            }
        }
    }

    public void copy() {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.getChars())
                if (ch.getIsSelect()) string += ch.getStringCh();
            if (line.getChars().size()-1 >= 0 && line.getChars().get(line.getChars().size()-1).getIsSelect())
                string += "\n";
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't copy text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void paste() {
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String s = (String) c.getData(DataFlavor.stringFlavor);
            for (int index = 0; index < s.length(); index++){
                if (s.charAt(index) == '\n') {
                    newLine();
                } else {
                    lines.get(caret.getCaretY()).add(caret.getCaretX(), s.charAt(index),panelFont);
                    moveCaretToRight();
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't past text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
        deleteSelectedText();
    }

    public void cut() {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect())
                    string += ch.getStringCh();
            }
            if (line.getChars().size()-1 >= 0 && line.getChars().get(line.getChars().size()-1).getIsSelect()){
                string += "\n";
            }
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't cut text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
        deleteSelectedText();
    }

    private boolean deleteSelectedText(){
        boolean setCaret = true;
        for (int y = lines.size() - 1; y >= 0; y--) {
            if (!setCaret && caret.getCaretX() == 0) deleteChar();
            for (int x = lines.get(y).getChars().size() - 1; x >= 0; x--) {
                if (lines.get(y).getChars().get(x).getIsSelect()) {
                    if (setCaret) {
                        caret.setCaretX(x + 1);
                        caret.setCaretY(y);
                        setCaret = false;
                    }
                    deleteChar();
                }
            }
        }
        return setCaret;
    }

    public boolean deleteAllText(){
        boolean setCaret = true;
        for (int y = lines.size() - 1; y >= 0; y--) {
            if (!setCaret && caret.getCaretX() == 0) deleteChar();
            for (int x = lines.get(y).getChars().size() - 1; x >= 0; x--) {
                if (setCaret) {
                    caret.setCaretX(x + 1);
                    caret.setCaretY(y);
                    setCaret = false;
                }
                deleteChar();
            }
        }
        return setCaret;
    }

    public void falseAllSelection(){
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                ch.setIsSelect(false);
            }
        }
    }

    public void backSpaceKey(){
        if (deleteSelectedText()) deleteChar();
    }

    public void deleteKey(){
        if (deleteSelectedText()) deleteNextChar();
    }

    public void enterKey(){
        deleteSelectedText();
        newLine();
    }

    public void inputCharKey(char keyChar){
        deleteSelectedText();
        lines.get(caret.getCaretY()).add(caret.getCaretX(), keyChar,panelFont);
        moveCaretToRight();
    }

    public void click(Point point) {
        for (Line line : lines) {
            checkEndLine(point,line);
            for (Char ch : line.getChars()) {
                if (contains(point,ch)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
    }

    public void click(Point one, Point two) {
        for (Line line : lines) {
            checkEndLine(two,line);
            for (Char ch : line.getChars()) {
                ch.setIsSelect(contains(one, two,ch));
                if (contains(two,ch)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
    }

    private void drawCaret() {
        int caretCoordinateX = caret.getCaretCoordinateX();
        int caretCoordinateY = caret.getCaretCoordinateY();
        Graphics2D graphics2d = (Graphics2D) this.getGraphics();
        Font font = this.getFont();
        graphics2d.setFont(font);
        FontMetrics fm =  graphics2d.getFontMetrics();
        graphics2d.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fm.getHeight()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2d.setColor(this.getBackground());
        graphics2d.drawLine (caretCoordinateX, caretCoordinateY, caretCoordinateX,caretCoordinateY-(int)(0.6*fm.getHeight()));
    }

    public Point followCaret(int width) {
        int x = 0;
        if (this.getCaret().getCaretCoordinateX() > width)
            x = this.getCaret().getCaretCoordinateX();

        int y = this.getCaret().getCaretCoordinateY() -
                this.getLines().get(this.getCaret().getCaretY()).getMaxHight();
        return new Point(x, y);
    }

    private void checkEndLine(Point2D point,Line line) {
        boolean b = line.getCoordinateY() -line.getMaxHight() <= point.getY();
        if (b){
            caret.setCaretX(line.getChars().size());
            caret.setCaretY(line.getNumberLine());
            if (START_COORDINATE >= point.getX()){
                caret.setCaretX(0);
            }
        }
    }

    private boolean contains(Point2D point,Char ch){
        int y=ch.getY();
        int x=ch.getX();
        return (x <= point.getX() && x+ch.getWight() >= point.getX() && y-ch.getHeight()  <= point.getY() && y >= point.getY());
    }

    private boolean contains(Point one, Point two,Char ch) {
        int y=ch.getY();
        int x=ch.getX();
        int height = lines.get(ch.getNumberLine()).getMaxHight();
        Point upPoint = (one.getY() < two.getY()) ? one : two;
        Point downPoint = (one.getY() < two.getY()) ? two : one;
        Point leftPoint = (one.getX() < two.getX()) ? one : two;
        Point rightPoint = (one.getX() < two.getX()) ? two : one;
        if (y < downPoint.getY() || y-height > upPoint.getY()) {
            return ((x >= upPoint.getX()) && y - height < upPoint.getY() && y >= upPoint.getY() ||
                    (x <= downPoint.getX()) && y - height < downPoint.getY() && y >= downPoint.getY() ||
                    (y - height >= upPoint.getY() && y < downPoint.getY()));
        } else {
            return (y-height <= leftPoint.getY() && y >= leftPoint.getY()) &&
                    (y-height <= rightPoint.getY() && y >= rightPoint.getY()) &&
                    (x > leftPoint.getX() && x <= rightPoint.getX());
        }
    }

    private void CaretTimer(TextPanel textPanel) {
        final java.util.Timer time = new java.util.Timer();
        time.schedule(new TimerTask() {
            public void run() {
                textPanel.drawCaret();
            }
        }, 500, 1000);
    }

    public void moveCaretToRight() {
        boolean isTextEnd = lines.get(caret.getCaretY()).size() == caret.getCaretX() && lines.size() == caret.getCaretY() + 1;
        boolean isLineNotEnd = lines.get(caret.getCaretY()).size() > caret.getCaretX();
        boolean isCanMoveDown= caret.getCaretY() < lines.size() - 1;

        if (isTextEnd) {
            return;
        } else if (isLineNotEnd) {
            caret.setCaretX(caret.getCaretX()+1);
        } else if (isCanMoveDown) {
            caret.setCaretY(caret.getCaretY()+1);
            caret.setCaretX(0);
        }
    }

    public void moveCaretToDown() {
        boolean isLinesNotEnds=caret.getCaretY() < lines.size() - 1;
        boolean isNextLineLess=lines.get(caret.getCaretY()).size() < caret.getCaretX();

        if (isLinesNotEnds) {
            caret.setCaretY(caret.getCaretY()+1);
            if (isNextLineLess) {
                caret.setCaretX(lines.get(caret.getCaretY()).size());
            }
        }
    }

    public void moveCaretToLeft() {
        if (caret.getCaretX() == 0 && caret.getCaretY() == 0)
            return;
        else
        if (caret.getCaretX() != 0)
            caret.setCaretX(caret.getCaretX()-1);
        else {
            moveCaretToUP();
            caret.setCaretX(lines.get(caret.getCaretY()).size());
        }
    }

    public void moveCaretToUP() {
        if (caret.getCaretY() != 0) {
            caret.setCaretY(caret.getCaretY()-1);
            if (lines.get(caret.getCaretY()).size() < caret.getCaretX())
                caret.setCaretX(lines.get(caret.getCaretY()).size());
        }
    }



}