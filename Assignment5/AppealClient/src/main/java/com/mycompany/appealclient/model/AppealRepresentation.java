/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author shaosh
 */
@XmlRootElement(name = "appeal", namespace = Uri.APPEAL_NAMESPACE)
public class AppealRepresentation extends Representation{
    private static final Logger LOG = Logger.getLogger(AppealRepresentation.class.getName());
    @XmlElement
    private Appeal appeal;
    @XmlElement
    private AppealStatus status;
    @XmlElement
    private Id id;
    @XmlElementWrapper
    @XmlElement(name = "link")
    private ArrayList<Link> links;
    
    
//    public AppealRepresentation (Appeal appeal, ArrayList<Link> links, AppealStatus status){
//        this.appeal = new Appeal(appeal.getName(), appeal.getSubject(), appeal.getAppealItems());
//        this.links = links;
//        this.status = status;
//    }
    AppealRepresentation(){}
    
    public AppealRepresentation (Appeal appeal, AppealStatus status){
        LOG.info("Creating an AppealRepresentation");
        this.appeal = new Appeal(appeal.getName(), appeal.getSubject(), appeal.getGradingItem(), appeal.getAppealItems());
        this.status = status;
        this.links = null;
    }
    
    public AppealRepresentation (Appeal appeal, AppealStatus status, ArrayList<Link> links){
        LOG.info("Creating an AppealRepresentation");
        this.appeal = new Appeal(appeal.getName(), appeal.getSubject(), appeal.getGradingItem(), appeal.getAppealItems());
        this.status = status;
        this.links = links;
    }
    
    public AppealRepresentation (Appeal appeal, AppealStatus status, ArrayList<Link> links, Id id){
        LOG.info("Creating an AppealRepresentation");
        this.appeal = new Appeal(appeal.getName(), appeal.getSubject(), appeal.getGradingItem(), appeal.getAppealItems());
        this.status = status;
        this.links = links;
        this.id = id;
    }
    
    public String AppealRepresentationToXml(){
        LOG.info("Start converting AppealRepresentation to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(AppealRepresentation.class);
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
    @XmlTransient
    public Appeal getAppeal(){
        return appeal;
    }
    
    public void setAppeal(Appeal appeal){
        this.appeal = appeal;
    }
    @XmlTransient
    public AppealStatus getStatus(){
        return status;
    }
    @XmlTransient
    public ArrayList<Link> getLinks(){
        if(links == null)
            return null;
        return links;
    }
    @XmlTransient
    public Id getId(){
        return id;
    }
    
    public String toString(){
        String appeal = this.appeal.toString();
        String status = "status: " + this.status.toString() + "\n";
        String id = this.id.toString();
        String link = "";
        for(int i = 0; links != null && i < links.size(); i++){
            link += links.get(i).toString();
        }
        return appeal + status + link + "id: " + id;
    }
    
    public String getSelf() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("self")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
    
    public String getUpdate() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("update")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
    
    public String getDelete() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("delete")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
    
    public String getSubmit() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("submit")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
    
    public String getCreate() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("create")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
    
    public String getDecision() throws URISyntaxException{
        String rel = null;
        for(int i = 0; links != null && i < links.size(); i++){
            rel = links.get(i).getRel();
            if (rel.substring(rel.lastIndexOf('/') + 1, rel.length()).equals("decision")){
                return links.get(i).getUri().toString();
            }
        }
        return null;
    }
}
