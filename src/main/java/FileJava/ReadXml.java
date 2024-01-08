package FileJava;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml {
    
    private static final String DIRECTORY_PATH = "C:\\Users\\tudor\\eclipse-workspace\\GestionaleBDX\\General File\\";
    
    public static ArrayList<Persona> LetturaXml(String NomeFile) {
        String fullPath = DIRECTORY_PATH + NomeFile;
        ArrayList<Persona> listaPersone = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fullPath);
            document.getDocumentElement().normalize();

            NodeList rows = document.getElementsByTagName("ROW");
            for (int i = 0; i < rows.getLength(); i++) {
                Node rowNode = rows.item(i);
                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element row = (Element) rowNode;
                    Persona persona = new Persona();

                    persona.setId(getElementValue(row, "PF_IDANAG"));
                    persona.setCognome(convertSpecialCharacters(getElementValue(row, "PF_COGNOME")));
                    persona.setNome(convertSpecialCharacters(getElementValue(row, "PF_NOME")));
                    persona.setCodiceFiscale(getElementValue(row, "PF_CDFISC"));
                    persona.setComuneCodice(getElementValue(row, "PF_CDCOMU"));
                    persona.setDataNascita(getElementValue(row, "PF_DTNASC"));

                    String sesso = getElementValue(row, "PF_FSESSO");
                    if (sesso != null && !sesso.isEmpty()) {
                        persona.setSesso(sesso.charAt(0));
                    }

                    persona.setDataInserimento(getElementValue(row, "PF_DTINSE"));


                    listaPersone.add(persona);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            listaPersone.clear();
            return listaPersone;
        }
        return listaPersone;
    }

    private static String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return null;
    }

    
    private static String convertSpecialCharacters(String input) {
        if (input == null) return null;

        input = input.replace("&#39;", "'");

        input = input.replace("&amp;", "&")
                     .replace("&lt;", "<")
                     .replace("&gt;", ">")
                     .replace("&apos;", "'")
                     .replace("&quot;", "\"");

        return input;
    }

}
