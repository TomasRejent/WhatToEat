/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.gui.view;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import cz.afrosoft.whattoeat.ServiceHolder;
import static cz.afrosoft.whattoeat.data.util.ParameterCheckUtils.checkNotNull;
import cz.afrosoft.whattoeat.logic.model.Ingredient;
import cz.afrosoft.whattoeat.logic.model.IngredientInfo;
import cz.afrosoft.whattoeat.logic.model.enums.IngredientUnit;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Tomas Rejent
 */
final class IngredientView implements Comparable<IngredientView>{

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
        }else if(thisKeyWords.size() < thatKeyWords.size()){
            if(thatKeyWords.containsAll(thisKeyWords)){
                return LESS;
            }else{
                return GREATER;
            }
        }else if(thisKeyWords.size() > thatKeyWords.size()){
            if(thisKeyWords.containsAll(thatKeyWords)){
                return GREATER;
            }else{
                return LESS;
            }
        }else if(thisKeyWords.size() == 1 && thatKeyWords.size() == 1){
            final String thisKeyWord = thisKeyWords.iterator().next();
            final String thatKeyWord = thatKeyWords.iterator().next();
            return thisKeyWord.compareTo(thatKeyWord);
        } else{
            return EQUAL;
        }
    }



        @Override
        public String toString() {
            return "IngredientView{" + "ingredient=" + ingredient + ", ingredientInfo=" + ingredientInfo + '}';
        }
    }
