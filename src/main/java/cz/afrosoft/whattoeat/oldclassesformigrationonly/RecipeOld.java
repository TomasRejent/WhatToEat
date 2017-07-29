/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.OldRecipeIngredient;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.PreparationTime;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.core.gui.I18n;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Alexandra
 */
public final class RecipeOld extends UUIDEntity implements Serializable, PersistentEntity<String>, Comparable<RecipeOld> {

    private String name;
    private String preparation;
    private Set<OldRecipeIngredient> ingredients;

    private PreparationTime preparationTime;
    private Set<OldRecipeType> recipeTypes;
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

    public Set<OldRecipeIngredient> getIngredients() {
        return Optional.ofNullable(ingredients).orElse(Collections.emptySet());
    }

    public void setIngredients(Set<OldRecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public PreparationTime getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(PreparationTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Set<OldRecipeType> getRecipeTypes() {
        return Optional.ofNullable(recipeTypes).orElse(EnumSet.noneOf(OldRecipeType.class));
    }

    public void setRecipeTypes(Set<OldRecipeType> recipeTypes) {
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
    public int compareTo(final RecipeOld thatRecipe) {
        if (thatRecipe == null) {
            return 1;
        }

        return I18n.compareStringsIgnoreCase(name, thatRecipe.getName());
    }

    @Override
    public String toString() {
        return "Recipe{" + "name=" + name + ", preparation=" + preparation + ", ingredients=" + ingredients + ", preparationTime=" + preparationTime + ", recipeType=" + recipeTypes + ", taste=" + taste + ", rating=" + rating + ", keywords=" + keywords + ", sideDishes=" + sideDishes + '}';
    }
}
