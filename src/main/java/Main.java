import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        //String path = Main.class.getResource("CUV-Seg.xml").getFile();
        String path = Main.class.getResource("CUV-Seg-Traditional.xml").getFile();
        //String path = Main.class.getResource("1.txt").getFile();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "utf-8");
        BufferedReader br = new BufferedReader (isr); 
        InputSource is = new InputSource(br);        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(is);
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList)xPath.evaluate("/Segmented-Chinese-Text/Book/Chapter/Verse", doc.getDocumentElement(), XPathConstants.NODESET);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cuv.seg"), "utf-8"));
        
        for (int j=0; j<nodes.getLength(); j++) {
            Element verse = (Element) nodes.item(j);
            NodeList words = verse.getElementsByTagName("Word");

            for (int k=0; k<words.getLength(); k++) {
                Element word = (Element)words.item(k);
                //System.out.print("[" + word.getTextContent() + "]");                
                String line = word.getTextContent() + " ";
                
                System.out.print(line);
                out.write(line);
                out.flush();
            }

            System.out.println();
            out.write(System.getProperty("line.separator"));
        }
    }
}
