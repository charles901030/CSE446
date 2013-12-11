/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import java.io.StringWriter;
import java.util.UUID; 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
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