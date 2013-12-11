/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author shaosh
 */
public class Justification {
    @XmlValue
    private String justification;
    
    Justification(){}
    
    public Justification(String justification){
        this.justification = justification;
    }
    @XmlTransient
    public String getJustification(){
        return justification;
    } 
    
    public void setJustification(String justification){
        this.justification = justification;
    }
    
    public String toString(){
        return justification;
    }
}