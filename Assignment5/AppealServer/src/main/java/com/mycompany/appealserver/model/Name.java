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
public class Name {
    @XmlValue
    private String name;
    
    Name(){}
    
    public Name(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        return name;
    }
}
