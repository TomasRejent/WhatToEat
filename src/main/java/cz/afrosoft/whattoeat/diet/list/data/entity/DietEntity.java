package cz.afrosoft.whattoeat.diet.list.data.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;

/**
 * Represents diet for certain time interval. This diet defines meals for each day of its duration. Diet can be created
 * manually or by generator.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "DIET")
public class DietEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "FROM_DATE", nullable = false)
    private LocalDate from;

    @Column(name = "TO_DATE", nullable = false)
    private LocalDate to;

    @Column(name = "GENERATOR", nullable = false)
    private GeneratorType generator;

    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DayDietEntity> dayDiets;

    public Integer getId() {
        return id;
    }

    public DietEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DietEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public LocalDate getFrom() {
        return from;
    }

    public DietEntity setFrom(final LocalDate from) {
        this.from = from;
        return this;
    }

    public LocalDate getTo() {
        return to;
    }

    public DietEntity setTo(final LocalDate to) {
        this.to = to;
        return this;
    }

    public GeneratorType getGenerator() {
        return generator;
    }

    public DietEntity setGenerator(final GeneratorType generator) {
        this.generator = generator;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DietEntity setDescription(final String description) {
        this.description = description;
        return this;
    }

    public List<DayDietEntity> getDayDiets() {
        return dayDiets;
    }

    public DietEntity setDayDiets(final List<DayDietEntity> dayDiets) {
        this.dayDiets = dayDiets;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("name", name)
            .append("from", from)
            .append("to", to)
            .toString();
    }
}
