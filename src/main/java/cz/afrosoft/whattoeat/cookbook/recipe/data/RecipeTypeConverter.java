package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.core.data.IdEnumConverter;

import javax.persistence.Converter;

/**
 * Converter for {@link RecipeType}. Allows persisting this enum only as its id in database.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class RecipeTypeConverter extends IdEnumConverter<RecipeType> {

    public RecipeTypeConverter() {
        super(RecipeType.class);
    }
}
