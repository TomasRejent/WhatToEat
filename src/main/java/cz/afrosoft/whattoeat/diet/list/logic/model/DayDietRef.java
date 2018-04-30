package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

import java.time.LocalDate;

/**
 * Represents reference to {@link DayDiet} which can be used in related entities.
 * Reference provides only limited subset of attributes and does not contain any other
 * related entities. It always contains id and in this case day of DayDiet.
 *
 * @author Tomas Rejent
 */
public interface DayDietRef extends IdEntity, Comparable<DayDietRef> {

    /**
     * Day to which this day diet belongs.
     *
     * @return (NotNull)
     */
    LocalDate getDay();

}
