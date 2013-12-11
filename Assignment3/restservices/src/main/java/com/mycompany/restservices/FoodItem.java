/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restservices;

/**
 *
 * @author shaosh
 */
public class FoodItem {
    private String country;
    private String id;
    private String name;
    private String description;
    private String category;
    private String price;
    public FoodItem(String country, String id, String name, String description, String category, String price){
        this.country = new String(country);
        this.id = new String(id);
        this.name = new String(name);
        this.description = new String(description);
        this.category = new String(category);
        this.price = new String(price);
    }
    
    public FoodItem getFoodItem(){
        return this;
    }
    
    public String getCountry(){
        return country;
    }
    
    public void setCountry(String country){
        this.country = new String(country);
    }
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = new String(id);
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = new String(name);
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = new String(description);
    }
    
    public String getCategory(){
        return category;
    }
    
    public void setCategory(String category){
        this.category = new String(category);
    }
    
    public String getPrice(){
        return price;
    }
    
    public void setPrice(String price){
        this.price = new String(price);
    }
}
