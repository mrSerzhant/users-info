package by.serzhant.usersinfo.dal;

import by.serzhant.usersinfo.entity.Role;
import by.serzhant.usersinfo.entity.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;

public class DataWriter {


    public void writeDataFile(List<User> inputUserList) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();
            Element root = document.createElement("users");
            document.appendChild(root);

            for (User user : inputUserList) {
                Element elementUser = document.createElement("user");

                Element elementFirstName = document.createElement("firstName");
                elementFirstName.setTextContent(user.getFirstName());
                elementUser.appendChild(elementFirstName);


                Element elementLastName = document.createElement("lastName");
                elementLastName.setTextContent(user.getLastName());
                elementUser.appendChild(elementLastName);

                Element elementEmail = document.createElement("email");
                elementEmail.setTextContent(user.getEmail());
                elementUser.appendChild(elementEmail);

                Element elementRoles = document.createElement("roles");

                for (Role role : user.getRoles()) {
                    Element elementRole = document.createElement("role");
                    elementRole.setTextContent(role.name());
                    elementRoles.appendChild(elementRole);
                }

                elementUser.appendChild(elementRoles);

                Element elementPhoneList = document.createElement("phoneNumbers");

                for (String element : user.getListPhoneNumber()) {
                    Element elementPhoneNumber = document.createElement("phoneNumber");
                    elementPhoneNumber.setTextContent(element);
                    elementPhoneList.appendChild(elementPhoneNumber);
                }

                elementUser.appendChild(elementPhoneList);

                root.appendChild(elementUser);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult("src/resources/data.xml");
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}