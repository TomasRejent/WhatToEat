/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.ingredient.gui.view;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldIngredient;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldRecipeIngredient;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.ServiceHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Set;

import static cz.afrosoft.whattoeat.oldclassesformigrationonly.ParameterCheckUtils.checkNotNull;

/**
 *
 * @author Tomas Rejent
 */
final class IngredientView implements Comparable<IngredientView>{

    private final OldRecipeIngredient ingredient;
    private final OldIngredient ingredientInfo;

        private int servings = 1;

    public IngredientView(OldRecipeIngredient ingredient, OldIngredient ingredientInfo) {
            checkNotNull(ingredient, "RecipeIngredient cannot be null.");
            checkNotNull(ingredientInfo, "RecipeIngredient info cannot be null.");

            this.ingredient = ingredient;
            this.ingredientInfo = ingredientInfo;
        }

        public String getName(){
            return ingredientInfo.getName();
        }

        public float getQuantity(){
            return ServiceHolder.getIngredientQuantityService().getQuantity(ingredient, ingredientInfo, servings);
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
    public int compareTo(IngredientView ingredientView) {
        Validate.notNull(ingredientView, "Cannot compare to null.");

        final int LESS = -1;
        final int EQUAL = 0;
        final int GREATER = 1;

        final String thisName = ingredientInfo.getName();
        final Set<String> thisKeyWords = ingredientInfo.getKeywords();
        final String thatName = ingredientView.getName();
        final Set<String> thatKeyWords = ingredientView.getKeywords();

        if(thisKeyWords.equals(thatKeyWords)){
            return thisName.compareTo(thatName);
        }else if(thisKeyWords.size() == 1 && thatKeyWords.size() == 1){
            final String thisKeyWord = thisKeyWords.iterator().next();
            final String thatKeyWord = thatKeyWords.iterator().next();
            return thisKeyWord.compareTo(thatKeyWord);
        } else if(thisKeyWords.size() < thatKeyWords.size()){
            return LESS;
        }else if (thisKeyWords.size() > thatKeyWords.size()){
            return GREATER;
        }else{
            return StringUtils.join(thisKeyWords).compareTo(StringUtils.join(thatKeyWords));
        }
    }



        @Override
        public String toString() {
            return "IngredientView{" + "ingredient=" + ingredient + ", ingredientInfo=" + ingredientInfo + '}';
        }
    }
