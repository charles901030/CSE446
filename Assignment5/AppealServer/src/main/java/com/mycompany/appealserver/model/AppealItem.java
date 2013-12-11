/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlValue;
import java.util.logging.Logger;
/**
 *
 * @author shaosh
 */
@XmlRootElement(name = "appealItem")
public class AppealItem {
    private static final Logger LOG = Logger.getLogger(AppealItem.class.getName());
//    @XmlElement
//    private GradingItem gradingItem;
    @XmlElement
    private Justification justification;
    @XmlElement
    private Image image;
    
    AppealItem(){}
    
    public AppealItem(/*GradingItem gradingItem, */Justification justification, Image image){
        LOG.info("Creating an AppealItem");
//        this.gradingItem = gradingItem;
        this.justification = justification;
        this.image = image;        
    }
    
//    public GradingItem getGradingItem(){
//        return gradingItem;
//    }
    
    public Image getImage(){
        return image;
    }
    
    public Justification getJustification(){
        return justification;
    }
    
    public String toString(){
        return /*gradingItem + " " +*/ justification + " " + image;
    }
}
