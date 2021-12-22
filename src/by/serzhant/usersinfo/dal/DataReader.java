package by.serzhant.usersinfo.dal;

import by.serzhant.usersinfo.entity.Role;
import by.serzhant.usersinfo.entity.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public List<User> readDataFile(Path path) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = documentBuilder.parse(path.toString());
        } catch (SAXException | IOException e) {
            return new ArrayList<>();
        }

        Element root = document.getDocumentElement();
        List<User> userList = new ArrayList<>();

        userList.addAll(createElement(root, "user"));

        return userList;
    }

    private User buildUser(Element user) {
        String firsName = getElementTextContent(user, "firstName");
        String lastName = getElementTextContent(user, "lastName");
        String email = getElementTextContent(user, "email");

        NodeList rolesNodeList = user.getElementsByTagName("role");


        int rolesNodeListLength = rolesNodeList.getLength();

        User newUser = new User(firsName,lastName,email);

        for (int i = 0; i < rolesNodeListLength; i++) {
            Node roleNode = rolesNodeList.item(i);

            Element roleElement = (Element) roleNode;

            String role = roleElement.getTextContent();
            newUser.addRole(buildRole(role));
        }

        NodeList phoneNumberNodeList = user.getElementsByTagName("phoneNumber");
        int phoneNumberListLength = phoneNumberNodeList.getLength();

        for (int i = 0; i < phoneNumberListLength; i++) {
            Node phoneNumberNode = phoneNumberNodeList.item(i);

            Element phoneNumberElement = (Element) phoneNumberNode;

            String phoneNumber = phoneNumberElement.getTextContent();
            newUser.addPhoneNumber(phoneNumber);
        }

        return newUser;
    }

    private Role buildRole(String inputString) {
        switch (inputString) {
            case "USER":
                return Role.USER;
            case "CUSTOMER":
                return Role.CUSTOMER;
            case "ADMIN":
                return Role.ADMIN;
            case "PROVIDER":
                return Role.PROVIDER;
            case "SUPER_ADMIN":
                return Role.SUPER_ADMIN;
        }

        return Role.USER;
    }


    private String getElementTextContent(Element gemElement, String tagName) {
        NodeList list = gemElement.getElementsByTagName(tagName);

        return list.item(0).getTextContent();
    }


    private List<User> createElement(Element root, String tagName) {
        List<User> userList = new ArrayList<>();
        NodeList elementsList = root.getElementsByTagName(tagName);

        for (int i = 0; i < elementsList.getLength(); i++) {
            Element userElement = (Element) elementsList.item(i);
            userList.add(buildUser(userElement));
        }

        return userList;
    }
}
