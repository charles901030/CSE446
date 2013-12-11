/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author shaosh
 */
public class Subject {
    @XmlValue
    private String subject;
    
    Subject(){}
    
    public Subject(String item){
        this.subject = "APPEAL - " + item;
    }
    @XmlTransient
    public String getSubject(){
        return subject;
    }
    
    public String toString(){
        return subject;
    }
}
