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
public class Grade {
    @XmlValue
    private int grade;
    
    Grade(){}
    
    public Grade(int grade){
        this.grade = grade;
    }
    
    public int getGrade(){
        return grade;
    }
    
    public String toString(){
        return String.valueOf(grade);
    }
}
