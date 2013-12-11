
package com.mycompany.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

//My import
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.ByteArrayOutputStream;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * REST Client
 *
 * @author shaosh
 */
public class RestClient {
    private WebResource webResourceAdd, webResourceGet;
    private Client client;
    private static final String BASEURI = "http://localhost:8080/restservices/webresources/";
    private static final String XMLNS = "http://cse446.asu.edu/PoxAssignment";
    private static final Logger LOG = Logger.getLogger(RestClient.class.getName());
                            
    public RestClient(){
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResourceAdd = client.resource(BASEURI).path("AddFoodItem");
        webResourceGet = client.resource(BASEURI).path("GetFoodItem");        
    }
    
    public String postFoodItem(String country, String name, String description, String category, String price) throws UniformInterfaceException{
        String xmlString = new String(); 
        String response = new String();
        try{
            //Build the xml tree. Convert the member variables into xml document
            Document request = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();//You can not use new Document()
            Element root = request.createElement("NewFoodItems");//Create a new element with "NewFoodItems" in the tag
            root.setAttribute("xmlns", XMLNS);//Set the attribute of the element to xmlns = XMLNS: <NewFoodItems xmlns=XMLNS>
            request.appendChild(root);//Always remember to add the root element to the document.
            Element child = request.createElement("FoodItem");//Create a new element with "FoodItem" in the tag
            child.setAttribute("country", country);//Change attribute
            root.appendChild(child);//add the FoodItem element into the root element
            Element nameElement = request.createElement("name");
            nameElement.setTextContent(name);//Set content of the element: <name>name</name>. Should use set/getTextContent, not set/getNodeValue
            child.appendChild(nameElement);//add the name element into the FoodItem element
            Element descriptionElement = request.createElement("description");
            descriptionElement.setTextContent(description);
            child.appendChild(descriptionElement);
            Element categoryElement = request.createElement("category");
            categoryElement.setTextContent(category);
            child.appendChild(categoryElement);
            Element priceElement = request.createElement("price");
            priceElement.setTextContent(price);
            child.appendChild(priceElement);                        
            //Convert the xml document into a string so that it can be sent to the server
            try{
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");//Encode with UTF-8
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(request), new StreamResult(stream));
                xmlString = stream.toString();
                System.out.println("Request message for AddFoodItem: " + xmlString);
                response = webResourceAdd.type(MediaType.APPLICATION_XML).post(String.class, xmlString);//use post(Class, object). Class is the returned type, object is the request message
                if (response.length() == 0)
                    return "null";
                return response;
            }
            catch (TransformerConfigurationException e) {  
                e.printStackTrace();  
            } 
            catch (TransformerException e) { 
                e.printStackTrace();  
            }           
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (response.length() == 0)
            return "null";
        return response;  
    }
    
    public String getFoodItem(String[] idList) throws UniformInterfaceException {     
        String xmlString = new String(); 
        String response = new String();
        try{
            Document request = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = request.createElement("SelectedFoodItems");
            root.setAttribute("xmlns", XMLNS);
            Element child;
            for(int i = 0; i < idList.length; i++){
                child = request.createElement("FoodItemId");
                child.setTextContent(idList[i]);
                root.appendChild(child);
            }       
            request.appendChild(root);
            try{
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(request), new StreamResult(stream));
                xmlString = stream.toString(); 
                webResourceGet.type(MediaType.APPLICATION_XML).put(xmlString);//Using PUT to send the request message for GET
                System.out.println("Request message for GetFoodItem: " + xmlString);
                //GET the fooditem mentioned in the PUT
                WebResource resource = webResourceGet;
                response = resource.accept(MediaType.APPLICATION_XML).get(String.class);
                return response;
            }
            catch (TransformerConfigurationException e) {  
                e.printStackTrace();  
            } 
            catch (TransformerException e) {  
                e.printStackTrace();  
            }           
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        client.destroy();
    }
}
