package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.core.logic.model.IdEntity;

import java.util.List;

/**
 * Represents diet for one day. It contains list of meals for usual meal times, like breakfast. However ot does not restrict type of meal,
 * so if you want you could have recipe of soup type for breakfast, instead of lunch as is usual.
 *
 * @author Tomas Rejent
 */
public interface DayDiet extends IdEntity, DayDietRef, Comparable<DayDietRef> {

    /**
     * @return (NotNull) List of meals for breakfast. May be empty.
     */
    List<Meal> getBreakfasts();

    /**
     * @return (NotNull) List of meals for snack. May be empty.
     */
    List<Meal> getSnacks();

    /**
     * @return (NotNull) List of meals for lunch. May be empty.
     */
    List<Meal> getLunch();

    /**
     * @return (NotNull) List of meals for afternoon snack. May be empty.
     */
    List<Meal> getAfternoonSnacks();

    /**
     * @return (NotNull) List of meals for dinner. May be empty.
     */
    List<Meal> getDinners();

    /**
     * @return (NotNull) List of meals which does not fit any predefined category. This allows more flexibility. May be empty.
     */
    List<Meal> getOthers();
}
