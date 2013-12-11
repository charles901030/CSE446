/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.Appeal;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import com.mycompany.appealserver.model.Uri;
import com.mycompany.appealserver.model.AppealRepresentation;
import com.mycompany.appealserver.model.AppealStatus;
import com.mycompany.appealserver.model.Decision;
import com.mycompany.appealserver.model.DecisionRepresentation;
import com.mycompany.appealserver.model.DecisionStatus;
import com.mycompany.appealserver.model.Grade;
import com.mycompany.appealserver.model.GradingItem;
import com.mycompany.appealserver.model.Id;
import com.mycompany.appealserver.model.IdRepresentation;
import com.mycompany.appealserver.model.Link;
import com.mycompany.appealserver.model.Name;
import com.mycompany.appealserver.model.Reminder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
/**
 * REST Web Service
 *
 * @author shaosh
 */
@Path("appeal")
public class AppealResource {
    private static final Logger LOG = Logger.getLogger(AppealResource.class.getName());
    public static AppealRepresentationRepository pendingRepository= new AppealRepresentationRepository();
    public static AppealRepresentationRepository submittedRepository= new AppealRepresentationRepository();
    public static DecisionRepository decisionRepository = new DecisionRepository();
    public static ReminderRepository reminderRepository = new ReminderRepository();
    public static GradebookRepository gradebook = new GradebookRepository();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AppealResource
     */
    public AppealResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.appealserver.AppealResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("submitted/{appealId}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getSubmittedAppeal(@PathParam("appealId") Id id) {
        LOG.info("Start getSubmittedAppeal");
        Response response = null;
        AppealRepresentation appealRepresentation = submittedRepository.getAppealRepresentation(id);
        if(appealRepresentation == null){
            LOG.info("Fail to getSubmittedAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to getSubmittedAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else{
            LOG.info("Get appealRepresentation in submittedRepository");
            LOG.info(appealRepresentation.AppealRepresentationToXml());
            ArrayList<Link> links = new ArrayList();
            URI uriSubmitted = null;
            URI uriDecision = null;
            try{
                uriSubmitted = new URI(Uri.SUBMITTEDURI + "/" + id);
                uriDecision = new URI(Uri.DECISIONURI + "/" + id);
            }
            catch (URISyntaxException e){
                e.printStackTrace();
                LOG.info("Fail to create Pending URI");
                LOG.info("============================================================================================================");
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(Uri.APPEAL_MEDIATYPE).build();
                return response;
            }
            Link selfLink = new Link("self", uriSubmitted, Uri.APPEAL_MEDIATYPE);
            links.add(selfLink);
            Appeal appeal = appealRepresentation.getAppeal();
            AppealStatus status = appealRepresentation.getStatus();
            String reminderString = null;
            if(appealRepresentation.getStatus().toString().equals(AppealStatus.Submitted.toString())){
                Reminder reminder = new Reminder(appeal, id);
            //    String reminderString = reminder.send();
                reminderString = reminder.ReminderToXml();
                reminderRepository.addReminder(id, reminder);
                
                appealRepresentation.setStatus(AppealStatus.Reminded);
                appealRepresentation.setLinks(links);
                String xmlString = appealRepresentation.AppealRepresentationToXml();
                LOG.info("Create reminder");
                LOG.info(reminderString);
                LOG.info("Set appealRepresentation status to reminded and change its links"); 
                LOG.info(xmlString);
                LOG.info("Complete getSubmittedAppeal");
                LOG.info("============================================================================================================");
                return Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
            }
            else if(appealRepresentation.getStatus().toString().equals(AppealStatus.Processed.toString())){
                Link decisionLink = new Link("decision", uriDecision, Uri.APPEAL_MEDIATYPE);
                links.add(decisionLink);
            }
            else if(appealRepresentation.getStatus().toString().equals(AppealStatus.Inprogress.toString())){
            }
            else{
                LOG.info("Invalid AppealStatus");
                LOG.info("============================================================================================================");
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(Uri.APPEAL_MEDIATYPE).build();
                return response;
            }
            AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, status, links, id);
            String xmlString = newAppealRepresentation.AppealRepresentationToXml();
            LOG.info("The appealRepresentation to be returned"); 
            LOG.info(xmlString);
            response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Complete getSubmittedAppeal");
            LOG.info("============================================================================================================");
            return response;
        }        
    }
    
    @GET
    @Path("pending/{appealId}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getPendingAppeal(@PathParam("appealId") Id id) {
        LOG.info("Start getPendingAppeal");
        Response response = null;
        AppealRepresentation appealRepresentation = pendingRepository.getAppealRepresentation(id);
        if(appealRepresentation == null){
            LOG.info("Fail to getPendingAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to getPendingAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else{
            LOG.info("Get appealRepresentation in pendingRepository");
            LOG.info(appealRepresentation.AppealRepresentationToXml());
            ArrayList<Link> links = new ArrayList();
            URI uriPending = null;
            URI uriSubmitted = null;
            try{
                uriPending = new URI(Uri.PENDINGURI + "/" + id);
                uriSubmitted = new URI(Uri.SUBMITTEDURI);
            }
            catch (URISyntaxException e){
                e.printStackTrace();
                LOG.info("Fail to create Pending URI");
                LOG.info("============================================================================================================");
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(Uri.APPEAL_MEDIATYPE).build();
                return response;
            }
            Link selfLink = new Link("self", uriPending, Uri.APPEAL_MEDIATYPE);
            Link updateLink = new Link("update", uriPending, Uri.APPEAL_MEDIATYPE);
            Link deleteLink = new Link("delete", uriPending, Uri.APPEAL_MEDIATYPE);
            Link submitLink = new Link("submit", uriPending, Uri.APPEAL_MEDIATYPE);
            links.add(selfLink);
            links.add(updateLink);
            links.add(deleteLink);
            links.add(submitLink);
            Appeal appeal = appealRepresentation.getAppeal();
            AppealStatus status = appealRepresentation.getStatus();
            
            AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, status, links, id);
            String xmlString = newAppealRepresentation.AppealRepresentationToXml();
            LOG.info("The appealRepresentation to be returned"); 
            LOG.info(xmlString);
            response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Complete getPendingAppeal");
            LOG.info("============================================================================================================");
            return response;
        }        
    }

    @GET
    @Path("submitted")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getAppealtoProcess() {
        LOG.info("Start getAppealtoProcess");
        Response response = null;
        AppealRepresentation appealRepresentation = submittedRepository.getAppealRepresentation();
        if(appealRepresentation == null){
            LOG.info("Fail to getAppealtoProcess because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to getAppealtoProcess because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else{
            LOG.info("Get appealRepresentation in submittedRepository");
            LOG.info(appealRepresentation.AppealRepresentationToXml());
            Appeal appeal = appealRepresentation.getAppeal();
            AppealStatus status = appealRepresentation.getStatus();
            Id id = appealRepresentation.getId();
            
            ArrayList<Link> links = new ArrayList();            
            Link decisionLink;
            try {
                decisionLink = new Link("decision", new URI(Uri.SUBMITTEDURI + "/" + id), Uri.APPEAL_MEDIATYPE);
            } 
            catch (URISyntaxException ex) {
                Logger.getLogger(AppealResource.class.getName()).log(Level.SEVERE, null, ex);
                LOG.info("Fail to getAppealtoProcess because of internal server error");
                LOG.info("============================================================================================================");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(Uri.APPEAL_MEDIATYPE).build();
            }
            links.add(decisionLink);
            
            appealRepresentation.setStatus(AppealStatus.Inprogress);
            submittedRepository.updateAppealRepresentation(id, appealRepresentation);
            
            LOG.info("Set appealRepresentation status to inprogress"); 
            LOG.info(appealRepresentation.AppealRepresentationToXml());
            
            AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, AppealStatus.Inprogress, links, id);
            String xmlString = newAppealRepresentation.AppealRepresentationToXml();
            LOG.info("The appealRepresentation to be returned"); 
            LOG.info(xmlString);
            response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Complete getAppealtoProcess");
            LOG.info("============================================================================================================");
            return response;
        }        
    }
    /**
     * PUT method for updating or creating an instance of AppealResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
//    @DELETE
//    @Consumes(Uri.APPEAL_MEDIATYPE)
//    @Produces(Uri.APPEAL_MEDIATYPE)
//    public Response deleteProcessedAppeal(String content) {
//        LOG.info("Start deleteProcessedAppeal");
//        IdRepresentation idRepresentation = Action.XmlToIdRepresentation(content);
//        if(idRepresentation == null){
//            LOG.info("Fail to deleterocessedAppeal because of not found");
//            LOG.info("============================================================================================================");
//            return Response.status(Response.Status.BAD_REQUEST).type(Uri.APPEAL_MEDIATYPE).build();
//        }
//        Id id = idRepresentation.getId();
//        AppealRepresentation appealRepresentation = pendingRepository.getAppealRepresentation(id);
//        if(appealRepresentation == null){
//            LOG.info("Fail to deleterocessedAppeal because of not found");
//            LOG.info("============================================================================================================");
//            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
//        }
//        appealRepresentation.setStatus(AppealStatus.Deleted);
//        pendingRepository.updateAppealRepresentation(id, appealRepresentation);
//        
//        appealRepresentation = submittedRepository.getAppealRepresentation(id);
//        appealRepresentation.setStatus(AppealStatus.Deleted);
//        submittedRepository.updateAppealRepresentation(id, appealRepresentation);
//        LOG.info("Complete deleteProcessedAppeal");
//        LOG.info("============================================================================================================");
//        return Response.status(Response.Status.NO_CONTENT).type(Uri.APPEAL_MEDIATYPE).build();
//    }
    
    @DELETE
    @Path("pending/{appealId}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response deleteAppeal(@PathParam("appealId") Id id) {
        LOG.info("Start deleteAppeal");
        Response response = null;
        AppealRepresentation appealRepresentation = pendingRepository.getAppealRepresentation(id);
        if(appealRepresentation == null){
            LOG.info("Fail to deleteAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to deleteAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else{
            LOG.info("Get the appealRepresentation to be deleted");
            LOG.info(appealRepresentation.AppealRepresentationToXml());
            AppealRepresentation newAppealRepresentation = pendingRepository.getAppealRepresentation(id);
            pendingRepository.removeAppealRepresentation(id);
            newAppealRepresentation.setStatus(AppealStatus.Deleted);
            if(newAppealRepresentation.getId() == null)
                newAppealRepresentation.setId(id);
            if(newAppealRepresentation.getLinks() == null){
                ArrayList<Link> links = new ArrayList();
                URI uriSelf = null;
                try {
                    uriSelf = new URI( Uri.PENDINGURI + "/" + id);
                } 
                catch (URISyntaxException ex) {
                    Logger.getLogger(AppealResource.class.getName()).log(Level.SEVERE, null, ex);
                }
//                Link link = new Link("self", uriSelf, Uri.APPEAL_MEDIATYPE);
//                links.add(link);
//                newAppealRepresentation.setLinks(links);
            }
            newAppealRepresentation.setLinks(null);
            String xmlString = newAppealRepresentation.AppealRepresentationToXml();
            LOG.info("The appealRepresentation to be returned");
//            LOG.info("The self link is added for test deletion in the next step. In actual situation there is no link");
            LOG.info(xmlString);
            response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Complete deleteAppeal");
            LOG.info("============================================================================================================");
            return response;
        }
    }
        
        
    @PUT
    @Path("submitted/{appealId}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response createDecision(@PathParam("appealId") Id id, String content) {
        LOG.info("Start createDecision");
        Response response = null;
        DecisionRepresentation decisionRepresentation = Action.XmlToDecisionRepresentation(content);
        if(decisionRepresentation == null){
            response = Response.status(Response.Status.BAD_REQUEST).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Fail to createDecision because of bad request");
            LOG.info("============================================================================================================");
            return response;
        }
        LOG.info("The decisionRepresentation recieved");
        LOG.info(content);
        Decision decision = decisionRepresentation.getDecision();
        Grade grade = decision.getGrade();
        Link link = null;
        ArrayList<Link> links = new ArrayList();
        if(grade == null){
            URI uriCreate = URI.create(Uri.APPEALURI);
            link = new Link("create", uriCreate, Uri.APPEAL_MEDIATYPE);
            links.add(link);
        }
        else{
            Name name = decision.getName();
            GradingItem gradingItem = decision.getgradingItem();
            gradebook.addGrade(new NameItem(name, gradingItem), grade);
            URI uriGradebook = URI.create(Uri.GRADEBOOKURI + "/" + name + "/" + gradingItem);   
            System.out.println(uriGradebook.toString());
            link = new Link("updatedgrade", uriGradebook, Uri.APPEAL_MEDIATYPE);
            links.add(link);
            URI uriCreate = URI.create(Uri.PENDINGURI);
            link = new Link("create", uriCreate, Uri.APPEAL_MEDIATYPE);
            links.add(link);
        }
        URI uriDecision = URI.create(Uri.DECISIONURI + "/" + id);
        Link linkDecision = new Link("decision", uriDecision, Uri.APPEAL_MEDIATYPE);
        links.add(linkDecision);
        
        AppealRepresentation appealRepresentation = submittedRepository.getAppealRepresentation(id);
        LOG.info("Get the appealRepresentation from submittedRepository");
        LOG.info(appealRepresentation.AppealRepresentationToXml());
        appealRepresentation.setStatus(AppealStatus.Processed);
        LOG.info("Get the appealRepresentation status to processed");
        LOG.info(appealRepresentation.AppealRepresentationToXml());
        submittedRepository.updateAppealRepresentation(id, appealRepresentation);
        
        DecisionRepresentation newDecisionRepresentation = new DecisionRepresentation(decision, DecisionStatus.Unretrieved, id, links);
        decisionRepository.addDecisionRepresentation(id, newDecisionRepresentation);
        String xmlString = newDecisionRepresentation.DecisionRepresentationToXml();
        LOG.info("The decisionRepresentation to be returned");
        LOG.info(xmlString);
        LOG.info("Complete createDecision");
        LOG.info("============================================================================================================");
        return Response.status(Response.Status.CREATED).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
    }
    
    @POST
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response createAppeal(String content) {
        LOG.info("Start createAppeal");
        Response response = null;
        AppealRepresentation appealRepresentation = Action.XmlToAppealRepresentation(content);
        if(appealRepresentation == null){
            response = Response.status(Response.Status.BAD_REQUEST).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Fail to createAppeal because of bad request");
            LOG.info("============================================================================================================");
            return response;
        }
        LOG.info("The appealRepresentation received");
        LOG.info(content);
        Appeal appeal = appealRepresentation.getAppeal();
        AppealStatus status = appealRepresentation.getStatus();
        Id id;
        ArrayList<Link> links;
        
//        ArrayList<Link> linkList = appealRepresentation.getLinks();
//        if(linkList == null){
        id = pendingRepository.addAppealRepresentation(appealRepresentation);
        URI uriPending = null;
        URI uriSubmitted = null;
//        try{
        uriPending = URI.create(Uri.PENDINGURI + "/" + id);//new URI(Uri.PENDINGURI + "/" + id);  
        uriSubmitted = URI.create(Uri.SUBMITTEDURI);//new URI(Uri.SUBMITTEDURI + "/" + id);
//        }
//        catch (URISyntaxException e){
//            e.printStackTrace();
//            LOG.info("Fail to create Pending URI");
//            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//            return response;
//        } 
        Link selfLink = new Link("self", uriPending, Uri.APPEAL_MEDIATYPE);
        Link updateLink = new Link("update", uriPending, Uri.APPEAL_MEDIATYPE);
        Link deleteLink = new Link("delete", uriPending, Uri.APPEAL_MEDIATYPE);
        Link submitLink = new Link("submit", uriPending, Uri.APPEAL_MEDIATYPE);
        links = new ArrayList();
        links.add(selfLink);
        links.add(updateLink);
        links.add(deleteLink);
        links.add(submitLink);
//        }
//        else{
//            id = appealRepresentation.getId();
//            boolean updateFlag = pendingRepository.updateAppeal(id, appeal);
//            if(updateFlag == false){
//                response = Response.status(Response.Status.NOT_FOUND).build();
//                return response;
//            }
//            links = linkList;
//        }
        AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, status, links, id);
        String xmlString = newAppealRepresentation.AppealRepresentationToXml();
        LOG.info("The appealRepresentation to be returned");
        LOG.info(xmlString);
//        if(linkList == null)
//            response = Response.status(Response.Status.CREATED).entity(xmlString).build();
//        else
        response = Response.status(Response.Status.CREATED).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
        LOG.info("Complete createAppeal");
        LOG.info("============================================================================================================");
        return response;
    }    
    
    
    @POST
    @Path("pending/{appealId}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response updateAppeal(@PathParam("appealId") Id id, String content) {
        LOG.info("Start updateAppeal");
//        AppealRepresentation pendingAR = pendingRepository.getAppealRepresentation(id);
//        if(pendingAR != null){
//            if(pendingAR.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString()))
//                return Response.status(Response.Status.NOT_FOUND).build();
//            else if(pendingAR.getStatus().toString().equalsIgnoreCase(AppealStatus.Submitted.toString()))
//                return Response.status(Response.Status.FORBIDDEN).build();
//        } 
//        AppealRepresentation submittedAR = submittedRepository.getAppealRepresentation(id);
        
        Response response = null;
        AppealRepresentation appealRepresentation = Action.XmlToAppealRepresentation(content);
        if(appealRepresentation == null){
            response = Response.status(Response.Status.BAD_REQUEST).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Fail to updateAppeal because of bad request");
            LOG.info("============================================================================================================");
            return response;
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to updateAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        

        Appeal appeal = appealRepresentation.getAppeal();
        AppealStatus status = appealRepresentation.getStatus();
        boolean updateFlag = pendingRepository.updateAppealRepresentation(id, appealRepresentation);
        if(updateFlag == false){
            response = Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Fail to updateAppeal because of not found");
            LOG.info("============================================================================================================");
            return response;
        }
        ArrayList<Link> links = appealRepresentation.getLinks();
        
        AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, status, links, id);
        String xmlString = newAppealRepresentation.AppealRepresentationToXml();
        
        LOG.info("The appealRepresentation to be returned");
        LOG.info(xmlString);
        response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
        LOG.info("Complete updateAppeal");
        LOG.info("============================================================================================================");
        return response;
    }
    
    @PUT
    @Path("pending/{appealId}")
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response submitAppeal(@PathParam("appealId") Id id) {
        LOG.info("Start submitAppeal");
        Response response = null;
        AppealRepresentation appealRepresentation = pendingRepository.getAppealRepresentation(id);
        if(appealRepresentation == null){
            response = Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
            LOG.info("Fail to submitAppeal because of not found");
            LOG.info("============================================================================================================");
            return response;
        }
        else if(appealRepresentation.getStatus().toString().equalsIgnoreCase(AppealStatus.Deleted.toString())){
            LOG.info("Fail to submitAppeal because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        LOG.info("Get the appealRepresentation from pendingRepository");
        LOG.info(appealRepresentation.AppealRepresentationToXml());
        appealRepresentation.setStatus(AppealStatus.Submitted);
        LOG.info("Set the appealRepresentation status to submitted");
        LOG.info(appealRepresentation.AppealRepresentationToXml());
 //       submittedRepository.addAppealRepresentation(id, appealRepresentation);
        URI uriSubmitted = null;
        uriSubmitted = URI.create(Uri.SUBMITTEDURI + "/" + id);//new URI(Uri.SUBMITTEDURI + "/" + id);
        Link selfLink = new Link("self", uriSubmitted, Uri.APPEAL_MEDIATYPE);
        ArrayList<Link> links = new ArrayList();
        links.add(selfLink);
        
        Appeal appeal = appealRepresentation.getAppeal();
        AppealStatus status = appealRepresentation.getStatus();
        AppealRepresentation newAppealRepresentation = new AppealRepresentation(appeal, status, links, id);
        submittedRepository.addAppealRepresentation(id, newAppealRepresentation);
        String xmlString = newAppealRepresentation.AppealRepresentationToXml();
        LOG.info("Get the appealRepresentation to be returned");
        LOG.info(xmlString);
        response = Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
        LOG.info("Complete submitAppeal");
        LOG.info("============================================================================================================");
        return response;
    }
}
