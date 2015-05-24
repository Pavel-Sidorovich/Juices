package bsu.fpmi.chat.storage.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import bsu.fpmi.chat.controller.TaskServlet;
import bsu.fpmi.chat.model.Mess;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XMLHistoryUtil {
    private static final String STORAGE_LOCATION = "D:\\Juices\\Juices\\Chat\\WebChatApplication\\history.xml"; // history.xml will be located in the home directory
    private static final String MESSAGES = "messages";
    private static final String TASK = "task";
    private static final String ID = "id";
    private static final String MESSAGE= "message";
    private static final String USER = "user";

    private XMLHistoryUtil() {
    }

    public static synchronized void createStorage() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(MESSAGES);
        doc.appendChild(rootElement);

        Transformer transformer = getTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(STORAGE_LOCATION));
        transformer.transform(source, result);
    }

    public static synchronized void addData(Mess mess) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement(); // Root <tasks> element

        Element taskElement = document.createElement(TASK);
        root.appendChild(taskElement);

        taskElement.setAttribute(ID, mess.getId());

        Element description = document.createElement(MESSAGE);
        description.appendChild(document.createTextNode(mess.getMessage()));
        taskElement.appendChild(description);

        Element done = document.createElement(USER);
        done.appendChild(document.createTextNode(mess.getUser()));
        taskElement.appendChild(done);

        DOMSource source = new DOMSource(document);

        Transformer transformer = getTransformer();

        StreamResult result = new StreamResult(STORAGE_LOCATION);
        transformer.transform(source, result);
    }

    public static synchronized void updateData(Mess mess) throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Node taskToUpdate = getNodeById(document, mess.getId());

        if (taskToUpdate != null) {

            NodeList childNodes = taskToUpdate.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {

                Node node = childNodes.item(i);

                if (MESSAGE.equals(node.getNodeName())) {
                    node.setTextContent(mess.getMessage());
                }

                if (USER.equals(node.getNodeName())) {
                    node.setTextContent(mess.getUser());
                }

            }

            Transformer transformer = getTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(STORAGE_LOCATION));
            transformer.transform(source, result);
        } else {
            throw new NullPointerException();
        }
    }

    public static synchronized boolean doesStorageExist() {
        File file = new File(STORAGE_LOCATION);
        return file.exists();
    }

    public static synchronized List<Mess> getTasks() throws SAXException, IOException, ParserConfigurationException {
        List <Mess> tasks = new ArrayList<Mess>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement(); // Root <tasks> element
        NodeList taskList = root.getElementsByTagName(TASK);
        for (int i = 0; i < taskList.getLength(); i++) {
            Element taskElement = (Element) taskList.item(i);
            String id = taskElement.getAttribute(ID);
            String message = taskElement.getElementsByTagName(MESSAGE).item(0).getTextContent();
            String user = taskElement.getElementsByTagName(USER).item(0).getTextContent();
            Mess mess = new Mess(message, user, id);
            tasks.add(mess);
            Logger.getLogger(TaskServlet.class.getName()).info(user + " : " + message);
        }
        return tasks;
    }

    public static synchronized int getStorageSize() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement(); // Root <tasks> element
        return root.getElementsByTagName(TASK).getLength();
    }

    private static Node getNodeById(Document doc, String id) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile("//" + TASK + "[@id='" + id + "']");
        return (Node) expr.evaluate(doc, XPathConstants.NODE);
    }

    private static Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // Formatting XML properly
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }

}