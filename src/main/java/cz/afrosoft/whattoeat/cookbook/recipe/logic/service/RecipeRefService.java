package cz.afrosoft.whattoeat.cookbook.recipe.logic.service;

import cz.afrosoft.whattoeat.cookbook.recipe.data.entity.RecipeEntity;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeRef;
import cz.afrosoft.whattoeat.core.logic.service.RefService;

/**
 * Provides methods for conversions between {@link RecipeRef} and
 * {@link RecipeEntity}.
 */
public interface RecipeRefService extends RefService<RecipeRef, RecipeEntity> {
}
