/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.oldclassesformigrationonly.PersistentEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Alexandra
 */
public final class OldRecipeIngredient implements Serializable, PersistentEntity<String> {

    private String ingredientKey;
    private float quantity;

    public OldRecipeIngredient() {
    }

    public OldRecipeIngredient(String ingredientKey, float quantity) {
        this.ingredientKey = ingredientKey;
        this.quantity = quantity;
    }

    @Override
    public String getKey() {
        return ingredientKey;
    }

    public String getIngredientKey() {
        return ingredientKey;
    }

    public void setIngredientKey(String ingredientKey) {
        this.ingredientKey = ingredientKey;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(OldRecipeIngredient.class.getSimpleName());
        sb.append("{ingredientKey=").append(ingredientKey);
        sb.append(", quantity=").append(quantity);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.ingredientKey);
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
        final OldRecipeIngredient other = (OldRecipeIngredient) obj;
        if (!Objects.equals(this.ingredientKey, other.ingredientKey)) {
            return false;
        }
        return true;
    }

}
