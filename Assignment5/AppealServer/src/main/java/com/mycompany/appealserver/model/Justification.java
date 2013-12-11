/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import javax.xml.bind.annotation.XmlElement;
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
    
    public String getJustification(){
        return justification;
    } 
    
    public String toString(){
        return justification;
    }
}