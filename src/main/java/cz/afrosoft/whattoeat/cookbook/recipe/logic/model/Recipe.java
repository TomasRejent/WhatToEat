/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.data.PersistentEntity;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.logic.model.UUIDEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Alexandra
 */
public final class Recipe extends UUIDEntity implements Serializable, PersistentEntity<String>, Comparable<Recipe>{

    private String name;
    private String preparation;
    private Set<RecipeIngredient> ingredients;
    
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

    public Set<RecipeIngredient> getIngredients() {
        return Optional.ofNullable(ingredients).orElse(Collections.emptySet());
    }

    public void setIngredients(Set<RecipeIngredient> ingredients) {
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

    /**
     *
     * @return (NotNull) Set of keywords of recipe or empty set.
     */
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
    public int compareTo(final Recipe thatRecipe) {
        if(thatRecipe == null){
            return 1;
        }

        return I18n.compareStringsIgnoreCase(name, thatRecipe.getName());
    }

    @Override
    public String toString() {
        return "Recipe{" + "name=" + name + ", preparation=" + preparation + ", ingredients=" + ingredients + ", preparationTime=" + preparationTime + ", recipeType=" + recipeTypes + ", taste=" + taste + ", rating=" + rating + ", keywords=" + keywords + ", sideDishes=" + sideDishes + '}';
    }
}
