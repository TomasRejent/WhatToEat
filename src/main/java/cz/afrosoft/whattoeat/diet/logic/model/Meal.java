/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.diet.logic.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Alexandra
 */
public class Meal implements Serializable{

    private String recipeKey;
    private int servings;

    public Meal(){
        this.servings = 1;
    }

    public Meal(String recipeKey){
        this.recipeKey = recipeKey;
        this.servings = 1;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getRecipeKey() {
        return recipeKey;
    }

    public void setRecipeKey(String recipeKey) {
        this.recipeKey = recipeKey;
    }
}
