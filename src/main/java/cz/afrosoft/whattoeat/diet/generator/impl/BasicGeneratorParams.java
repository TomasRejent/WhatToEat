package cz.afrosoft.whattoeat.diet.generator.impl;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

/**
 * @author Tomas Rejent
 */
public class BasicGeneratorParams implements GeneratorParameters {

    private final LocalDate from;
    private final LocalDate to;

    public BasicGeneratorParams(final LocalDate from, final LocalDate to) {
        Validate.notNull(from);
        Validate.notNull(to);
        Validate.isTrue(!to.isBefore(from), "From date must be before or equal to to date.");

        this.from = from;
        this.to = to;
    }

    @Override
    public LocalDate getFrom() {
        return from;
    }

    @Override
    public LocalDate getTo() {
        return to;
    }
}
