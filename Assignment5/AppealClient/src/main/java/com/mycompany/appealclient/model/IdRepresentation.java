/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import java.io.StringWriter;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shaosh
 */
@XmlRootElement(name = "id")
public class IdRepresentation {
    private static final Logger LOG = Logger.getLogger(Id.class.getName());
    @XmlElement
    private Id id;
    
    IdRepresentation(){}
    
    public IdRepresentation(Id id){
        this.id = id;
    }
    
    @XmlTransient
    public Id getId(){
        return id;
    }
    
    public String IdRepresentationToXml(){
        LOG.info("Start converting IdRepresentation to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(IdRepresentation.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);
            xmlString = writer.toString();
            LOG.info("Complete converting IdRepresentation to Xml");
            return xmlString;
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert IdRepresentation to Xml");
            return xmlString;
        }
    }
}
