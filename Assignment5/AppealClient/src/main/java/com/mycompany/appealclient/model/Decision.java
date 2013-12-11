/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shaosh
 */
@XmlRootElement
public class Decision {
    @XmlElement
    Name name;
    @XmlElement
    GradingItem gradingItem;    
    @XmlElement
    Approval approval;
    @XmlElement(name = "comment")
    String comment;
    @XmlElement
    Grade grade;
    
    Decision(){}
    
    public Decision(Name name, GradingItem gradingItem, Approval approval, String comment, Grade grade){
        this.name = name;
        this.gradingItem = gradingItem;
        this.approval = approval;
        this.comment = comment;
        if(grade == null)
            this.grade = null;
        else
            this.grade = grade;
    }
    
    
    @XmlTransient
    public Name getName(){
        return name;
    }
    
    @XmlTransient
    public GradingItem getgradingItem(){
        return gradingItem;
    }
    
    @XmlTransient
    public Approval getApproval(){
        return approval;
    }
    
    @XmlTransient
    public String getComment(){
        return comment;
    }
    
    @XmlTransient
    public Grade getGrade(){
        return grade;
    }
    
//    public Id getId(){
//        return id;
//    }
    
    public String toString(){
        String str = "";
        str = str.concat("Name: " + name + "\n");
        str = str.concat("Grading Item: " + gradingItem + "\n");
        str = str.concat("Approval: " + approval + "\n");
        str = str.concat("Comment: " + comment + "\n");
        if(this.grade != null)
            str = str.concat("New Grade: " + grade + "\n");
//        str = str.concat("Id: " + id + "\n");
        return str;
    }
}
