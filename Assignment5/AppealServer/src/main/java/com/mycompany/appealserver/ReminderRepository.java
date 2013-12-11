/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver;

import com.mycompany.appealserver.model.Reminder;
import com.mycompany.appealserver.model.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author shaosh
 */
public class ReminderRepository {
    private HashMap<String, Reminder> repository;
    
    public ReminderRepository(){
        repository = new HashMap<String, Reminder>();
    }
    
    public void addReminder(Id id, Reminder reminder){
        repository.put(id.toString(), reminder);
    }
    
    public Reminder getReminder(Id id){
        if(repository.containsKey(id.toString()))
            return repository.get(id.toString());
        else{
            System.out.println("Invalid id!");
            return null;
        }
    }
    
    public void removeReminder(Id id){
        if(repository.containsKey(id.toString()))
            repository.remove(id.toString());
        else{
            System.out.println("Invalid id!");
        }
    }
    
    public int getSize(){
        return repository.size();
    }
    
    public ArrayList<Reminder> getReminders(){ 
        Iterator iterator = repository.keySet().iterator();
        
        if(iterator.hasNext() == false){
            return null;
        }
        else{
            ArrayList<Reminder> reminders = new ArrayList();
            while(iterator.hasNext()){
                Reminder reminder = repository.get(iterator.next());
                reminders.add(reminder);
            }
            repository.clear();
            return reminders;
        }
    } 
}
