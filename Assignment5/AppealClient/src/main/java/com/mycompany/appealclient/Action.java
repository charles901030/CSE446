/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient;
import com.mycompany.appealclient.model.Appeal;
import com.mycompany.appealclient.model.AppealRepresentation;
import com.mycompany.appealclient.model.Approval;
import com.mycompany.appealclient.model.Decision;
import com.mycompany.appealclient.model.DecisionRepresentation;
import com.mycompany.appealclient.model.DecisionStatus;
import com.mycompany.appealclient.model.Grade;
import com.mycompany.appealclient.model.Id;
import com.mycompany.appealclient.model.IdRepresentation;
import com.mycompany.appealclient.model.Link;
import com.mycompany.appealclient.model.Reminder;
import com.mycompany.appealclient.model.ReminderRepresentation;
import com.mycompany.appealclient.model.Uri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.ws.rs.core.Request;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.Scanner;
import java.util.logging.Level;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shaosh
 */
public class Action {
    
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource webResource = client.resource(Uri.BASEURI);
    private static int counter = 0;
//    private static Client client;
    
    private static Request request;
    private static final Logger LOG = Logger.getLogger(Action.class.getName());
    
//    public Action(){
//        ClientConfig config = new DefaultClientConfig();
//        client = Client.create(config);
//        webResource = client.resource(Uri.BASEURI);
//    }
    
    public static AppealRepresentation CreateAppeal(String xmlString){
        webResource = client.resource(Uri.APPEALURI);
        ClientResponse response = null;
        response = webResource.type(Uri.APPEAL_MEDIATYPE).post(ClientResponse.class, xmlString);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 201){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;        
    }
    
    public static AppealRepresentation CreateBadStart(String xmlString){
        webResource = client.resource(Uri.BADSTARTURI);
        ClientResponse response = null;
        response = webResource.type(Uri.APPEAL_MEDIATYPE).post(ClientResponse.class, xmlString);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 201){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;        
    }
    
    public static AppealRepresentation XmlToAppealRepresentation(String xmlString){
        AppealRepresentation appealRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert AppealRepresentation to Xml");
        }
        return appealRepresentation;
    }
    
    public static DecisionRepresentation XmlToDecisionRepresentation(String xmlString){
        LOG.info("Start convert Xml to DecisionRepresentation");
        DecisionRepresentation decisionRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(DecisionRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            decisionRepresentation = (DecisionRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert Xml to DecisionRepresentation");
        }
        LOG.info("Complete convert Xml to DecisionRepresentation");
        return decisionRepresentation;
    }
    
    public static ReminderRepresentation XmlToReminderRepresentation(String xmlString){
        LOG.info("Start convert Xml to Reminder");
        ReminderRepresentation reminderRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(ReminderRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reminderRepresentation = (ReminderRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert Xml to Reminder");
        }
        LOG.info("Complete convert Xml to Reminder");
        return reminderRepresentation;
    }
    
    public static NameItemGrade XmlToNameItemGrade(String xmlString){
        LOG.info("Start convert Xml to NameItemGrade");
        NameItemGrade nameItemGrade = null;
        try{
            JAXBContext context = JAXBContext.newInstance(NameItemGrade.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            nameItemGrade = (NameItemGrade) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert Xml to NameItemGrade");
        }
        LOG.info("Complete convert Xml to NameItemGrade");
        return nameItemGrade;
    }
    
    public static AppealRepresentation UpdateAppeal(String xmlString, /*Id id,*/ URI uri){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(uri);
        ClientResponse response = null;
//        response = webResource.path("pending").path(id.toString()).type(Uri.APPEAL_MEDIATYPE).post(ClientResponse.class, xmlString);
        response = webResource.type(Uri.APPEAL_MEDIATYPE).post(ClientResponse.class, xmlString);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class); 
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;
    }
    
    public static AppealRepresentation DeleteAppeal(URI uri){//Id id){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource./*path("pending").path(id.toString()).*/type(Uri.APPEAL_MEDIATYPE).delete(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;
    } 
    
    public static AppealRepresentation GetAppeal(/*Id id, String option,*/ URI uri){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(uri);
        ClientResponse response = null;
//        if(option.equalsIgnoreCase("submitted"))
//            response = webResource.path("submitted").path(id.toString()).type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
//        else if (option.equalsIgnoreCase("pending"))
//            response = webResource.path("pending").path(id.toString()).type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        response = webResource.type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;
    }
    
    public static AppealRepresentation SubmitAppeal(URI uri){//Id id){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource/*.path("pending").path(id.toString())*/.type(Uri.APPEAL_MEDIATYPE).put(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;
    }
    
    public static ReminderRepresentation GetReminders(){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(Uri.REMINDERURI);
        ClientResponse response = null;
        response = webResource/*.path("submitted")*/.type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToReminderRepresentation(entity);
        }
        return null;
    }
    
    public static ArrayList<DecisionRepresentation> ProcessReminders(ArrayList<Reminder> reminders){
        String xmlString = null;
        ArrayList<DecisionRepresentation> drList = new ArrayList();
        for(int i = 0; i < reminders.size(); i++){
            Reminder reminder = reminders.get(i);
            Appeal appeal = reminder.getAppeal();
            Id id = reminder.getId();
            Link link = reminder.getLink();
            URI uri = null;
            try {
                uri = link.getUri();
            } 
            catch (URISyntaxException ex) {
                Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
            xmlString = ProcessAppeal(appeal, id);
            DecisionRepresentation dr = SubmitDecision(xmlString, uri);
            drList.add(dr);
        }
        return drList;
    }
    
    public static AppealRepresentation GetAppealtoProcess(URI uri){
//        webResource = client.resource(Uri.APPEALURI);
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource/*.path("submitted")*/.type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToAppealRepresentation(entity);
        }
        return null;
    }
    
    public static String ProcessAppeal(AppealRepresentation appealRepresentation){
        LOG.info("Professor read the appeal:");
        LOG.info(appealRepresentation.toString());
        int approveFlag;
        String comment = "";
        Grade grade = null;
        Decision decision = null;
        DecisionRepresentation decisionRepresentation = null;
        Appeal appeal = appealRepresentation.getAppeal();
        
//        Scanner scan = new Scanner(System.in);
        LOG.info("Do you approve this appeal? (Y/N)");
        counter++;
        approveFlag = /*(int)(Math.random())*/ counter % 2;
//        approveFlag = scan.nextLine();
//        while(approveFlag.equalsIgnoreCase("y") == false && approveFlag.equalsIgnoreCase("n") == false){
//            LOG.info("Do you approve this appeal? (Y/N)");
//            approveFlag = scan.next();
//        } 
        
        Approval approval = null;
//        if(approveFlag.equalsIgnoreCase("y"))
        if(approveFlag == 1)
            approval = Approval.Yes;
        else
            approval = Approval.No;
       
        LOG.info("Please write down your comments: ");
        if(approveFlag == 1)
            comment += "My mistake.";
        else
            comment += "Your fault.";
//        while(scan.hasNextLine()){
//        comment += scan.nextLine();
//        }
        
        if(approval == Approval.Yes){
            LOG.info("Please enter the new grade in integer: ");
            grade = new Grade((int)(Math.random() * 10) + 90);
//            do{
//                LOG.info("Please enter the new grade in integer: ");
//                if(scan.hasNextInt()){
//                    grade = new Grade(scan.nextInt());
//                    break;
//                }
//            }while(scan.hasNextInt() == false);
            decision = new Decision(appeal.getName(), appeal.getGradingItem(), approval, comment, grade);        
        }
        else
            decision = new Decision(appeal.getName(), appeal.getGradingItem(), approval, comment, null);
        decisionRepresentation = new DecisionRepresentation(decision, DecisionStatus.Unretrieved, appealRepresentation.getId());
        String xmlString = decisionRepresentation.DecisionRepresentationToXml();
        return xmlString;
    }   
    
    public static String ProcessAppeal(Appeal appeal, Id id){
        LOG.info(appeal.toString());
//        String approveFlag = null;
        int approveFlag;
        String comment = "";
        Grade grade = null;
        Decision decision = null;
        DecisionRepresentation decisionRepresentation = null;
        Scanner scan = new Scanner(System.in); 
        LOG.info("Do you approve this appeal? (Y/N)");
        counter++;
        approveFlag = /*(int)(Math.random())*/counter % 2;
        LOG.info(String.valueOf(approveFlag));
//        approveFlag = scan.nextLine();
//        
//        while(approveFlag.equalsIgnoreCase("y") == false && approveFlag.equalsIgnoreCase("n") == false);{
//            LOG.info("Do you approve this appeal? (Y/N)");
//            approveFlag = scan.nextLine();
//        } 
        
        Approval approval = null;
        
//        if(approveFlag.equalsIgnoreCase("y"))
        if(approveFlag == 1)
            approval = Approval.Yes;
        else
            approval = Approval.No;
//        if(approveFlag.equalsIgnoreCase("y"))
//            approval = Approval.Yes;
//        else
//            approval = Approval.No;
       
        LOG.info("Please write down your comments: ");
//        comment += scan.nextLine();
        if(approveFlag == 1)
            comment += "My mistake.";
        else
            comment += "Your fault.";
        LOG.info(comment);
        
        if(approval == Approval.Yes){
            LOG.info("Please enter the new grade in integer: ");
            grade = new Grade((int)(Math.random() * 10) + 90);
            LOG.info(grade.toString());
//            do{
//                LOG.info("Please enter the new grade in integer: ");
//                if(scan.hasNextInt()){
//                    grade = new Grade(scan.nextInt());
//                    break;
//                }
//            }while(scan.hasNextInt() == false);
            decision = new Decision(appeal.getName(), appeal.getGradingItem(), approval, comment, grade);        
        }
        else
            decision = new Decision(appeal.getName(), appeal.getGradingItem(), approval, comment, null);
        decisionRepresentation = new DecisionRepresentation(decision, DecisionStatus.Unretrieved, id);
        String xmlString = decisionRepresentation.DecisionRepresentationToXml();
        return xmlString;
    }
    
    public static DecisionRepresentation SubmitDecision(String xmlString, URI uri){
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource.type(Uri.APPEAL_MEDIATYPE).put(ClientResponse.class, xmlString);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 201){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToDecisionRepresentation(entity);
        }
        return null;
    }
    
    public static DecisionRepresentation GetDecision(URI uri){
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource.type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entity = response.getEntity(String.class);
            LOG.info("Response entity in XML:");
            LOG.info(entity);
            return XmlToDecisionRepresentation(entity);
        }
        return null;
    }
    
    public static NameItemGrade GetGrade(URI uri, Id id) throws URISyntaxException{
        webResource = client.resource(uri);
        ClientResponse response = null;
        response = webResource.type(Uri.APPEAL_MEDIATYPE).get(ClientResponse.class);
        int status = response.getStatus();
        String mediaType = response.getType().toString();
        LOG.info(String.valueOf(status));
        LOG.info(mediaType);
        if(status == 200){
            String entityGrade = response.getEntity(String.class);
            LOG.info(entityGrade);
            URI uriPending = null;
            try {
                uriPending = new URI(Uri.APPEALURI);
            } 
            catch (URISyntaxException ex) {
                Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
//            LOG.info("Start delete the appeals in pending repository and submitted repository");
//            IdRepresentation ir = new IdRepresentation(id);
//            String xmlString = ir.IdRepresentationToXml();
//            webResource = client.resource(uriPending);
//            response = webResource.type(Uri.APPEAL_MEDIATYPE).delete(ClientResponse.class, xmlString);  
//            status = response.getStatus();
//            mediaType = response.getType().toString();
//            LOG.info(String.valueOf(status));
//            LOG.info(mediaType);
//            LOG.info("Complete delete the appeals in pending repository and submitted repository");
            return XmlToNameItemGrade(entityGrade);
        }
        return null;
    }
    
    
}
