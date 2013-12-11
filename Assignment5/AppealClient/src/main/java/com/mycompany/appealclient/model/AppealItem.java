/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlValue;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlTransient;
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
    
    public AppealItem(Justification justification, Image image){
        LOG.info("Creating an AppealItem");
//        this.gradingItem = gradingItem;
        this.justification = justification;
        this.image = image;        
    }
    
//    public GradingItem getGradingItem(){
//        return gradingItem;
//    }
    @XmlTransient
    public Image getImage(){
        return image;
    }
    @XmlTransient
    public Justification getJustification(){
        return justification;
    }
    
    public void setImage(Image image){
        this.image = image;
    }
    
    public void setJustification(Justification justification){
        this.justification = justification;
    }
    
    public String toString(){
        return /*gradingItem + " " + */justification + " " + image;
    }
}
