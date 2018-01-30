package cz.afrosoft.whattoeat.diet.list.data;

import cz.afrosoft.whattoeat.core.data.IdEnumConverter;
import cz.afrosoft.whattoeat.diet.generator.logic.GeneratorType;

import javax.persistence.Converter;

/**
 * Converter for {@link GeneratorType}. Allows persisting this enum only as its id in database.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class GeneratorTypeConverter extends IdEnumConverter<GeneratorType> {

    public GeneratorTypeConverter() {
        super(GeneratorType.class);
    }
}
