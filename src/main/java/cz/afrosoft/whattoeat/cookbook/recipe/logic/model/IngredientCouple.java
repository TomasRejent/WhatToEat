/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Ingredient;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientInfo;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class IngredientCouple {

    private final Ingredient ingredient;
    private final IngredientInfo ingredientInfo;

    public IngredientCouple(Ingredient ingredient, IngredientInfo ingredientInfo) {
        checkNotNull(ingredient, "Ingredient cannot be null.");
        checkNotNull(ingredientInfo, "Ingredient info cannot be null.");

        this.ingredient = ingredient;
        this.ingredientInfo = ingredientInfo;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public IngredientInfo getIngredientInfo() {
        return ingredientInfo;
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
        if (!Objects.equals(this.ingredient, other.ingredient)) {
            return false;
        }
        return true;
    }



}
