/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Alexandra
 */
public class Meal implements Serializable{
     
    private String recipeName; 
    private int servings;

    public Meal(){
        this.servings = 1;
    }
    
    public Meal(String recipeName){
        this.recipeName = recipeName;
        this.servings = 1;
    }
    
    public String getRecipeName() {
        return recipeName;
    }

    public int getServings() {
        return servings;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return recipeName;        
    }   
}
