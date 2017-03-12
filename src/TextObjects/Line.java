package TextObjects;


import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 19.2.17.
 */
public class Line {

    private int maxHight;
    private int maxLength;
    private int coordinateY;
    private int numberLine;
    private List<Char> chars = new LinkedList<Char>();

    public Line() {
        maxHight = 15;
        maxLength = 0;
    }

    public List<Char> getChars(){
        return chars;
    }

    public void add(char ch,Font font) {
        chars.add(new Char(ch,font));
    }

    public void add(Char ch) {
        chars.add(new Char(ch));
    }

    public void add(int index, char ch, Font font) {
        chars.add(index, new Char(ch,font));
    }

    public void remove(int caretX) {
        chars.remove(caretX-1);
    }

    public int indexOf(Char ch) {
        return chars.indexOf(ch);
    }

    public Line copySubLine(int x1, int x2){
        Line newLine = new Line();
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

    public int getNumberLine() {
        return numberLine;
    }

    public void setMaxHightNumber(int value) { maxHight = value;}

    public void add(String text, String font, String style, String size) {
        chars.add(new Char(text.charAt(0), font, style, size));
    }
}
