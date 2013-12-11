/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shaosh
 */
@XmlRootElement(name = "reminderlist", namespace = Uri.APPEAL_NAMESPACE)
public class ReminderRepresentation {
    private static final Logger LOG = Logger.getLogger(DecisionRepresentation.class.getName());
    @XmlElementWrapper
    @XmlElement(name = "reminder")
    private ArrayList<Reminder> reminders;
    
    ReminderRepresentation(){}
    
    public ReminderRepresentation(ArrayList<Reminder> reminders){
        this.reminders = reminders;
    }
    
    public String ReminderRepresentationToXml(){
        LOG.info("Start converting AppealRepresentation to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(ReminderRepresentation.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);
            xmlString = writer.toString();
            LOG.info("Complete converting AppealRepresentation to Xml");
            return xmlString;
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert AppealRepresentation to Xml");
            return xmlString;
        }
    }
    
    public void addReminder(Reminder reminder){
        reminders.add(reminder);
    }
    
    @XmlTransient
    public ArrayList<Reminder> getReminders(){
        return reminders;
    }
}
