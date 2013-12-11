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
public class Grade {
    @XmlValue
    private int grade;
    
    Grade(){}
    
    public Grade(int grade){
        this.grade = grade;
    }
    
    @XmlTransient
    public int getGrade(){
        return grade;
    }
    
    public String toString(){
        return String.valueOf(grade);
    }
}
