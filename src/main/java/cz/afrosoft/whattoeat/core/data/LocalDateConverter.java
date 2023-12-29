package cz.afrosoft.whattoeat.core.data;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter for persisting {@link LocalDate}.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(final LocalDate attribute) {
        if (attribute == null) {
            return null;
        } else {
            return Date.valueOf(attribute);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(final Date dbData) {
        if (dbData == null) {
            return null;
        } else {
            return dbData.toLocalDate();
        }
    }
}
