/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.AppealRepresentation;
import com.mycompany.appealserver.model.AppealStatus;
import com.mycompany.appealserver.model.DecisionRepresentation;
import com.mycompany.appealserver.model.DecisionStatus;
import com.mycompany.appealserver.model.Id;
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
@Path("decision")
public class DecisionResource {
    private static final Logger LOG = Logger.getLogger(AppealResource.class.getName());
    public static DecisionRepository decisionRepository = AppealResource.decisionRepository;
    public static GradebookRepository gradebook = AppealResource.gradebook;
    private AppealRepresentationRepository pendingRepository= AppealResource.pendingRepository;
    private AppealRepresentationRepository submittedRepository= AppealResource.submittedRepository;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DecisionResource
     */
    public DecisionResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.appealserver.DecisionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{appealId}")
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getDecision(@PathParam("appealId") Id id) {
        LOG.info("Start getDecision");
        DecisionRepresentation decisionRepresentation = decisionRepository.getDecisionRepresentation(id);
        if(decisionRepresentation == null){
            LOG.info("Fail to createDecision because of not found");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
        LOG.info("Get decisionRepresentation");
        LOG.info(decisionRepresentation.DecisionRepresentationToXml());
        decisionRepresentation.setDecisionStatus(DecisionStatus.Retrieved);
        decisionRepository.updateDecisionRepresentation(id, decisionRepresentation);
        String xmlString = decisionRepresentation.DecisionRepresentationToXml();
        LOG.info("Set decisionRepresentation status to retieved");
        LOG.info(xmlString);
        
        AppealRepresentation ar = pendingRepository.getAppealRepresentation(id);
        LOG.info("Get appealRepresentation in pendingRepository");
        LOG.info(ar.AppealRepresentationToXml());
        ar.setStatus(AppealStatus.Deleted);
        pendingRepository.updateAppealRepresentation(id, ar);
        LOG.info("Set status of appealRepresentation in pendingRepository to deleted");
        LOG.info(ar.AppealRepresentationToXml());
        
        ar = submittedRepository.getAppealRepresentation(id);
        LOG.info("Get appealRepresentation in submittedRepository");
        LOG.info(ar.AppealRepresentationToXml());
        ar.setStatus(AppealStatus.Deleted);
        submittedRepository.updateAppealRepresentation(id, ar);
        LOG.info("Set status of appealRepresentation in submittedRepository to deleted");
        LOG.info(ar.AppealRepresentationToXml());
        
        LOG.info("Complete getDecision");
        LOG.info("============================================================================================================");
        return Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
    }
}
