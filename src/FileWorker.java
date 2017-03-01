import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alex on 20.2.17.
 */
public class FileWorker {
    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;

    public FileWorker(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
    }

    public void openFile(){
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath=fileChooser.getSelectedFile().getPath();
            if (getExtension(fileChooser.getSelectedFile().getName()).equals("txt"))
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

    }

    private void openTextFile(String fileName) {
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            textPanel.setLines(new ArrayList<Line>());
            while( ( line = reader.readLine() ) != null ) {
                Line newLine = new Line(mainWindow);
                char [] newCharArray = line.toCharArray ();
                for (char ch: newCharArray){
                    newLine.add(ch);
                }
                textPanel.getLines().add(newLine);
            }
            mainWindow.updateWindow();
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
                try {

                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                    Document doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("TextEditorByAlex");
                    doc.appendChild(rootElement);

                    for (Line line : textPanel.getLines()) {
                        Element Line = doc.createElement("Line");
                        rootElement.appendChild(Line);
                        for (Char ch : line.getChars()) {
                            Element character = doc.createElement("Char");
                            character.setTextContent(ch.getCharCh()+"");
                            rootElement.appendChild(character);
                            Line.appendChild(character);

                        }

                    }






                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(fileChooser.getSelectedFile()+".xml"));



                    transformer.transform(source, result);

                    System.out.println("File saved!");

                } catch (ParserConfigurationException pce) {
                    pce.printStackTrace();
                } catch (TransformerException tfe) {
                    tfe.printStackTrace();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }



    public void newFile() {
        textPanel.deleteAllText();
        mainWindow.updateWindow();
    }

}
