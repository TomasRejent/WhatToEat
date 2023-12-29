package cz.afrosoft.whattoeat.cookbook.recipe.data;

import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.Taste;
import cz.afrosoft.whattoeat.core.data.IdEnumConverter;

import jakarta.persistence.Converter;

/**
 * Converter for {@link Taste}. Allows persisting this enum only as its id in database.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class TasteConverter extends IdEnumConverter<Taste> {

    public TasteConverter() {
        super(Taste.class);
    }
}
