/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
/**
 *
 * @author shaosh
 */
public class Representation {
    
    @XmlElement(name = "link", namespace = Uri.DAP_NAMESPACE)
    protected ArrayList<Link> links;
    
    protected Link getLinkByName(String uriName){
        if(links == null)
            return null;
        Link temp = null;
        for(int i = 0; i < links.size(); i++){
            temp = links.get(i);
            if(temp.getRel().equalsIgnoreCase(uriName)){
                return temp;
            }
        }
        return temp;
    }
}