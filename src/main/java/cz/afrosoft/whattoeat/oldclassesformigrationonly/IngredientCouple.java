/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.oldclassesformigrationonly;

import cz.afrosoft.whattoeat.core.gui.I18n;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

/**
 *
 * @author Tomas Rejent
 */
public class IngredientCouple implements Comparable<IngredientCouple>{

    private final OldRecipeIngredient recipeIngredient;
    private final OldIngredient ingredient;

    public IngredientCouple(OldRecipeIngredient recipeIngredient, OldIngredient ingredient) {
        checkNotNull(recipeIngredient, "RecipeIngredient cannot be null.");
        checkNotNull(ingredient, "RecipeIngredient info cannot be null.");

        this.recipeIngredient = recipeIngredient;
        this.ingredient = ingredient;
    }

    public OldRecipeIngredient getRecipeIngredient() {
        return recipeIngredient;
    }

    public OldIngredient getIngredient() {
        return ingredient;
    }

    @Override
    public int compareTo(IngredientCouple thatIngredientCouple) {
        if (thatIngredientCouple == null){
            return 1;
        }
        final OldIngredient thisIngredient = ingredient;
        final OldIngredient thatIngredient = thatIngredientCouple.getIngredient();
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
