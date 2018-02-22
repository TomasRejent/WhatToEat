package cz.afrosoft.whattoeat.diet.list.logic.service.impl;

import cz.afrosoft.whattoeat.diet.generator.logic.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietUpdateObject;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
final class DietImpl implements Diet {

    private final Integer id;
    private final String name;
    private final LocalDate from;
    private final LocalDate to;
    private final GeneratorType generatorType;
    private final String description;
    private final List<DayDietRef> dayDiets;

    public DietImpl(final Integer id, final String name, final LocalDate from, final LocalDate to, final GeneratorType generator, final String description, final List<DayDietRef> dayDiets) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.generatorType = generator;
        this.description = description;
        this.dayDiets = Collections.unmodifiableList(new ArrayList<>(dayDiets));
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getFrom() {
        return from;
    }

    @Override
    public LocalDate getTo() {
        return to;
    }

    @Override
    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public List<DayDietRef> getDayDiets() {
        return dayDiets;
    }

    @Override
    public int compareTo(final Diet otherDiet) {
        return DietComparator.INSTANCE.compare(this, otherDiet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("from", from)
                .append("to", to)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DietImpl diet = (DietImpl) o;

        return new EqualsBuilder()
                .append(id, diet.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    static final class Builder implements DietUpdateObject {

        private final Integer id;
        private String name;
        private LocalDate from;
        private LocalDate to;
        private GeneratorType generatorType;
        private String description;
        private List<DayDietRef> dayDiets;

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
        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public Optional<LocalDate> getFrom() {
            return Optional.ofNullable(from);
        }

        @Override
        public Optional<LocalDate> getTo() {
            return Optional.ofNullable(to);
        }

        @Override
        public Optional<GeneratorType> getGenerator() {
            return Optional.ofNullable(generatorType);
        }

        @Override
        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public DietUpdateObject setName(final String name) {
            Validate.notBlank(name);
            this.name = name;
            return this;
        }

        @Override
        public DietUpdateObject setFrom(final LocalDate from) {
            Validate.notNull(from);
            this.from = from;
            return this;
        }

        @Override
        public DietUpdateObject setTo(final LocalDate to) {
            Validate.notNull(to);
            this.to = to;
            return this;
        }

        @Override
        public DietUpdateObject setDescription(final String description) {
            this.description = description;
            return this;
        }

        @Override
        public DietUpdateObject setGenerator(final GeneratorType generator) {
            Validate.notNull(generator);
            this.generatorType = generator;
            return this;
        }

        @Override
        public DietUpdateObject setDayDiets(final List<DayDietRef> dayDiets) {
            Validate.notNull(dayDiets);
            this.dayDiets = dayDiets;
            return this;
        }

        Diet build() {
            Validate.notNull(id);
            Validate.notBlank(name);
            Validate.notNull(from);
            Validate.notNull(to);
            Validate.notNull(generatorType);
            Validate.notNull(dayDiets);

            return new DietImpl(id, name, from, to, generatorType, description, dayDiets);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .append("from", from)
                    .append("to", to)
                    .append("generatorType", generatorType)
                    .append("description", description)
                    .append("dayDiets", dayDiets)
                    .toString();
        }
    }
}
