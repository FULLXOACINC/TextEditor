package TextObjects;

import java.awt.*;


/**
 * Created by alex on 19.2.17.
 */
public class Char{

    private Font font;
    private Boolean isSelect;
    private char ch;
    private int height;
    private int wight;
    private int x;
    private int y;
    private int fontStyle;
    private int numberLine;

    public Char(char ch, Font font){
        this.ch = ch;
        isSelect = false;
        this.font = font;
    }

    public Char(Char ch) {
        this.ch = ch.getCharCh();
        this.font = ch.getFont();
        this.isSelect = ch.getIsSelect();
        this.fontStyle = ch.getFontStyles();
    }

    public Char(char ch, String font, String style, String size) {
        this.ch = ch;
        this.font = new Font(font, Integer.parseInt(style) , Integer.parseInt(size));
        this.isSelect = false;
        this.fontStyle = Integer.parseInt(style);
    }

    public String getStringCh(){
        return Character.toString(ch);
    }

    public char getCharCh(){
        return ch;
    }

    public Font getFont() {
        return font;
    }

    public String getFontType(){
        return font.getFontName();
    }

    public int getFontStyles(){
        return fontStyle;
    }

    public int getFontSize(){
        return font.getSize();
    }

    public void setFontType(String type){
        font = new Font(type, getFontStyles(), getFontSize());
    }

    public void setFontStyles(int style){
        if (fontStyle == Font.PLAIN && style == Font.BOLD) {
            fontStyle = Font.BOLD;
        } else if (fontStyle == Font.BOLD && style == Font.BOLD) {
            fontStyle = Font.PLAIN;
        } else if (fontStyle == Font.PLAIN && style == Font.ITALIC) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.ITALIC && style == Font.ITALIC) {
            fontStyle = Font.PLAIN;
        } else if (fontStyle == Font.BOLD && style == Font.ITALIC) {
            fontStyle = Font.BOLD + Font.ITALIC;
        } else if (fontStyle == Font.ITALIC && style == Font.BOLD) {
            fontStyle = Font.BOLD + Font.ITALIC;
        } else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.BOLD) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.ITALIC) {
            fontStyle = Font.BOLD;
        }

    }

    public void setFontSize(int size){
        font = new Font(getFontType(), getFontStyles(), size);
    }

    public void setNumberLine(int number){
        numberLine = number;
    }

    public void setIsSelect(boolean selected){ isSelect = selected;}

    public boolean getIsSelect(){ return isSelect; }

    public void setHeight(int height){ this.height = height;}

    public int getHeight(){ return height;}

    public void setWight(int wight){ this.wight = wight;}

    public int getWight(){ return wight;}

    public void setX(int x){ this.x = x;}

    public int getX(){ return x;}

    public void setY(int y){ this.y = y;}

    public int getY(){ return y;}

    public void setNormalizationBold() {
        if (fontStyle == Font.PLAIN) {
            fontStyle = Font.BOLD;
        } else if (fontStyle == Font.ITALIC) {
            fontStyle = Font.BOLD + Font.ITALIC;
        }
    }

    public void setNormalizationItalic() {
        if (fontStyle == Font.PLAIN) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.BOLD) {
            fontStyle = Font.BOLD + Font.ITALIC;
        }
    }

    public int getNumberLine() {
        return numberLine;
    }
}
