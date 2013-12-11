/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.Id;
import com.mycompany.appealserver.model.ReminderRepresentation;
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
@Path("reminder")
public class ReminderResource {
    private static final Logger LOG = Logger.getLogger(ReminderResource.class.getName());
    private ReminderRepository reminderRepository= AppealResource.reminderRepository;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReminderResource
     */
    public ReminderResource() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.appealserver.ReminderResource
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes(Uri.APPEAL_MEDIATYPE)
    @Produces(Uri.APPEAL_MEDIATYPE)
    public Response getReminders() {
        LOG.info("Start getReminders");
        if(reminderRepository.getSize() != 0){
            ReminderRepresentation reminderRepresentation = new ReminderRepresentation(reminderRepository.getReminders());
            String xmlString = reminderRepresentation.ReminderRepresentationToXml();
            LOG.info("Get Reminders");
            LOG.info(xmlString);
            LOG.info("Complete getReminders");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.OK).entity(xmlString).type(Uri.APPEAL_MEDIATYPE).build();
        }
        else{
            LOG.info("No reminders");
            LOG.info("============================================================================================================");
            return Response.status(Response.Status.NOT_FOUND).type(Uri.APPEAL_MEDIATYPE).build();
        }
    }
}
