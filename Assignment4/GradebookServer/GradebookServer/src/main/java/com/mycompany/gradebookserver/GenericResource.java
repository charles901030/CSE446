/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gradebookserver;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

//My imports
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import com.mongodb.*;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.logging.Level;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.ws.rs.core.Response;
/**
 * REST Web Service
 *
 * @author shaosh
 */
@Path("")
public class GenericResource {

    @Context
    private UriInfo context;
     
    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection studentlist;
    private static DBCollection itemlist;
    private static boolean emptyDB = true;
    private static boolean assignmentExist = false;
    private static boolean quizExist = false;
    private static boolean examExist = false;
    
    private static String xmlns;
    private static final Logger LOG = Logger.getLogger(GenericResource.class.getName());


    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() throws UnknownHostException {        
        
    }
    
    public static void createStudentList() throws UnknownHostException{       
        System.out.println("start");
//        mongoClient = new MongoClient();
//        db = mongoClient.getDB("gradebook");
//        studentlist = db.getCollection("students");
//        itemlist = db.getCollection("items");
        
        String[] names = {"Anderson Brandon", "Anastasia Atwood", "Blake Bailey", "Jesse Benton", "Andrew Berresfor", "Anthony Brewer",
                          "Frank Calliss", "Chia-Wen Chan", "Tommy Chhay", "Jeffrey Cirka"};
        int[] ids = new int[10];
        BasicDBObject student;
        BasicDBObject gradingitems = new BasicDBObject("assignments", "").append("quizzes", "").append("exams", "").append("paper", "");
//        BasicDBObject gradeitems;
//        BasicDBObject assignments = new BasicDBObject("Assignment1", "").append("Assignment2", "").append("Assignment3", "").append("Assignment4", "");
//        BasicDBObject exams = new BasicDBObject("Midterm", "").append("FInal", "");
//        BasicDBObject quizzes = new BasicDBObject("Quiz1", "").append("Quiz2", "").append("Quiz3", "");        
        
        for (int i = 0; i < 10; i++){
            ids[i] = i + 1;
//            gradeitems = new BasicDBObject("name", names[i]).append("id", ids[i]).append("assignments", assignments).append("quizzes", quizzes).append("exams", exams).append("paper", "").append("total", "").append("letter", "");
//            gradeitems = new BasicDBObject("name", names[i]).append("id", ids[i]).append("assignments", assignments).append("quizzes", quizzes).append("exams", exams).append("paper", "").append("total", "").append("letter", "");
            student =  new BasicDBObject("name", names[i]).append("studentid", ids[i])./*append("grading items", "").*/append("total", "").append("possible", "").append("letter", "");
            studentlist.insert(student);
        }
//        BasicDBObject item = new BasicDBObject("assignments", "").append("quizzes", "").append("exams", "").append("paper", "");
        emptyDB = false;
        System.out.println("end");      
    }
    
    /**
     * Retrieves representation of an instance of com.mycompany.gradebookserver.GenericResource
     * @return an instance of java.lang.String
     */
    @Path("/{studentId}/{item}")
    @GET
    @Produces("application/xml")
    public Response getGradingItem(@PathParam("studentId") String id, @PathParam("item") String item) {
        String xmlString = new String();
        Response response;
        String comment;
        String grade;
        String name;
        String total;
        String letter;
        String possible;
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        field.put("studentid", Integer.parseInt(id));
        field.put(item, 1);
        BasicDBObject obj = null;
        DBCursor cursor = studentlist.find(query, field);
        if(!cursor.hasNext()){
            response = Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_XML).build();
            return response;
        }
        while(cursor.hasNext()){
            obj = (BasicDBObject)(cursor.next());
            if(obj.get(item) == null){
//                System.out.println("Not exist");
                response = Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_XML).build();
                return response;
            }
            if(obj.getString("studentid").equals(id)){
//                System.out.println(obj.toString());
//                System.out.println(Integer.parseInt(id));
                break;
            }
        }
        comment = ((BasicDBObject)(obj.get(item))).getString("comment");
        grade = ((BasicDBObject)(obj.get(item))).getString("grade");
        name = obj.getString("name");

        BasicDBObject field2 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        field2.put("studentid", Integer.parseInt(id));
        field2.put("total", 1);
        field2.put("letter", 1);
        field2.put("possible", 1);
//        System.out.println(field2.toString());
        DBCursor cursor2 = studentlist.find(query2, field2);
        BasicDBObject obj2 = null;
        while(cursor2.hasNext()){
            obj2 = ((BasicDBObject)(cursor2.next()));
            if(obj2.getString("studentid").equals(id)){
//                System.out.println(obj2.toString());
//                System.out.println(Integer.parseInt(id));
                break;
            }                   
        }
        total = obj2.get("total").toString();
        letter = obj2.get("letter").toString();
        possible = obj2.get("possible").toString();

        try{
            Document responseDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = responseDoc.createElement("ItemRead");
//                root.setAttribute("xmlns", xmlns);
            Element itemElement = responseDoc.createElement("Item");
            itemElement.setTextContent(item);
            Element gradeElement = responseDoc.createElement("Grade");
            gradeElement.setTextContent(grade);
            Element commentElement = responseDoc.createElement("Comment");
            commentElement.setTextContent(comment);
            Element nameElement = responseDoc.createElement("Name");
            nameElement.setTextContent(name);
            Element totalElement = responseDoc.createElement("Total");
            totalElement.setTextContent(total);
            Element letterElement = responseDoc.createElement("Letter");
            letterElement.setTextContent(letter);
            Element possibleElement = responseDoc.createElement("Possible");
            possibleElement.setTextContent(possible);
            root.appendChild(itemElement);
            root.appendChild(commentElement);
            root.appendChild(gradeElement);
            root.appendChild(nameElement);
            root.appendChild(totalElement);
            root.appendChild(letterElement);
            root.appendChild(possibleElement);
            responseDoc.appendChild(root);
            try{
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(responseDoc), new StreamResult(stream));
                xmlString = stream.toString();        
            }
            catch (TransformerConfigurationException e) {  
                e.printStackTrace();  
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                return response;
            } 
            catch (TransformerException e) {  
                e.printStackTrace();  
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                return response;
            }          
            response = Response.status(Response.Status.OK).entity(xmlString).build();
            return response;
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace(); 
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            return response;
        }
//        }   
//        else{
//            response = Response.status(Response.Status.NOT_FOUND).build();
//            return response;
//        }
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @Path("/{studentId}/{item}")
    @PUT    
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response putGradingItem(String content, @PathParam("studentId") String id, @PathParam("item") String item) {
        String xmlString = new String();
        Response response;
        try{
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            Document doc = dBuilder.parse(new InputSource(inputStream));
            
            NodeList updateGradingItemList = doc.getElementsByTagName("UpdateGradingItem");
            if(updateGradingItemList != null){
                xmlns = updateGradingItemList.item(0).getAttributes().getNamedItem("xmlns").getNodeValue();
                String grade = doc.getElementsByTagName("Grade").item(0).getTextContent();
                String comment = doc.getElementsByTagName("Comment").item(0).getTextContent();
                
                BasicDBObject query = new BasicDBObject();
                BasicDBObject field = new BasicDBObject();
                field.put("studentid", Integer.parseInt(id));
                field.put(item, 1);
                BasicDBObject obj = null;
                DBCursor cursor = studentlist.find(query, field);
                if(!cursor.hasNext()){
                    response = Response.status(Response.Status.NOT_FOUND).build();
                    return response;
                }
                while(cursor.hasNext()){
                    obj = (BasicDBObject)(cursor.next());
                    if(obj.get(item) == null){
//                        System.out.println("Not exist");
                        response = Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_XML).build();
                        return response;
                    }
                    if(obj.getString("studentid").equals(id)){
        //                System.out.println(obj.toString());
        //                System.out.println(Integer.parseInt(id));
                        break;
                    }
                }
                    
                if(!comment.isEmpty()){
                    String item2 = item.concat(".comment");
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject(item2, comment)));
                }
                if(!grade.isEmpty()){
                    String item2 = item.concat(".grade");
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject(item2, grade)));
                }
//                }
//                else{
//                    response = Response.status(Response.Status.NOT_FOUND).build();
//                    return response;
//                }
                
                field = new BasicDBObject();
                field.put("total", 1);
                cursor = studentlist.find(query, field);
                String gradeStored = ((BasicDBObject)(cursor.next())).getString("total");
                double gradenum;
                if(gradeStored.equals(""))
                    gradenum = 0;
                else
                    gradenum = Double.parseDouble(gradeStored);
                Double newGrade =  Double.parseDouble(grade);
                System.out.println("" + gradenum + " " + newGrade);
                if(item.indexOf("Assignment") >= 0){
                    gradenum += (newGrade / 4 * 0.3);
                }
                else if(item.indexOf("Quiz") >= 0){
                    gradenum += (newGrade / 3 * 0.05);
                }
                else if(item.equalsIgnoreCase("Midterm") || item.equalsIgnoreCase("Final")){
                    gradenum += (newGrade * 0.25);
                }
                else{
                    gradenum += (newGrade * 0.15);
                }
                studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("total", gradenum)));
                
                field = new BasicDBObject();
                field.put("possible", 1);
                cursor = studentlist.find(query, field);
                String possibleStored = ((BasicDBObject)(cursor.next())).getString("possible");
                double gradePossible;
                if(gradeStored.equals(""))
                    gradePossible = 0;
                else
                    gradePossible = Double.parseDouble(gradeStored);
                if(item.indexOf("Assignment") >= 0){
                    gradePossible += 7.5;
                }
                else if(item.indexOf("Quiz") >= 0){
                    gradePossible += 1.7;
                }
                else if(item.equalsIgnoreCase("Midterm") || item.equalsIgnoreCase("Final")){
                    gradePossible += 25;
                }
                else{
                    gradePossible += 15;
                }
                studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("possible", gradePossible)));
                
                int total = (int)((newGrade / gradePossible) * 100);
                if(total >= 99)  
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A+")));
		else if(total >= 95) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A")));
		else if(total >= 90)
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A-")));
		else if(total >= 87) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B+")));
		else if(total >= 84) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B")));
		else if(total >= 80) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B-")));
		else if(total >= 75) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "C+")));
		else if(total >= 70) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "C")));
		else if(total >= 60) 
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "D")));
		else  
                    studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "E")));
              //                }                
                /*
                Document responseDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Element root = responseDoc.createElement("ItemUpdated");
                root.setAttribute("xmlns", xmlns);
                Element itemElement = responseDoc.createElement("Item");
                itemElement.setTextContent(item);
                Element gradeElement = responseDoc.createElement("Grade");
                gradeElement.setTextContent(grade);
                Element commentElement = responseDoc.createElement("Comment");
                commentElement.setTextContent(comment);
                root.appendChild(itemElement);
                root.appendChild(commentElement);
                responseDoc.appendChild(root);
                try{
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty("encoding", "UTF-8");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    transformer.transform(new DOMSource(responseDoc), new StreamResult(stream));
                    xmlString = stream.toString();        
                }
                catch (TransformerConfigurationException e) {  
                    e.printStackTrace();  
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                    return response;
                } 
                catch (TransformerException e) {  
                    e.printStackTrace();  
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                    return response;
                }       */         
                response = Response.status(Response.Status.NO_CONTENT).build();
                return response;
               
            }
            else{
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                return response;
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace(); 
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            return response;
        }
        catch(SAXException e){
            e.printStackTrace();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            return response;
        }
        catch(java.io.IOException e){
            e.printStackTrace();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            return response;
        }        
    }
    
    @Path("")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postGradingItem(String content) throws UnknownHostException{
        Response response;
        String xmlString = new String();
        if(emptyDB && studentlist.count() == 0){
            try {
                createStudentList();
            } catch (UnknownHostException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                return response;
            }
        }
        
        try{
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes("utf-8"));
            Document doc = dBuilder.parse(new InputSource(inputStream));
            
            NodeList gradingItemList = doc.getElementsByTagName("NewGradingItem");
            if(gradingItemList != null){
                xmlns = gradingItemList.item(0).getAttributes().getNamedItem("xmlns").getNodeValue();
//                String type = doc.getElementsByTagName("type").item(0).getTextContent();
                String title = doc.getElementsByTagName("Title").item(0).getTextContent();
                String titleURI = title.replace(" ", "");
                String type = doc.getElementsByTagName("Type").item(0).getTextContent();
                String id = doc.getElementsByTagName("Id").item(0).getTextContent();
                
                if(id.equals("")){
                    BasicDBObject gradeItem = new BasicDBObject(type, title);
                    BasicDBObject itemContent = new BasicDBObject("type", type).append("grade", "null").append("comment", "null");
                    DBCursor cursor = studentlist.find(); 
                    itemlist.insert(gradeItem);
                    int num = 0;
    //                String[] locationURIs = new String[10];
    //                System.out.println(context.getAbsolutePath());
                    while(cursor.hasNext() && num < 10){
                        num++;
                        studentlist.update(new BasicDBObject("studentid", num), new BasicDBObject("$set", new BasicDBObject(titleURI, itemContent)));
                        URI locationURI = URI.create(context.getAbsolutePath().toString() + num + "/" + titleURI);
    //                    locationURIs[num - 1] = new String(locationURI.toString());
    //                    System.out.println(locationURI);
                    }
                }
                else{
                    BasicDBObject query = new BasicDBObject();
                    BasicDBObject field = new BasicDBObject();
                    field.put("studentid", Integer.parseInt(id));
                    field.put(titleURI, 1);
                    DBCursor cursor = studentlist.find(query, field);
                    if(!(cursor.hasNext())){
                        response = Response.status(Response.Status.NOT_FOUND).build();
                        return response;
                    }
                    while(cursor.hasNext()){
                        BasicDBObject obj = (BasicDBObject)(cursor.next());
                        if(obj.get(titleURI) == null && obj.getString("studentid").equals(id)){break;}
                        else{
                            response = Response.status(Response.Status.FORBIDDEN).type(MediaType.APPLICATION_XML).build();
                            return response;
                        }
                    }
//                    BasicDBObject obj;
//                    
                    BasicDBObject itemContent = new BasicDBObject("type", type).append("grade", "null").append("comment", "null");
                    int idnum = Integer.parseInt(id);
                    studentlist.update(new BasicDBObject("studentid", idnum), new BasicDBObject("$set", new BasicDBObject(titleURI, itemContent)));
                    URI locationURI = URI.create(context.getAbsolutePath().toString() + idnum + "/" + titleURI);
                }
                
                Document responseDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Element root = responseDoc.createElement("ItemCreated");
                root.setAttribute("xmlns", xmlns);
                Element itemElement = responseDoc.createElement("Title");
                itemElement.setTextContent(title);
                Element locationElement = responseDoc.createElement("Location");
                if(id.equals(""))
                    locationElement.setTextContent(context.getAbsolutePath().toString() + "{id}" + "/" + titleURI);
                else
                    locationElement.setTextContent(context.getAbsolutePath().toString() + id + "/" + titleURI);
                root.appendChild(itemElement);
                root.appendChild(locationElement);
                responseDoc.appendChild(root);
                try{
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty("encoding", "UTF-8");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    transformer.transform(new DOMSource(responseDoc), new StreamResult(stream));
                    xmlString = stream.toString();        
                }
                catch (TransformerConfigurationException e) {  
                    e.printStackTrace();  
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                    return response;
                } 
                catch (TransformerException e) {  
                    e.printStackTrace();  
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                    return response;
                }                
                response = Response.status(Response.Status.CREATED).entity(xmlString).build();
                return response;
                        //     BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("grading items", gradeItem));
//                if(type.equalsIgnoreCase("assignments")){
//                    while(cursor.hasNext())
//                        studentlist.update(cursor.next(), set);
//                }
//                else if(type.equalsIgnoreCase("quizzes")){
//                
//                }
//                else if(type.equalsIgnoreCase("exams")){
//                
//                }
//                else if(type.equalsIgnoreCase("paper")){
//                
//                }
//                else{
//                
//                }
            }
            else{
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                return response;
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
        response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        return response;        
    } 
    
    @Path("/{studentId}/{item}")
    @DELETE
    @Produces("application/xml")
    public Response deleteGradingItem(@PathParam("studentId") String id, @PathParam("item") String item){
        Response response;
        String grade;
        
        int idnum = Integer.parseInt(id);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        field.put("studentid", Integer.parseInt(id));
        field.put(item, 1);
        BasicDBObject obj;
        DBCursor cursor = studentlist.find(query, field);
        if(cursor.hasNext()){
            obj = (BasicDBObject)((BasicDBObject)(cursor.next())).get(item);
            System.out.println("obj: " + obj.toString());
            grade = obj.getString("grade");
            studentlist.update(new BasicDBObject("studentid", idnum), new BasicDBObject("$unset", new BasicDBObject(item, 1)));
        }
        else{
            response = Response.status(Response.Status.NOT_FOUND).build();
            return response;
        }
        
        field = new BasicDBObject();
        field.put("total", 1);
        cursor = studentlist.find(query, field);
        String gradeStored = ((BasicDBObject)(cursor.next())).getString("total");
        double gradenum;
        if(gradeStored.equals(""))
            gradenum = 0;
        else
            gradenum = Double.parseDouble(gradeStored);
        System.out.println("grade: " + grade);
        Double newGrade = Double.parseDouble(grade);
        if(item.indexOf("Assignment") >= 0){
            gradenum -= (Double)(newGrade / 4 * 0.3);
        }
        else if(item.indexOf("Quiz") >= 0){
            gradenum -= (Double)(newGrade / 3 * 0.05);
        }
        else if(item.equalsIgnoreCase("Midterm") || item.equalsIgnoreCase("Final")){
            gradenum -= (Double)(newGrade * 0.25);
        }
        else{
            gradenum -= (Double)(newGrade * 0.15);
        }
        studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("total", gradenum)));

        field = new BasicDBObject();
        field.put("possible", 1);
        cursor = studentlist.find(query, field);
        String possibleStored = ((BasicDBObject)(cursor.next())).getString("possible");
        double gradePossible;
        if(gradeStored.equals(""))
            gradePossible = 0;
        else
            gradePossible = Double.parseDouble(gradeStored);
        if(item.indexOf("Assignment") >= 0){
            gradePossible -= 7.5;
        }
        else if(item.indexOf("Quiz") >= 0){
            gradePossible -= 1.7;
        }
        else if(item.equalsIgnoreCase("Midterm") || item.equalsIgnoreCase("Final")){
            gradePossible -= 25;
        }
        else{
            gradePossible -= 15;
        }
        studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("possible", gradePossible)));

        int total = (int)((newGrade / gradePossible) * 100);
        if(total >= 99)  
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A+")));
        else if(total >= 95) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A")));
        else if(total >= 90)
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "A-")));
        else if(total >= 87) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B+")));
        else if(total >= 84) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B")));
        else if(total >= 80) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "B-")));
        else if(total >= 75) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "C+")));
        else if(total >= 70) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "C")));
        else if(total >= 60) 
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "D")));
        else  
            studentlist.update(new BasicDBObject("studentid", Integer.parseInt(id)), new BasicDBObject("$set", new BasicDBObject("letter", "E")));
        
        response = Response.status(Response.Status.NO_CONTENT).build();
        return response;
    }
    
    @Path("releaseditems")
    @GET
    @Produces("application/xml")
    public Response getReleasedGradingItem() throws UnknownHostException {
        String xmlString = new String();
        Response response;
        String item;
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        BasicDBObject obj;
        
        mongoClient = new MongoClient();
        db = mongoClient.getDB("gradebook");
        studentlist = db.getCollection("students");
        itemlist = db.getCollection("items");
        itemlist = db.getCollection("items"); 
        if(itemlist == null){
            
            response = Response.status(Response.Status.NOT_FOUND).build();
            return response;
        }
            
        DBCursor cursor = itemlist.find(query, field);
        if(cursor.hasNext() == false){
            response = Response.status(Response.Status.NOT_FOUND).build();
            return response;
        }
        try{
            Document responseDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = responseDoc.createElement("ItemReleased");
            Element itemElement;
            
            while(cursor.hasNext()){
                obj = (BasicDBObject)(cursor.next());
                if(obj.getString("Assignment") != null){
                    item = obj.getString("Assignment");
                }
                else if(obj.getString("Quiz") != null){
                    item = obj.getString("Quiz");
                }
                else if(obj.getString("Exam") != null){
                    item = obj.getString("Exam");
                }
                else{
                    item = obj.getString("Paper");
                }
                itemElement = responseDoc.createElement("Item");
                itemElement.setTextContent(item);
                root.appendChild(itemElement);
            }
            responseDoc.appendChild(root);
            try{
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                transformer.transform(new DOMSource(responseDoc), new StreamResult(stream));
                xmlString = stream.toString();        
            }
            catch (TransformerConfigurationException e) {  
                e.printStackTrace();  
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                return response;
            } 
            catch (TransformerException e) {  
                e.printStackTrace();  
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                return response;
            }          
            response = Response.status(Response.Status.OK).entity(xmlString).build();
            return response;
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace(); 
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            return response;
        }
    }
}
