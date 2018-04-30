package cz.afrosoft.whattoeat.diet.list.logic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayDietUpdateObject {

    Optional<Integer> getId();

    Optional<LocalDate> getDay();

    Optional<List<MealUpdateObject>> getBreakfasts();

    Optional<List<MealUpdateObject>> getSnacks();

    Optional<List<MealUpdateObject>> getLunch();

    Optional<List<MealUpdateObject>> getAfternoonSnacks();

    Optional<List<MealUpdateObject>> getDinners();

    Optional<List<MealUpdateObject>> getOthers();

    DayDietUpdateObject setDay(LocalDate day);

    DayDietUpdateObject setBreakfasts(List<MealUpdateObject> breakfasts);

    DayDietUpdateObject setSnacks(List<MealUpdateObject> snacks);

    DayDietUpdateObject setLunch(List<MealUpdateObject> lunch);

    DayDietUpdateObject setAfternoonSnacks(List<MealUpdateObject> afternoonSnacks);

    DayDietUpdateObject setDinners(List<MealUpdateObject> dinners);

    DayDietUpdateObject setOthers(List<MealUpdateObject> others);

}
