/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.logic.model;

import cz.afrosoft.whattoeat.logic.model.enums.PreparationTime;
import cz.afrosoft.whattoeat.logic.model.enums.RecipeType;
import cz.afrosoft.whattoeat.logic.model.enums.Taste;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public final class Recipe implements Serializable{
    
    private String name;
    private String preparation;
    private Set<Ingredient> ingredients;
    
    private PreparationTime preparationTime;
    private Set<RecipeType> recipeTypes;
    private Taste taste;
    private int rating;
    
    private Set<String> keywords;
    private Set<String> sideDishes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Set<Ingredient> getIngredients() {
        return Optional.ofNullable(ingredients).orElse(Collections.emptySet());
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public PreparationTime getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(PreparationTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Set<RecipeType> getRecipeTypes() {
        return Optional.ofNullable(recipeTypes).orElse(EnumSet.noneOf(RecipeType.class));
    }

    public void setRecipeTypes(Set<RecipeType> recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

    public Taste getTaste() {
        return taste;
    }

    public void setTaste(Taste taste) {
        this.taste = taste;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<String> getKeywords() {
        return Optional.ofNullable(keywords).orElse(Collections.emptySet());
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getSideDishes() {
        return Optional.ofNullable(sideDishes).orElse(Collections.emptySet());
    }

    public void setSideDishes(Set<String> sideDishes) {
        this.sideDishes = sideDishes;
    }

    @Override
    public String toString() {
        return "Recipe{" + "name=" + name + ", preparation=" + preparation + ", ingredients=" + ingredients + ", preparationTime=" + preparationTime + ", recipeType=" + recipeTypes + ", taste=" + taste + ", rating=" + rating + ", keywords=" + keywords + ", sideDishes=" + sideDishes + '}';
    }
}
