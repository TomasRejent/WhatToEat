package cz.afrosoft.whattoeat.diet.generator.logic;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

/**
 * @author tomas.rejent
 */
public class NoneGenerator implements Generator<GeneratorParameters> {

    @Override
    public List<DayDietEntity> generate(final GeneratorParameters parameters) {
        Validate.notNull(parameters);

        final LocalDate from = parameters.getFrom();
        final LocalDate to = parameters.getTo();
        final List<DayDietEntity> dayDiets = new LinkedList<>();
        for (LocalDate day = from; day.isBefore(to) || day.equals(to); day = day.plusDays(1)) {
            final DayDietEntity dayDietEntity = new DayDietEntity();
            dayDietEntity.setDay(day);
            dayDiets.add(dayDietEntity);
        }

        return dayDiets;
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.NONE;
    }
}
