package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.core.data.IdEnumConverter;
import cz.afrosoft.whattoeat.oldclassesformigrationonly.OldRecipeType;

import javax.persistence.Converter;

/**
 * Converter for {@link OldRecipeType}. Allows persisting this enum only as its id in database.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class RecipeTypeConverter extends IdEnumConverter<RecipeType> {

    public RecipeTypeConverter() {
        super(RecipeType.class);
    }
}
