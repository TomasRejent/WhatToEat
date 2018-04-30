package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

/**
 * @author Tomas Rejent
 */
public class DayDietRefImpl implements DayDietRef {

    private final Integer id;
    private final LocalDate day;

    public DayDietRefImpl(final Integer id, final LocalDate day) {
        Validate.notNull(id);
        Validate.notNull(day);

        this.id = id;
        this.day = day;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public LocalDate getDay() {
        return day;
    }

    @Override
    public int compareTo(final DayDietRef otherDayDiet) {
        return DayDietComparator.INSTANCE.compare(this, otherDayDiet);
    }
}
