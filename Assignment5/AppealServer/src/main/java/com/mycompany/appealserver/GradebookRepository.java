/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.Grade;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author shaosh
 */
public class GradebookRepository {
    private HashMap<String, Grade> repository;
    
    public GradebookRepository(){
        repository = new HashMap<String, Grade>();
    }
    
    public void addGrade(NameItem nameItem, Grade grade){
        repository.put(nameItem.toString(), grade);
    }
    
    public Grade getGrade(NameItem nameItem){ 
        if(repository.containsKey(nameItem.toString()))
            return repository.get(nameItem.toString());
        else{
            System.out.println("Invalid name or grading item!");
            return null;
        }
    } 
    
    public boolean updateGrade(NameItem nameItem, Grade grade){
        if(repository.containsKey(nameItem.toString())){
            repository.put(nameItem.toString(), grade);
            return true;
        }
        else{
            return false;
        }
    }
    
    public void removeGradeItem(NameItem nameItem){
        if(repository.containsKey(nameItem.toString()))
            repository.remove(nameItem.toString());
        else{
            System.out.println("Invalid name or grading item!");
        }
    }
}
