/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.AppealRepresentation;
import com.mycompany.appealserver.model.Grade;
import com.mycompany.appealserver.model.GradingItem;
import com.mycompany.appealserver.model.Name;
import com.mycompany.appealserver.model.Uri;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author shaosh
 */
@Path("gradebook")
public class GradebookResource {
    private static final Logger LOG = Logger.getLogger(ReminderResource.class.getName());
    private GradebookRepository gradebook = AppealResource.gradebook; 
    private AppealRepresentationRepository pendingRepository= AppealResource.pendingRepository;
    private AppealRepresentationRepository submittedRepository= AppealResource.submittedRepository;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GradebookResource
     */
    public GradebookResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.appealserver.GradebookResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{studentName}/{gradingItem}")
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getGrade(@PathParam("studentName") Name name, @PathParam("gradingItem") GradingItem gradingItem) {
        LOG.info("Start getGrade");
        NameItem nameItem = new NameItem(name, gradingItem);
        Grade grade = gradebook.getGrade(nameItem);
        if(grade == null){
            LOG.info("Fail to getGrade because not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).build();
        } 
        NameItemGrade nameItemGrade = new NameItemGrade(name, gradingItem, grade);
        String xmlString = nameItemGrade.NameItemGradeToXml();
        if(xmlString == null){
            LOG.info("Fail to getGrade because internal server error");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.info("Get student name, grading item and grade");
        LOG.info(xmlString);
        LOG.info("Complete getGrade");
        LOG.info("============================================================================================================");
        return Response.status(Response.Status.OK).entity(xmlString).build();
    }    
}
