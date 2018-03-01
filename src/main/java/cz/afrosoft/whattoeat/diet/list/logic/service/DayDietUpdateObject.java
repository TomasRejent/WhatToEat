package cz.afrosoft.whattoeat.diet.list.logic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import cz.afrosoft.whattoeat.diet.list.logic.model.MealRef;

public interface DayDietUpdateObject {

    Optional<Integer> getId();

    Optional<LocalDate> getDay();

    Optional<List<MealRef>> getBreakfasts();

    Optional<List<MealRef>> getSnacks();

    Optional<List<MealRef>> getLunch();

    Optional<List<MealRef>> getAfternoonSnacks();

    Optional<List<MealRef>> getDinners();

    Optional<List<MealRef>> getOthers();

    DayDietUpdateObject setDay(LocalDate day);

    DayDietUpdateObject setBreakfasts(List<MealRef> breakfasts);

    DayDietUpdateObject setSnacks(List<MealRef> snacks);

    DayDietUpdateObject setLunch(List<MealRef> lunch);

    DayDietUpdateObject setAfternoonSnacks(List<MealRef> afternoonSnacks);

    DayDietUpdateObject setDinners(List<MealRef> dinners);

    DayDietUpdateObject setOthers(List<MealRef> others);

}
