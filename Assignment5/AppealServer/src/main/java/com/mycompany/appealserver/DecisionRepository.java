/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.DecisionRepresentation;
import com.mycompany.appealserver.model.Id;
import java.util.HashMap;

/**
 *
 * @author shaosh
 */
public class DecisionRepository {
    private HashMap<String, DecisionRepresentation> repository;
    
    public DecisionRepository(){
        repository = new HashMap<String, DecisionRepresentation>();
    }
    
    public void addDecisionRepresentation(Id id, DecisionRepresentation decisionRepresentation){
        repository.put(id.toString(), decisionRepresentation);
    }
    
    public void updateDecisionRepresentation(Id id, DecisionRepresentation decisionRepresentation){
        repository.put(id.toString(), decisionRepresentation);
    }
    
    public DecisionRepresentation getDecisionRepresentation(Id id){
        if(repository.containsKey(id.toString()))
            return repository.get(id.toString());
        else{
            System.out.println("Invalid id!");
            return null;
        }
    }
    
    public void removeDecisionRepresentation(Id id){
        if(repository.containsKey(id.toString()))
            repository.remove(id.toString());
        else{
            System.out.println("Invalid id!");
        }
    }
}
