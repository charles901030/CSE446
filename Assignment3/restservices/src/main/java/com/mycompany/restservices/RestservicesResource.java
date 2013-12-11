
package com.mycompany.restservices;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

//My import
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author shaosh
 */
@Path("")
public class RestservicesResource{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestservicesResource
     */
    private static ArrayList<FoodItem> FoodItemList = new ArrayList(); //Used to store the data in the xml file  
    private static ArrayList<String> RequestIdList = new ArrayList();//Used to store the requested id sent by PUT
    private static int idGB = 100, idUS = 200, idIN = 300;//Used to calculate id for fooditems
    private static String xmlns;
    private static final Logger LOG = Logger.getLogger(RestservicesResource.class.getName());
    public RestservicesResource() {

    }
    
    //Load the data from xml file into the FoodItemList arraylist in the memory
    public static void readXml(){
        String country = new String();
        String id = new String();
        String name = new String();
        String description = new String();
        String category = new String();
        String price = new String();
//        LOG.info("Starting read xml");
        try{
            //Parse the file data
            File fooditemData = new File("C:\\FoodItemData.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fooditemData);
            
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList fooditems = root.getChildNodes();
            Node fooditem;
            if(fooditems != null){
                for(int i = 0; i < fooditems.getLength(); i++){
                    fooditem = fooditems.item(i);
                    if(fooditem.getNodeType() == Node.ELEMENT_NODE){
                        Element eElement = (Element) fooditem;
                        //Calculate the item number of each country
                        country = fooditem.getAttributes().getNamedItem("country").getNodeValue();
                        if(country.equals("GB"))
                            idGB++;
                        else if(country.equals("US"))
                            idUS++;
                        else if(country.equals("IN"))
                            idIN++;
                        else
                            System.out.println("Invalid country!");
//                        System.out.println(country);
                        //Convert every node into an object of FoodItem
                        for(Node node = fooditem.getFirstChild(); node != null; node = node.getNextSibling()){
                            if(node.getNodeType() == Node.ELEMENT_NODE){
                                if(node.getNodeName().equals("id")){                                    
                                    id=node.getFirstChild().getNodeValue();                                    
//                                    System.out.println(id);  
                                }
                                else if(node.getNodeName().equals("name")){
                                    name=node.getFirstChild().getNodeValue();                                    
//                                    System.out.println(name);  
                                }
                                else if(node.getNodeName().equals("description")){
                                    description=node.getFirstChild().getNodeValue();                                    
//                                    System.out.println(description);  
                                }
                                else if(node.getNodeName().equals("category")){
                                    category = node.getFirstChild().getNodeValue();
//                                    System.out.println(category);
                                }
                                else if(node.getNodeName().equals("price")){
                                    price = node.getFirstChild().getNodeValue();
//                                    System.out.println(price);
                                }
                            }
                        }
                        FoodItem fi = new FoodItem(country, id, name, description, category, price);
                        //add the fooditem into the FoodItemList
                        FoodItemList.add(fi);
                    }
                }            
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }   
        LOG.info("Complete read xml");
 //       System.out.println("Success");
    }

    /**
     * Retrieves representation of an instance of com.mycompany.restservices.RestservicesResource
     * @return an instance of java.lang.String
     */
    @Path("GetFoodItem")
    @GET
    @Produces("application/xml")
    public String getFoodItem() {
        boolean found = false;
        int code = 200;
        String interpretation;
        String xmlString = new String();
        try{ 
//            System.out.println(RequestIdList.size());
            //Build the xml document for response message
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("RetrievedFoodItems");
            Element element,idElement, nameElement, descriptionElement, categoryElement, priceElement, fooditemElement;
            root.setAttribute("xmlns", xmlns);
            doc.appendChild(root);
            //Search in the FoodItemList witht the same id in the RequestIdList
            for(int i = 0; i < RequestIdList.size(); i++){ 
                for(int j = 0; j < FoodItemList.size(); j++){ 
                    if(FoodItemList.get(j).getId().equals(RequestIdList.get(i))){
                        found = true;
                        //Build the element one by one
                        element = doc.createElement("FoodItem");
                        element.setAttribute("country", FoodItemList.get(j).getCountry());
                        root.appendChild(element);
                        idElement = doc.createElement("id");
                        idElement.setTextContent(FoodItemList.get(j).getId());
                        element.appendChild(idElement);
                        nameElement = doc.createElement("name");
                        nameElement.setTextContent(FoodItemList.get(j).getName());
                        element.appendChild(nameElement);
                        descriptionElement = doc.createElement("description");
                        descriptionElement.setTextContent(FoodItemList.get(j).getDescription());
                        element.appendChild(descriptionElement);
                        categoryElement = doc.createElement("category");
                        categoryElement.setTextContent(FoodItemList.get(j).getCategory());
                        element.appendChild(categoryElement);
                        priceElement = doc.createElement("price");
                        priceElement.setTextContent(FoodItemList.get(j).getPrice());
                        element.appendChild(priceElement);
                        break;                        
                    }
                }
                if(found == false){//If can't find the id, create an element with invalidfooditem
                    element = doc.createElement("InvalidFoodItem");
                    root.appendChild(element);
                    fooditemElement = doc.createElement("FoodItemId");
                    fooditemElement.setTextContent(RequestIdList.get(i));
                    element.appendChild(fooditemElement); 
                    code = 404;
                }
                found = false;
            }
            try{
                //Convert the xml document into string for response message
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(doc), new StreamResult(stream));
                xmlString = stream.toString();
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
        RequestIdList.clear();//After each get clear the RequestIdList for next GET
        if(code == 404){
            interpretation = new String("Invalid or incorrect input message");
            System.out.println("GET " + code + ": " + interpretation);
        }
        else{
            interpretation = new String("All Food Item Retrieved");
            System.out.println("GET " + code + ": " + interpretation);
        }
        System.out.println("=========================================================================");
        return xmlString;
    }
    
    @Path("GetFoodItem")
    @PUT
    @Consumes("application/xml")
    public void putRequestMessage(String content) {
//        LOG.info("Begin PUT");
        //If the FoodItemList is empty, load the xml file
        if(FoodItemList.size() == 0)
            readXml();
        int code;
        String interpretation;
        try {
            //Parse the request message
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            Document doc = dBuilder.parse(new InputSource(inputStream));
            
            //Extract the address for response message, extract the id and store in the RequestIdList for GET
            NodeList selectedList = doc.getElementsByTagName("SelectedFoodItems");
            NodeList foodItemList = doc.getElementsByTagName("FoodItemId");
            if(selectedList != null){
                xmlns = new String(selectedList.item(0).getAttributes().getNamedItem("xmlns").getNodeValue());
                if(foodItemList != null){
                    for(int i = 0; i < foodItemList.getLength(); i++){
                        RequestIdList.add(foodItemList.item(i).getTextContent());
//                        System.out.println("request:" + i + " id: " + foodItemList.item(i).getTextContent());
                    }
//                    System.out.println("RequestId")
                    return;
                }
                else{
                    code = 404;
                    interpretation = new String("Invalid or incorrect input message");
                    System.out.println("PUT " + code + ": " + interpretation);
                    return;
                }
            }
            else{
                code = 404;
                interpretation = new String("Invalid or incorrect input message");
                System.out.println("PUT " + code + ": " + interpretation);
                return;
            }
        } 
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch(SAXException e){
            e.printStackTrace();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
        return;
    }
    
    @Path("AddFoodItem")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public String postFoodItem(String content) throws IOException {
//        LOG.info("Starting posting");
        //If the FoodItemList is empty, load data from the file
        if(FoodItemList.size() == 0)
            readXml();
        boolean found = false;
        int code;
        String interpretation;
        String xmlString = new String();
        try {
            //Parse the xml string to store in the FoodItemList
//            LOG.info("Prepare parsing xml");     
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            Document doc = dBuilder.parse(new InputSource(inputStream));   
//            LOG.info("Start parsing xml");
            NodeList selectedList = doc.getElementsByTagName("NewFoodItems");
            NodeList foodItemList = doc.getElementsByTagName("FoodItem");
            if(selectedList != null){
                //Extract the address, and also other member variables
                xmlns = new String(selectedList.item(0).getAttributes().getNamedItem("xmlns").getNodeValue());  
                if(foodItemList != null){
                    String country = foodItemList.item(0).getAttributes().getNamedItem("country").getNodeValue();
                    String name = doc.getElementsByTagName("name").item(0).getTextContent();
                    String category = doc.getElementsByTagName("category").item(0).getTextContent();
                    String description = doc.getElementsByTagName("description").item(0).getTextContent();
                    String price = doc.getElementsByTagName("price").item(0).getTextContent();
//                    System.out.println(country);
//                    System.out.println(name);
//                    System.out.println(category);
//                    System.out.println(description);
//                    System.out.println(price);

//                    LOG.info("Building root");
                    //Build the xml document for response message
                    Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                    Element root = response.createElement("FoodItemAdded");
                    Element idElement;
                    root.setAttribute("xmlns", xmlns);
                    idElement = response.createElement("FoodItemId");
                    String idString = new String();
                    
//                    LOG.info("Building children");
                    //Check if the fooditem is already existed
                    for(int i = 0; i < FoodItemList.size() && found == false; i++){                        
                        if(country.equals(FoodItemList.get(i).getCountry())&&
                           name.equals(FoodItemList.get(i).getName())&&
                           description.equals(FoodItemList.get(i).getDescription())&&
                           category.equals(FoodItemList.get(i).getCategory())&&
                           price.equals(FoodItemList.get(i).getPrice())){
                            idString = FoodItemList.get(i).getId();
                            found = true;
                            break;
                        }
                    }
                    //If the fooditem is not existed, give it a new id and add it to the FoodItemList
                    if(found == false){                       
                        if(country.equals("GB")){                                   
                            FoodItem item = new FoodItem(country, "" + idGB, name, description, category, price);
                            idString = "" + idGB;
                            idGB++;
                            FoodItemList.add(item);
                        }
                        else if(country.equals("US")){                                   
                            FoodItem item = new FoodItem(country, "" + idUS, name, description, category, price);
                            idString = "" + idUS;
                            idUS++;
                            FoodItemList.add(item);
                        }
                        else if(country.equals("IN")){                                   
                            FoodItem item = new FoodItem(country, "" + idIN, name, description, category, price);
                            idString = "" + idIN;
                            idIN++;
                            FoodItemList.add(item);
                        }
                        else{
                            code = 400;
                            interpretation = new String("Invalid or incorrect input message");
                            System.out.println("POST " + code + ": " + interpretation);
                            System.out.println("=========================================================================");
                            return "";
                        }
                        code = 200;
                        interpretation = new String("Food Item Added");
                        System.out.println("POST " + code + ": " + interpretation);
                    }                    
                    else{
                        code = 409;
                        interpretation = new String("Food Item already in the Food List");
                        System.out.println("POST " + code + ": " + interpretation);
                    }
                    idElement.setTextContent(idString);
                    root.appendChild(idElement);
                    response.appendChild(root);
//                    LOG.info("Start transfer from xml to string");
                    try{
                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        transformer.setOutputProperty("encoding", "UTF-8");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        transformer.transform(new DOMSource(response), new StreamResult(stream));
                        xmlString = stream.toString();        
                    }
                    catch (TransformerConfigurationException e) {  
                        e.printStackTrace();  
                    } 
                    catch (TransformerException e) {  
                        e.printStackTrace();  
                    }               
                    System.out.println("=========================================================================");
                    return xmlString;       
                }
                else{
                    code = 400;
                    interpretation = new String("Invalid or incorrect input message");
                    System.out.println("POST " + code + ": " + interpretation);
                    System.out.println("=========================================================================");
                    return "";
                }
            }
            else{
                code = 400;
                interpretation = new String("Invalid or incorrect input message");
                System.out.println("POST " + code + ": " + interpretation); 
                System.out.println("=========================================================================");
                return "";
            }
        } 
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch(SAXException e){
            e.printStackTrace();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
        System.out.println("=========================================================================");
        return "";
    }
}