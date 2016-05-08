/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.gui.view;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;

/**
 *
 * @author Tomas Rejent
 */
final class IngredientView{

        private final Ingredient ingredient;
        private final IngredientInfo ingredientInfo;

        private int servings = 1;

        public IngredientView(Ingredient ingredient, IngredientInfo ingredientInfo) {
            checkNotNull(ingredient, "Ingredient cannot be null.");
            checkNotNull(ingredientInfo, "Ingredient info cannot be null.");

            this.ingredient = ingredient;
            this.ingredientInfo = ingredientInfo;
        }

        public String getName(){
            return ingredient.getName();
        }

        public float getQuantity(){
            return ingredient.getQuantity() * servings;
        }

        public IngredientUnit getIngredientUnit(){
            return ingredientInfo.getIngredientUnit();
        }

        public float getPrice(){
            return ingredient.getQuantity() * ingredientInfo.getPrice() * servings;
        }

        public ImmutableSet<String> getKeywords(){
            return ImmutableSortedSet.copyOf(ingredientInfo.getKeywords());
        }

        public int getServings() {
            return servings;
        }

        public void setServings(int servings) {
            this.servings = servings;
        }



        @Override
        public String toString() {
            return "IngredientView{" + "ingredient=" + ingredient + ", ingredientInfo=" + ingredientInfo + '}';
        }
    }
