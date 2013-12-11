/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.AppealRepresentation;
import com.mycompany.appealserver.model.AppealStatus;
import com.mycompany.appealserver.model.Id;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
/**
 *
 * @author shaosh
 */
public class AppealRepresentationRepository {
    private HashMap<String, AppealRepresentation> repository;
    
    public AppealRepresentationRepository(){
        repository = new HashMap<String, AppealRepresentation>();
    }
    
    public Id addAppealRepresentation(AppealRepresentation appealRepresentation){
        Id id = new Id();
        repository.put(id.toString(), appealRepresentation);
        return id;
    }
    
    public void addAppealRepresentation(Id id, AppealRepresentation appealRepresentation){
        repository.put(id.toString(), appealRepresentation);
    }
    
    public AppealRepresentation getAppealRepresentation(Id id){
        if(repository.containsKey(id.toString()))
            return repository.get(id.toString());
        else{
            System.out.println("Invalid id!");
            return null;
        }
    } 
    
    public AppealRepresentation getAppealRepresentation(){ 
        Iterator iterator = repository.keySet().iterator();
        if(iterator.hasNext() == false){
            return null;
        }
        else{
//            System.out.println("there is something");
            while(iterator.hasNext()){
                Object obj = iterator.next();
                AppealRepresentation appealRepresentation = repository.get(obj);
//                System.out.println(appealRepresentation.getStatus().toString());
                if(appealRepresentation.getStatus().toString().equals(AppealStatus.Submitted.toString()) ||
                   appealRepresentation.getStatus().toString().equals(AppealStatus.Reminded.toString())){
//                    System.out.println(appealRepresentation.getStatus().toString());
                    return appealRepresentation;
                }
            }
            return null;
        }
    } 
    
    public boolean updateAppealRepresentation(Id id, AppealRepresentation appealRepresentation){
//        Iterator iterator = repository.keySet().iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//        System.out.println(id);
//        System.out.println(repository.containsKey(id.toString()));
        if(repository.containsKey(id.toString())){
            repository.put(id.toString(), appealRepresentation);
            return true;
        }
        else{
            return false;
        }
    }
    
    public void removeAppealRepresentation(Id id){
        if(repository.containsKey(id.toString()))
            repository.remove(id.toString());
        else{
            System.out.println("Invalid id!");
        }
    }
    
    public boolean searchAppealRepresentation(Id id){
        if(repository.containsKey(id.toString()))
            return true;
        else
            return false;
    }
}
