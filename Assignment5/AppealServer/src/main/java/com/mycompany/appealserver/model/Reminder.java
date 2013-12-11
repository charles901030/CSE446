/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
/**
 *
 * @author shaosh
 */
@XmlRootElement(name = "reminderItem")
public class Reminder {
    private static final Logger LOG = Logger.getLogger(AppealItem.class.getName());
    @XmlElement
    private Appeal appeal;
    @XmlElement
    private Id id;
    @XmlElement(name = "message")
    private String reminder;
    @XmlElement
    private Link link;
    @XmlTransient
    private URI uri;
    
    Reminder(){}
    
    public Reminder(Appeal appeal, Id id){
        this.appeal = appeal;
        this.id = id;
        reminder = "The Appeal (ID = " + id +") needs to be processed.";//:\n" + appeal.toString(); 
        try {
            uri = new URI(Uri.SUBMITTEDURI + "/" + id);            
            link = new Link("submit", uri, Uri.APPEAL_MEDIATYPE);
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String send(){
        return reminder;
    }
    
    public String ReminderToXml(){
        LOG.info("Start converting Reminder to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(Reminder.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);
            xmlString = writer.toString();
            LOG.info("Complete converting Reminder to Xml");
            return xmlString;
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert Reminder to Xml");
            return xmlString;
        }
    }
    
    @XmlTransient
    public Id getId()
    {
        return id;
    }
    
    @XmlTransient
    public Appeal getAppeal()
    {
        return appeal;
    }
    
    @XmlTransient
    public String getReminder()
    {
        return reminder;
    }
    
    @XmlTransient
    public Link getLink()
    {
        return link;
    }
}
