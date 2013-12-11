/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import java.io.StringWriter;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.logging.Logger;
/**
 *
 * @author shaosh
 */
public class Representation {
    private static final Logger LOG = Logger.getLogger(Representation.class.getName());
    @XmlElement(name = "link", namespace = Uri.DAP_NAMESPACE)
    protected ArrayList<Link> links;
    
    protected Link getLinkByName(String uriName){
        if(links == null)
            return null;
        Link temp = null;
        for(int i = 0; i < links.size(); i++){
            temp = links.get(i);
            if(temp.getRel().equalsIgnoreCase(uriName)){
                return temp;
            }
        }
        return temp;
    }
    
//    protected String RepresentationToXml(){
//        LOG.info("Start converting Representation to Xml");
//        String xmlString = null;
//        try{
//            JAXBContext jaxbContext = JAXBContext.newInstance(AppealRepresentation.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            StringWriter writer = new StringWriter();
//            marshaller.marshal(this, writer);
//            xmlString = writer.toString();
//            LOG.info("Complete converting Representation to Xml");
//            return xmlString;
//        }
//        catch(JAXBException e){
//            e.printStackTrace();
//            LOG.info("Fail to convert AppealRepresentation to Xml");
//            return xmlString;
//        }
//    }
}