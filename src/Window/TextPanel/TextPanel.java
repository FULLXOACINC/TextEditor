package Window.TextPanel;

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
    private TextPanelModel textPanelModel;


    public TextPanel(){
        textPanelModel = new TextPanelModel(this);
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
//        Caret caret = textPanelModel.getCaret();
        for (Line line: textPanelModel.getLines()) {
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
        for (Line line: textPanelModel.getLines()) {
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
                if (textPanelModel.getCaret().getCaretX() == letterX && textPanelModel.getCaret().getCaretY()  == lineY){
                    textPanelModel.getCaret().setCaretCoordinateX(x);
                    textPanelModel.getCaret().setCaretCoordinateY(y);
                }
            }
            line.setMaxLength(x);
            line.setCoordinateY(y);
            line.setNumberLine(lineY);
            xMax = xMax < x ? x : xMax;
            if (textPanelModel.getCaret().getCaretX() == 0 && textPanelModel.getCaret().getCaretY() == lineY){
                textPanelModel.getCaret().setCaretCoordinateX(START_COORDINATE);
                textPanelModel.getCaret().setCaretCoordinateY(y);
            }
        }
        setPreferredSize(new Dimension(xMax + 50, y + 50));
    }

    void drawCaret() {
        Caret caret = textPanelModel.getCaret();
        int caretCoordinateX = caret.getCaretCoordinateX();
        int caretCoordinateY = caret.getCaretCoordinateY();
        Graphics2D graphics2d = (Graphics2D) this.getGraphics();
        Font font = textPanelModel.getFont();
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

    public TextPanelModel getTextPanelModel() {
        return textPanelModel;
    }





}