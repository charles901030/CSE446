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
public class Image {
    @XmlValue
    private String name;
    
    Image(){}
    
    public Image(String name){
        this.name = name;
    }
    
    public String getImage(){
        return name;
    }
    
    public String toString(){
        return name;
    }
}
