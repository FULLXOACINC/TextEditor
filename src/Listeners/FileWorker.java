package Listeners;

import TextObjects.Char;
import TextObjects.Line;
import Window.TextPanel.TextPanel;
import Window.TextPanel.TextPanelModel;

import javax.swing.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by alex on 20.2.17.
 */
public class FileWorker {

    private TextPanelModel textPanelModel;

    public FileWorker(TextPanel textPanel){
        this.textPanelModel = textPanel.getTextPanelModel();
    }



    public void openFile(){
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath=fileChooser.getSelectedFile().getPath();
            boolean isTxt=getExtension(fileChooser.getSelectedFile().getName()).equals("txt");

            if (isTxt)
              openTextFile(filePath);
            else
              openXMLFile(filePath);
        }
    }

    private String getExtension(String name) {
        String extension = null;
        int i = name.lastIndexOf('.');
        if (i > 0 &&  i < name.length() - 1) {
            extension = name.substring(i+1).toLowerCase();
        }
        return extension;
    }

    private void openXMLFile(String fileName) {
        try {
            Line newLine = new Line();
            textPanelModel.setLines(new ArrayList<Line>());
            XMLStreamReader xmlReader = XMLInputFactory.newInstance()
                    .createXMLStreamReader(fileName, new FileInputStream(fileName));
            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement()) {
                    if (xmlReader.getLocalName().equals("Line")){
                        newLine = new Line();
                    }
                    else if (xmlReader.getLocalName().equals("Char")){
                        String font = xmlReader.getAttributeValue(null, "Font");
                        String size = xmlReader.getAttributeValue(null, "Size");
                        String style = xmlReader.getAttributeValue(null, "FontStyle");
                        xmlReader.next();
                        newLine.add(xmlReader.getText(), font, style, size);
                    }
                } else if (xmlReader.isEndElement()) {
                    if (xmlReader.getLocalName().equals("Line")){
                        textPanelModel.getLines().add(newLine);
                    }
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTextFile(String fileName) {
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line;
            textPanelModel.setLines(new ArrayList<Line>());
            while( ( line = reader.readLine() ) != null ) {
                Line newLine = new Line();
                char [] newCharArray = line.toCharArray ();
                for (char ch: newCharArray){
                    newLine.add(ch,textPanelModel.getFont());
                }
                textPanelModel.getLines().add(newLine);
            }

        }
        catch ( IOException e ) {
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveFile(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                    XMLOutputFactory output = XMLOutputFactory.newInstance();
                    XMLStreamWriter xmlWriter = output.createXMLStreamWriter
                            (new FileWriter(fileChooser.getSelectedFile() + ".xml"));
                    xmlWriter.writeStartDocument("UTF-8", "1.0");
                    xmlWriter.writeStartElement("TextDoc");
                for (Line line : textPanelModel.getLines()) {
                        xmlWriter.writeStartElement("Line");
                        for (Char ch : line.getChars()) {
                            xmlWriter.writeStartElement("Char");
                            xmlWriter.writeAttribute("Font", ch.getFontType());
                            xmlWriter.writeAttribute("FontStyle", Integer.toString(ch.getFontStyles()));
                            xmlWriter.writeAttribute("Size", Integer.toString(ch.getFontSize()));
                            xmlWriter.writeCharacters(ch.getStringCh());
                            xmlWriter.writeEndElement();
                        }
                        xmlWriter.writeEndElement();
                    }
                    xmlWriter.writeEndElement();
                    xmlWriter.writeEndDocument();
                    xmlWriter.flush();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void newFile() {
        textPanelModel.deleteAllText();
    }

}
