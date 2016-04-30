/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model;

import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public final class IngredientInfo implements Serializable{
    
    private String name;
    private IngredientUnit ingredientUnit;
    private float price;
    private Set<String> keywords;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IngredientInfo other = (IngredientInfo) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientInfo{" + "name=" + name + ", ingredientUnit=" + ingredientUnit + ", price=" + price + ", keywords=" + keywords + '}';
    } 
}
