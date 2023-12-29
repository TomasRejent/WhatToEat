package cz.afrosoft.whattoeat.core.data;

import org.apache.commons.lang3.Validate;

import jakarta.persistence.AttributeConverter;

/**
 * Base class for {@link AttributeConverter} of {@link IdEnum}. This implementation allows null values to be converted
 * to null results.
 *
 * @author Tomas Rejent
 */
public abstract class IdEnumConverter<T extends IdEnum> implements AttributeConverter<T, Integer> {

    private final Class<T> enumClass;

    public IdEnumConverter(final Class<T> enumClass) {
        Validate.notNull(enumClass);
        this.enumClass = enumClass;
    }

    @Override
    public Integer convertToDatabaseColumn(final T attribute) {
        if (attribute == null) {
            return null;
        } else {
            return attribute.getId();
        }
    }

    @Override
    public T convertToEntityAttribute(final Integer dbData) {
        if (dbData == null) {
            return null;
        } else {
            return IdEnum.valueOf(enumClass, dbData);
        }
    }
}
