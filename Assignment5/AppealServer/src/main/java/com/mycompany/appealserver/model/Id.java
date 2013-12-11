/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import java.util.UUID;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author shaosh
 */
public class Id {
    @XmlValue
    private String id;
    
    public Id(String id){
        this.id = id;
    }
    
    public Id(){
        this(UUID.randomUUID().toString());
    }
    
    public String toString(){
        return id;
    }
}