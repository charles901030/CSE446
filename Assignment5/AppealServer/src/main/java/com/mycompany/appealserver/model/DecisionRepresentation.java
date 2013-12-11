/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

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
@XmlRootElement(name = "decision", namespace = Uri.DECISIONURI)
public class DecisionRepresentation extends Representation{
    private static final Logger LOG = Logger.getLogger(DecisionRepresentation.class.getName());
    @XmlElement
    private Decision decision;
    @XmlElement
    private DecisionStatus status;
    @XmlElementWrapper
    @XmlElement(name = "link")
    private ArrayList<Link> links;
    @XmlElement
    private Id id;
    
    DecisionRepresentation(){}
    
    public DecisionRepresentation (Decision decision, DecisionStatus status, Id id){
        LOG.info("Creating an DecisionRepresentation");
        this.decision = new Decision(decision.getName(), decision.getgradingItem(), decision.getApproval(), decision.getComment(), decision.getGrade());
        this.status = status;
        this.id = id;
        this.links = null;
    }
    
    public DecisionRepresentation (Decision decision, DecisionStatus status, Id id, ArrayList<Link> links){
        LOG.info("Creating an AppealRepresentation");
        this.decision = new Decision(decision.getName(), decision.getgradingItem(), decision.getApproval(), decision.getComment(), decision.getGrade());
        this.status = status;
        this.id = id;
        this.links = links;
    }
    
    public String DecisionRepresentationToXml(){
        LOG.info("Start converting AppealRepresentation to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(DecisionRepresentation.class);
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
    public Decision getDecision(){
        return decision;
    }
    
    @XmlTransient
    public DecisionStatus getDecisionStatus(){
        return status;
    }
    
    public void setDecisionStatus(DecisionStatus status){
        this.status = status;
    }
    
    @XmlTransient
    public Id getId(){
        return id;
    }
    
    @XmlTransient
    public ArrayList<Link> getLinks(){
        if(links == null)
            return null;
        return links;
    }
    
    public String toString(){
        String decision = this.decision.toString();
        String status = this.status.toString();
        String id = this.id.toString();
        String link = "";
        for(int i = 0; i < links.size(); i++){
            link += links.get(i).toString();
        }
        return decision + status + link + id;
    }
    
}
