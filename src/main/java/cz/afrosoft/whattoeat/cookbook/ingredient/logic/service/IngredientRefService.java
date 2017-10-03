package cz.afrosoft.whattoeat.cookbook.ingredient.logic.service;

import cz.afrosoft.whattoeat.cookbook.ingredient.data.entity.IngredientEntity;
import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientRef;
import cz.afrosoft.whattoeat.core.logic.service.RefService;

/**
 * Provides methods for conversions between {@link IngredientRef} and
 * {@link IngredientEntity}.
 *
 * @author Tomas Rejent
 */
public interface IngredientRefService extends RefService<IngredientRef, IngredientEntity> {


}
