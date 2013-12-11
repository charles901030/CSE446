/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.AppealRepresentation;
import com.mycompany.appealserver.model.DecisionRepresentation;
import com.mycompany.appealserver.model.IdRepresentation;
import java.io.ByteArrayInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.logging.Logger;

/**
 *
 * @author shaosh
 */
public class Action {
    private static final Logger LOG = Logger.getLogger(Action.class.getName());
    public static AppealRepresentation XmlToAppealRepresentation(String xmlString){
        LOG.info("Start convert Xml to AppealRepresentation");
        AppealRepresentation appealRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()));
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert AppealRepresentation to Xml");
            
        }
        LOG.info("Complete convert Xml to AppealRepresentation");
        return appealRepresentation;
    }
    
    public static DecisionRepresentation XmlToDecisionRepresentation(String xmlString){
        LOG.info("Start convert Xml to DecisionRepresentation");
        DecisionRepresentation decisionRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(DecisionRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            decisionRepresentation = (DecisionRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert AppealRepresentation to Xml");
        }
        LOG.info("Complete convert Xml to DecisionRepresentation");
        return decisionRepresentation;
    }
    
    public static IdRepresentation XmlToIdRepresentation(String xmlString){
        LOG.info("Start convert Xml to IdRepresentation");
        IdRepresentation idRepresentation = null;
        try{
            JAXBContext context = JAXBContext.newInstance(IdRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            idRepresentation = (IdRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()) );
        }
        catch(JAXBException e){
            e.printStackTrace();
            LOG.info("Fail to convert AppealRepresentation to Xml");
        }
        LOG.info("Complete convert Xml to DecisionRepresentation");
        return idRepresentation;
    }
}
