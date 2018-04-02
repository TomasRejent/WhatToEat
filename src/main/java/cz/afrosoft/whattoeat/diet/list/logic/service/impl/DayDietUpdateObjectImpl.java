package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;

/**
 * @author tomas.rejent
 */
final class DayDietUpdateObjectImpl implements DayDietUpdateObject {

    private final Integer id;
    private LocalDate day;
    private List<MealUpdateObject> breakfasts;
    private List<MealUpdateObject> snacks;
    private List<MealUpdateObject> lunch;
    private List<MealUpdateObject> afternoonSnacks;
    private List<MealUpdateObject> dinners;
    private List<MealUpdateObject> others;

    public DayDietUpdateObjectImpl() {
        this.id = null;
    }

    public DayDietUpdateObjectImpl(final Integer id) {
        Validate.notNull(id);
        this.id = id;
    }

    @Override
    public Optional<Integer> getId() {
        return Optional.ofNullable(id);
    }

    @Override
    public Optional<LocalDate> getDay() {
        return Optional.ofNullable(day);
    }

    @Override
    public Optional<List<MealUpdateObject>> getBreakfasts() {
        return Optional.ofNullable(breakfasts);
    }

    @Override
    public Optional<List<MealUpdateObject>> getSnacks() {
        return Optional.ofNullable(snacks);
    }

    @Override
    public Optional<List<MealUpdateObject>> getLunch() {
        return Optional.ofNullable(lunch);
    }

    @Override
    public Optional<List<MealUpdateObject>> getAfternoonSnacks() {
        return Optional.ofNullable(afternoonSnacks);
    }

    @Override
    public Optional<List<MealUpdateObject>> getDinners() {
        return Optional.ofNullable(dinners);
    }

    @Override
    public Optional<List<MealUpdateObject>> getOthers() {
        return Optional.ofNullable(others);
    }

    @Override
    public DayDietUpdateObjectImpl setDay(final LocalDate day) {
        this.day = day;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setBreakfasts(final List<MealUpdateObject> breakfasts) {
        this.breakfasts = breakfasts;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setSnacks(final List<MealUpdateObject> snacks) {
        this.snacks = snacks;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setLunch(final List<MealUpdateObject> lunch) {
        this.lunch = lunch;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setAfternoonSnacks(final List<MealUpdateObject> afternoonSnacks) {
        this.afternoonSnacks = afternoonSnacks;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setDinners(final List<MealUpdateObject> dinners) {
        this.dinners = dinners;
        return this;
    }

    @Override
    public DayDietUpdateObjectImpl setOthers(final List<MealUpdateObject> others) {
        this.others = others;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("day", day)
            .append("breakfasts", breakfasts)
            .append("snacks", snacks)
            .append("lunch", lunch)
            .append("afternoonSnacks", afternoonSnacks)
            .append("dinners", dinners)
            .append("others", others)
            .toString();
    }
}
