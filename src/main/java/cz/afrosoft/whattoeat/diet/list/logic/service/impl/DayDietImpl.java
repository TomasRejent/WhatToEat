package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealRef;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;

final class DayDietImpl implements DayDiet{

    private final Integer id;
    private final LocalDate day;
    private final List<MealRef> breakfasts;
    private final List<MealRef> snacks;
    private final List<MealRef> lunch;
    private final List<MealRef> afternoonSnacks;
    private final List<MealRef> dinners;
    private final List<MealRef> others;

    private DayDietImpl(final Integer id, final LocalDate day, final List<MealRef> breakfasts, final List<MealRef> snacks, final List<MealRef> lunch, final List<MealRef>
        afternoonSnacks, final List<MealRef> dinners, final List<MealRef> others) {
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
    public List<MealRef> getBreakfasts() {
        return breakfasts;
    }

    @Override
    public List<MealRef> getSnacks() {
        return snacks;
    }

    @Override
    public List<MealRef> getLunch() {
        return lunch;
    }

    @Override
    public List<MealRef> getAfternoonSnacks() {
        return afternoonSnacks;
    }

    @Override
    public List<MealRef> getDinners() {
        return dinners;
    }

    @Override
    public List<MealRef> getOthers() {
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

    static final class Builder implements DayDietUpdateObject {

        private final Integer id;
        private LocalDate day;
        private List<MealRef> breakfasts;
        private List<MealRef> snacks;
        private List<MealRef> lunch;
        private List<MealRef> afternoonSnacks;
        private List<MealRef> dinners;
        private List<MealRef> others;

        public Builder() {
            this.id = null;
        }

        public Builder(final Integer id) {
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
        public Builder setDay(final LocalDate day) {
            this.day = day;
            return this;
        }

        @Override
        public Builder setBreakfasts(final List<MealRef> breakfasts) {
            this.breakfasts = breakfasts;
            return this;
        }

        @Override
        public Builder setSnacks(final List<MealRef> snacks) {
            this.snacks = snacks;
            return this;
        }

        @Override
        public Builder setLunch(final List<MealRef> lunch) {
            this.lunch = lunch;
            return this;
        }

        @Override
        public Builder setAfternoonSnacks(final List<MealRef> afternoonSnacks) {
            this.afternoonSnacks = afternoonSnacks;
            return this;
        }

        @Override
        public Builder setDinners(final List<MealRef> dinners) {
            this.dinners = dinners;
            return this;
        }

        @Override
        public Builder setOthers(final List<MealRef> others) {
            this.others = others;
            return this;
        }

        DayDiet build(){
            Validate.notNull(id);
            Validate.notNull(day);
            Validate.notNull(breakfasts);
            Validate.notNull(snacks);
            Validate.notNull(lunch);
            Validate.notNull(afternoonSnacks);
            Validate.notNull(dinners);
            Validate.notNull(others);

            return new DayDietImpl(id, day, breakfasts, snacks, lunch, afternoonSnacks, dinners, others);
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
