/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.ingredient.logic.model;

import cz.afrosoft.whattoeat.oldclassesformigrationonly.PersistentEntity;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.UUIDEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @author Alexandra
 */
public final class OldIngredient extends UUIDEntity implements Serializable, PersistentEntity<String> {

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
        return Optional.ofNullable(keywords).orElse(Collections.emptySet());
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Ingredient{" + "name=" + name + ", ingredientUnit=" + ingredientUnit + ", price=" + price + ", keywords=" + keywords + '}';
    }
}
