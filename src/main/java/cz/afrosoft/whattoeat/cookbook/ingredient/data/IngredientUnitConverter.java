package cz.afrosoft.whattoeat.cookbook.ingredient.data;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.model.IngredientUnit;
import cz.afrosoft.whattoeat.core.data.IdEnumConverter;

import javax.persistence.Converter;

/**
 * Converter for {@link IngredientUnit}. Allows persisting this enum only as its id in database.
 *
 * @author Tomas Rejent
 */
@Converter
public class IngredientUnitConverter extends IdEnumConverter<IngredientUnit> {

    public IngredientUnitConverter() {
        super(IngredientUnit.class);
    }
}
