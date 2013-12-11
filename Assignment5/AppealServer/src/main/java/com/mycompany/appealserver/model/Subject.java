/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

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
    
    public String getSubject(){
        return subject;
    }
    
    public String toString(){
        return subject;
    }
}
