import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            //citire in memorie
            File xmlFile = new File("src/recipes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("XML incarcat cu succes!");

            //adaugare reteta manuala si salvare (=task 4)
            addRecipeManual(doc, xmlFile);

            //afiseaza recomandarile in consola
            showRecommendations(doc);

            //genereaza tabel colorat cu xls
            generateHTML(doc);

            //exemplu de filtrare dupa bucatarie
            findRecipeByCuisine(doc, "Italian");

        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metoda pentru adaugare si salvare locala (=task 4)
    public static void addRecipeManual(Document doc, File xmlFile) throws Exception {
        //gasim nodul parinte recipes
        Node recipesNode = doc.getElementsByTagName("recipes").item(0);

        //cream elementul recipe
        Element newRecipe = doc.createElement("recipe");

        //cream restul campurilor
        Element title = doc.createElement("title");
        title.setTextContent("Paste Carbonara Test");
        newRecipe.appendChild(title);

        Element c1 = doc.createElement("cuisine");
        c1.setTextContent("Italian");
        newRecipe.appendChild(c1);

        Element c2 = doc.createElement("cuisine");
        c2.setTextContent("European");
        newRecipe.appendChild(c2);

        Element diff = doc.createElement("difficulty");
        diff.setTextContent("Beginner");
        newRecipe.appendChild(diff);

        //adaugam in lista din memorie
        recipesNode.appendChild(newRecipe);

        //salvare efectiva in fisierul xml cu suprascriere
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);

        System.out.println("\n--- Task 4: Reteta a fost adaugata si salvata in fisier! ---");
    }

    public static void showRecommendations(Document doc) throws Exception {
        XPath xPath = XPathFactory.newInstance().newXPath();

        //leveluri de skilluri
        String userSkill = xPath.evaluate("/platform/user/skillLevel", doc);
        String expression = "/platform/recipes/recipe[difficulty='" + userSkill + "']/title";
        NodeList nodeList = (NodeList) xPath.evaluate(expression, doc, XPathConstants.NODESET);

        System.out.println("\n--- Recomandari Skill (" + userSkill + ") ---");
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println("- " + nodeList.item(i).getTextContent());
        }

        //skill si preferred cuisine
        String userCuisine = xPath.evaluate("/platform/user/preferredCuisine", doc);
        String expression2 = "/platform/recipes/recipe[difficulty='" + userSkill + "' and cuisine='" + userCuisine + "']/title";
        NodeList nodeList2 = (NodeList) xPath.evaluate(expression2, doc, XPathConstants.NODESET);

        System.out.println("\n--- Recomandari Skill + Bucatarie (" + userCuisine + ") ---");
        for (int i = 0; i < nodeList2.getLength(); i++) {
            System.out.println("- " + nodeList2.item(i).getTextContent());
        }
    }

    //transformarea XSL -> HTML
    public static void generateHTML(Document doc) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        //path to xsl
        Source xsl = new StreamSource(new File("src/webapp/recipes.xsl"));
        Transformer transformer = factory.newTransformer(xsl);

        Source xml = new DOMSource(doc);
        //rezultat in html in webapp
        Result result = new StreamResult(new File("src/webapp/display.html"));
        transformer.transform(xml, result);
        System.out.println("\n--- Fisierul display.html a fost generat in folderul webapp! ---");
    }

    //filter dupa o bucatarie specifica
    public static void findRecipeByCuisine(Document doc, String cuisineType) throws Exception {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//recipe[cuisine='" + cuisineType + "']/title";
        NodeList recipes = (NodeList) xPath.evaluate(expression, doc, XPathConstants.NODESET);

        System.out.println("\n--- Retete tip " + cuisineType + " ---");
        for (int i = 0; i < recipes.getLength(); i++) {
            System.out.println("- " + recipes.item(i).getTextContent());
        }
    }
}