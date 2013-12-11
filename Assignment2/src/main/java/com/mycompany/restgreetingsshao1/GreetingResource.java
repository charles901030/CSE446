/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restgreetingsshao1;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.logging.*;

/**
 * REST Web Service
 *
 * @author shaosh
 */
@Path("greeting")
public class GreetingResource {
    private static  final Logger LOG =  Logger.getLogger(GreetingResource.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GreetingResource() {
        LOG.info("Creating a Greeting Resource");        
    }

    /**
     * Retrieves representation of an instance of com.mycompany.test.GenericResource
     * @return an instance of java.lang.String
     */
    @Path("Application/JSON")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(){// throws JSONException{
        //TODO return proper representation object
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("greeting message","Hi There, I am application/json");
//        jsonObject.put("h1","Greeting Message");
//        jsonObject.put("title","REST Message");
        String greeting = "\"Greeting\": {\n"
                +                           "\t\t\"title\": \"REST Message\"\n "
                +                           "\t\t\"h1\": \"Greeting Message\"\n "
                +                           "\t\t\"greeting message\": \"Hi There, I am application/json\"}\n";
        LOG.info("Retrieving the Json Greeting \"" + greeting.toString() + "\"");
        return greeting.toString();
    }   
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void putJson(String content) {
//    }
    
    @Path("Text/Html")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        //TODO return proper representation object
        String titleMessage = "<title>REST Message</title>";
        String headerMessage = "<h1>Greeting Message</h1>";
        String greetingMessage = "Hi There, I am text/html";
        LOG.info("Retrieving the Text/Html Greeting \"" + greetingMessage + "\"");
        return "<html>" + titleMessage + "<body>" + headerMessage + greetingMessage + "</body>" + "</html>";
    } 

    /**
     * PUT method for updating or creating an instance of TextHtmlGreetingResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @PUT
//    @Consumes(MediaType.TEXT_HTML)
//    public void putHtml(String content) {
//    }
    
    @Path("Application/Xml")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAppXml() {
        
        //TODO return proper representation object
        String greeting = "<xml><title>REST Message</title><body><h1>Greeting Message</h1><greetingmessage>Hi There, I am application/xml</greetingmessage></body></xml>";
        LOG.info("Retrieving the App/Xml Greeting \"" + greeting + "\"");
        return greeting;
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putAppXml(String content) {
//    }
//    
    @Path("Text/Plain")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        String titleMessage = "REST Message\n";
        String headerMessage = "Greeting Message\n";
        String greetingMessage = "Hi There, I am text/plain";
        LOG.info("Retrieving the Text/Plain Greeting \"" + greetingMessage + "\"");
        return titleMessage +  headerMessage + greetingMessage;
    }

    /**
     * PUT method for updating or creating an instance of TextPlainGreetingResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @PUT
//    @Consumes(MediaType.TEXT_PLAIN)
//    public void putText(String content) {
//    }
    
    @Path("Text/Xml")
    @GET
    @Produces(MediaType.TEXT_XML)
    public String getTextXml() {
        //TODO return proper representation object       
        String greeting = "<?xml version=\"1.0\" encoding=\"utf-8\"?><xml><title>REST Message</title><body><h1>Greeting Message</h1><greetingmessage>Hi There, I am text/xml</greetingmessage></body></xml>";
//        XStream xml = new XStream();
//        String xmlString = xml.toXML(greeting);
        
        LOG.info("Retrieving the Text/Xml Greeting \"" + greeting + "\"");
        return greeting;
    }

    /**
     * PUT method for updating or creating an instance of TextPlainGreetingResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @PUT
//    @Consumes(MediaType.TEXT_XML)
//    public void putTextXml(String content) {
//    }
}

