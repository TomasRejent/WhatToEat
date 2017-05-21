/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import static cz.afrosoft.whattoeat.core.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.Ingredient;

import java.util.Objects;

import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class IngredientCouple implements Comparable<IngredientCouple>{

    private final RecipeIngredient recipeIngredient;
    private final Ingredient ingredient;

    public IngredientCouple(RecipeIngredient recipeIngredient, Ingredient ingredient) {
        checkNotNull(recipeIngredient, "RecipeIngredient cannot be null.");
        checkNotNull(ingredient, "RecipeIngredient info cannot be null.");

        this.recipeIngredient = recipeIngredient;
        this.ingredient = ingredient;
    }

    public RecipeIngredient getRecipeIngredient() {
        return recipeIngredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public int compareTo(IngredientCouple thatIngredientCouple) {
        if (thatIngredientCouple == null){
            return 1;
        }
        final Ingredient thisIngredient = ingredient;
        final Ingredient thatIngredient = thatIngredientCouple.getIngredient();
        if(thisIngredient == thatIngredient){
            return 0;
        }else if(thisIngredient == null){
            return -1;
        }else if (thatIngredient == null){
            return 1;
        }else{
            return I18n.compareStringsIgnoreCase(thisIngredient.getName(), thatIngredient.getName());
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final IngredientCouple other = (IngredientCouple) obj;
        if (!Objects.equals(this.recipeIngredient, other.recipeIngredient)) {
            return false;
        }
        return true;
    }



}
