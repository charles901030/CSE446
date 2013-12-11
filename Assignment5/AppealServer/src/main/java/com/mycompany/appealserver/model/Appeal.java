/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.logging.Logger;
/**
 *
 * @author shaosh
 */
public class Appeal {
    private static final Logger LOG = Logger.getLogger(Appeal.class.getName());
    @XmlElement
    private Name name;
    @XmlElement
    private Subject subject;
    @XmlElement
    private GradingItem gradingItem;
    @XmlElementWrapper
    @XmlElement(name = "appealItem")
    private ArrayList<AppealItem> appealItems;
//    @XmlElement
//    private AppealStatus status;
    Appeal(){}
    
    public Appeal(Name name, Subject subject, GradingItem gradingItem, ArrayList appealItems){
        LOG.info("Creating an Appeal");
        this.name = name;
        this.subject = subject;
        this.gradingItem = gradingItem;
        this.appealItems = appealItems;
    }
    
    public Name getName(){
        return name;
    }
    
    public Subject getSubject(){
        return subject;
    }
    
    public GradingItem getGradingItem(){
        return gradingItem;
    }
    
    public ArrayList<AppealItem> getAppealItems(){
        return appealItems;
    }
    
    public String toString(){
        String str = "";
        str = str.concat("Name: " + name + "\n");
        str = str.concat("Subject: " + subject + "\n");
        str = str.concat("Grading Item: " + gradingItem + "\n");
        for(AppealItem i : appealItems){
            str = str.concat("Appeal Item: " + i.toString() + "\n");
        }
        return str;
    }
}
