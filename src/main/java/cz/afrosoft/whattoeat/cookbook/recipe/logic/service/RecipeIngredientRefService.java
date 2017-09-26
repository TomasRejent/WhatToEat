package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeIngredientEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeIngredientRef;
import cz.afrosoft.whattoeat.core.logic.service.RefService;

/**
 * Provides methods for conversions between {@link RecipeIngredientRef} and
 * {@link RecipeIngredientEntity}.
 */
public interface RecipeIngredientRefService extends RefService<RecipeIngredientRef, RecipeIngredientEntity> {
}
