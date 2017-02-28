import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by alex on 19.2.17.
 */
public class TextPanel  extends JComponent{
    private MainWindow mainWindow;
    private final int START_COORDINATE=10;
    private Caret caret;
    private int fontStyle;
    private Font panelFont;
    private ArrayList<Line> lines = new ArrayList<Line>();

    public TextPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
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

    public String getFontType(){
        return panelFont.getFontName();
    }

    public int getFontStyles(){
        return fontStyle;
    }

    public int getFontSize(){
        return panelFont.getSize();
    }
    public void setFontSize(int size){
        panelFont = new Font(getFontType(), getFontStyles(), size);
    }
    public void setFontType(String type){
        panelFont = new Font(type, getFontStyles(), getFontSize());
    }

    public void setFontStyles(int style){
        if (fontStyle == Font.PLAIN && style == Font.BOLD)
            fontStyle = Font.BOLD;
         else if (fontStyle == Font.BOLD && style == Font.BOLD)
            fontStyle = Font.PLAIN;
         else if (fontStyle == Font.PLAIN && style == Font.ITALIC)
            fontStyle = Font.ITALIC;
         else if (fontStyle == Font.ITALIC && style == Font.ITALIC)
            fontStyle = Font.PLAIN;
         else if (fontStyle == Font.BOLD && style == Font.ITALIC)
            fontStyle = Font.BOLD + Font.ITALIC;
         else if (fontStyle == Font.ITALIC && style == Font.BOLD)
            fontStyle = Font.BOLD + Font.ITALIC;
         else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.BOLD)
            fontStyle = Font.ITALIC;
         else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.ITALIC)
            fontStyle = Font.BOLD;


        panelFont = new Font(getFontType(), fontStyle, getFontSize());
    }

    public Caret getCaret(){
        return caret;
    }

    public ArrayList<Line> getLines(){
        return lines;
    }

    public void createInput(){
        caret = new Caret(mainWindow);
        new CaretTimer(this);
        Line newLine = new Line(mainWindow);
        lines.add(newLine);
    }

    public void newLine() {
        int lost = caret.getCaretX();
        Line newLine = lines.get(caret.getCaretY()).copySubLine
                (caret.getCaretX(), lines.get(caret.getCaretY()).size());
        lines.get(caret.getCaretY()).removeBack(lost);
        lines.add(caret.getCaretY()+1, newLine);
        caret.setCaretX(0);
        caret.moveCaretToDown();
    }

    public void deleteChar() {
        if (caret.getCaretX()==0 && caret.getCaretY()==0){
        } else if (caret.getCaretX()==0){
            caret.setCaretX(lines.get(caret.getCaretY()-1).size());
            if (lines.get(caret.getCaretY()).size() != 0){
                for (Char ch: lines.get(caret.getCaretY()).getChars()){
                    lines.get(caret.getCaretY()-1).getChars().add(ch);
                }
            }
            lines.remove(caret.getCaretY());
            caret.moveCaretToUP();
        } else{
            lines.get(caret.getCaretY()).remove(caret.getCaretX());
            caret.moveCaretToLeft();
        }
    }

    public void deleteNextChar() {
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

    public void changeSizeFont(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        setFontSize(Integer.parseInt(change));
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontSize(Integer.parseInt(change));
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void changeTypeFont(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        setFontType(change);
        System.out.println(change);
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontType(change);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void changeStyleOnBold(){
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
        mainWindow.updateWindow();
    }

    public void changeStyleOnItalic(){
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
        mainWindow.updateWindow();
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
                    lines.get(caret.getCaretY()).add(caret.getCaretX(), s.charAt(index));
                    caret.moveCaretToRight();
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

    public boolean deleteSelectedText(){
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
        lines.get(caret.getCaretY()).add(caret.getCaretX(), keyChar);
        caret.moveCaretToRight();
    }

    public void click(Point point) {
        for (Line line : lines) {
            line.checkEndLine(point);
            for (Char ch : line.getChars()) {
                if (ch.contains(point)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }


    public void click(Point one, Point two) {
        for (Line line : lines) {
            line.checkEndLine(two);
            for (Char ch : line.getChars()) {
                ch.setIsSelect(ch.contains(one, two));
                if (ch.contains(two)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }


}