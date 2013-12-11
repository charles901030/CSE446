/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.GradingItem;
import com.mycompany.appealserver.model.Name;

/**
 *
 * @author shaosh
 */
public class NameItem {
    private Name name;
    private GradingItem gradingItem;
    
    public NameItem(Name name, GradingItem gradingItem){
        this.name = name;
        this.gradingItem = gradingItem;
    }
    
    public Name getName(){
        return name;
    }
    
    public GradingItem getGradingItem(){
        return gradingItem;
    }
    
    public String toString(){
        return name.toString() + gradingItem.toString();
    }
}
