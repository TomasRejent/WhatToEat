package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealRef;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

final class DayDietImpl implements DayDiet{

    private final Integer id;
    private final LocalDate day;
    private final List<Meal> breakfasts;
    private final List<Meal> snacks;
    private final List<Meal> lunch;
    private final List<Meal> afternoonSnacks;
    private final List<Meal> dinners;
    private final List<Meal> others;

    private DayDietImpl(final Integer id, final LocalDate day, final List<Meal> breakfasts, final List<Meal> snacks, final List<Meal> lunch, final List<Meal>
            afternoonSnacks, final List<Meal> dinners, final List<Meal> others) {
        this.id = id;
        this.day = day;
        this.breakfasts = breakfasts;
        this.snacks = snacks;
        this.lunch = lunch;
        this.afternoonSnacks = afternoonSnacks;
        this.dinners = dinners;
        this.others = others;
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
    public List<Meal> getBreakfasts() {
        return breakfasts;
    }

    @Override
    public List<Meal> getSnacks() {
        return snacks;
    }

    @Override
    public List<Meal> getLunch() {
        return lunch;
    }

    @Override
    public List<Meal> getAfternoonSnacks() {
        return afternoonSnacks;
    }

    @Override
    public List<Meal> getDinners() {
        return dinners;
    }

    @Override
    public List<Meal> getOthers() {
        return others;
    }

    @Override
    public int compareTo(final DayDietRef otherDayDiet) {
        return DayDietComparator.INSTANCE.compare(this, otherDayDiet);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DayDietImpl dayDiet = (DayDietImpl) o;

        return new EqualsBuilder()
                .append(id, dayDiet.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("day", day)
                .toString();
    }

    static final class Builder {

        private Integer id;
        private LocalDate day;
        private List<Meal> breakfasts;
        private List<Meal> snacks;
        private List<Meal> lunch;
        private List<Meal> afternoonSnacks;
        private List<Meal> dinners;
        private List<Meal> others;

        public Builder(final Integer id) {
            Validate.notNull(id);
            this.id = id;
        }

        public Builder setDay(final LocalDate day) {
            this.day = day;
            return this;
        }

        public Builder setBreakfasts(final List<Meal> breakfasts) {
            this.breakfasts = breakfasts;
            return this;
        }

        public Builder setSnacks(final List<Meal> snacks) {
            this.snacks = snacks;
            return this;
        }

        public Builder setLunch(final List<Meal> lunch) {
            this.lunch = lunch;
            return this;
        }

        public Builder setAfternoonSnacks(final List<Meal> afternoonSnacks) {
            this.afternoonSnacks = afternoonSnacks;
            return this;
        }

        public Builder setDinners(final List<Meal> dinners) {
            this.dinners = dinners;
            return this;
        }

        public Builder setOthers(final List<Meal> others) {
            this.others = others;
            return this;
        }

        DayDiet build() {
            Validate.notNull(id);
            Validate.notNull(breakfasts);
            Validate.notNull(snacks);
            Validate.notNull(lunch);
            Validate.notNull(afternoonSnacks);
            Validate.notNull(dinners);
            Validate.notNull(others);

            return new DayDietImpl(id, day, breakfasts, snacks, lunch, afternoonSnacks, dinners, others);
        }
    }

    static final class DayDietUpdateObjectImpl implements DayDietUpdateObject {

        private final Integer id;
        private LocalDate day;
        private List<MealRef> breakfasts;
        private List<MealRef> snacks;
        private List<MealRef> lunch;
        private List<MealRef> afternoonSnacks;
        private List<MealRef> dinners;
        private List<MealRef> others;

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
        public Optional<List<MealRef>> getBreakfasts() {
            return Optional.ofNullable(breakfasts);
        }

        @Override
        public Optional<List<MealRef>> getSnacks() {
            return Optional.ofNullable(snacks);
        }

        @Override
        public Optional<List<MealRef>> getLunch() {
            return Optional.ofNullable(lunch);
        }

        @Override
        public Optional<List<MealRef>> getAfternoonSnacks() {
            return Optional.ofNullable(afternoonSnacks);
        }

        @Override
        public Optional<List<MealRef>> getDinners() {
            return Optional.ofNullable(dinners);
        }

        @Override
        public Optional<List<MealRef>> getOthers() {
            return Optional.ofNullable(others);
        }

        @Override
        public DayDietUpdateObjectImpl setDay(final LocalDate day) {
            this.day = day;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setBreakfasts(final List<MealRef> breakfasts) {
            this.breakfasts = breakfasts;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setSnacks(final List<MealRef> snacks) {
            this.snacks = snacks;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setLunch(final List<MealRef> lunch) {
            this.lunch = lunch;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setAfternoonSnacks(final List<MealRef> afternoonSnacks) {
            this.afternoonSnacks = afternoonSnacks;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setDinners(final List<MealRef> dinners) {
            this.dinners = dinners;
            return this;
        }

        @Override
        public DayDietUpdateObjectImpl setOthers(final List<MealRef> others) {
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
}
