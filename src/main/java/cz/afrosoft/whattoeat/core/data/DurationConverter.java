package cz.afrosoft.whattoeat.core.data;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * Converter for persisting {@link Duration}.
 *
 * @author Tomas Rejent
 */
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(final Duration attribute) {
        if (attribute == null) {
            return null;
        } else {
            return attribute.toMillis();
        }
    }

    @Override
    public Duration convertToEntityAttribute(final Long dbData) {
        if (dbData == null) {
            return null;
        } else {
            return Duration.ofMillis(dbData);
        }
    }
}
