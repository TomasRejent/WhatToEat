package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;

import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;

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

}
