/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.data.PersistentEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Alexandra
 */
public final class Ingredient implements Serializable, PersistentEntity<String> {
    
    private String name;
    private float quantity;

    public Ingredient() {
    }

    public Ingredient(String name, float quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String getKey() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Ingredient.class.getSimpleName());
        sb.append("{name=").append(name);
        sb.append(", quantity=").append(quantity);
        sb.append("}");
        return sb.toString();
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
        final Ingredient other = (Ingredient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
       
}
