/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.cookbook.recipe.logic.validator;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Recipe;
import cz.afrosoft.whattoeat.core.logic.validator.EntityValidator;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Tomas Rejent
 */
public class RecipeValidator implements EntityValidator<Recipe>{

    private static final String NULL_ENTITY_KEY = "cz.afrosoft.whattoeat.recipes.error.null";
    private static final String EMPTY_NAME_KEY = "cz.afrosoft.whattoeat.recipes.error.name.required";
    private static final String EMPTY_TYPE_KEY = "cz.afrosoft.whattoeat.recipes.error.type.required";
    private static final String EMPTY_TASTE_KEY = "cz.afrosoft.whattoeat.recipes.error.taste.required";
    private static final String EMPTY_TIME_KEY = "cz.afrosoft.whattoeat.recipes.error.time.required";
    private static final String EMPTY_PREPARATION_KEY = "cz.afrosoft.whattoeat.recipes.error.preparation";
    private static final String EMPTY_INGREDIENTS_KEY = "cz.afrosoft.whattoeat.recipes.error.ingeients";

    @Override
    public boolean isValid(final Recipe recipe) {
        return validateInternal(recipe).isEmpty();
    }

    @Override
    public Map<String, String> validate(final Recipe recipe) {
        return validateInternal(recipe);
    }

    private Map<String, String> validateInternal(final Recipe recipe){
        final Map<String, String> errors = new HashMap<>();

        if(recipe == null){
            errors.put("recipe", I18n.getText(NULL_ENTITY_KEY));
            return errors;
        }

        if(StringUtils.isBlank(recipe.getName())){
            errors.put("name", I18n.getText(EMPTY_NAME_KEY));
        }

        if(recipe.getRecipeTypes() == null || recipe.getRecipeTypes().isEmpty()){
            errors.put("recipeType", I18n.getText(EMPTY_TYPE_KEY));
        }

        if(recipe.getTaste() == null){
            errors.put("taste", I18n.getText(EMPTY_TASTE_KEY));
        }

        if(recipe.getPreparationTime() == null){
            errors.put("preparationTime", I18n.getText(EMPTY_TIME_KEY));
        }

        if(StringUtils.isBlank(recipe.getPreparation())){
            errors.put("preparation", I18n.getText(EMPTY_PREPARATION_KEY));
        }

        if(recipe.getIngredients() == null || recipe.getIngredients().isEmpty()){
            errors.put("ingredients", I18n.getText(EMPTY_INGREDIENTS_KEY));
        }

        return errors;
    }

}
