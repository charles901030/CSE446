/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.Grade;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.mycompany.appealserver.model.GradingItem;
import com.mycompany.appealserver.model.Name;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shaosh
 */
@XmlRootElement
public class NameItemGrade {
    private static final Logger LOG = Logger.getLogger(NameItemGrade.class.getName());
    @XmlElement
    private Name name;
    @XmlElement
    private GradingItem gradingItem;
    @XmlElement
    private Grade grade;
    
    NameItemGrade(){}
    
    public NameItemGrade(Name name, GradingItem gradingItem, Grade grade){
        this.name = name;
        this.gradingItem = gradingItem;
        this.grade = grade;
    }
    
    @XmlTransient
    public Name getName(){
        return name;
    }
    
    @XmlTransient
    public GradingItem getGradingItem(){
        return gradingItem;
    }
    
    @XmlTransient
    public Grade getGrade(){
        return grade;
    }
    
    public String toString(){
        String str = "";
        str += "Name: " + name + "\n";
        str += "Grading Item: " + gradingItem + "\n";
        str += "Grade: " + grade + "\n";
        return str;
    }
    
    public String NameItemGradeToXml(){
        LOG.info("Start converting AppealRepresentation to Xml");
        String xmlString = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(NameItemGrade.class);
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
}

