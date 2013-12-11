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
public class Name {
    @XmlValue
    private String name;
    
    Name(){}
    
    public Name(String name){
        this.name = name;
    }
    @XmlTransient
    public String getName(){
        return name;
    }
    
    public String toString(){
        return name;
    }
}
