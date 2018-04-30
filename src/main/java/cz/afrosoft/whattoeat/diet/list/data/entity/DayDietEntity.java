package cz.afrosoft.whattoeat.diet.list.data.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents all meals for one day. Meals are grouped in lists representing typical meal times. List enables serving of multiple dishes
 * in one time. For example lunch can consist of soup, main dish and desert.
 *
 * @author Tomas Rejent
 */
@Entity
@Table(name = "DAY_DIET")
public class DayDietEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "DAY", nullable = false)
    private LocalDate day;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> breakfast;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> snack;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> lunch;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> afternoonSnack;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> dinner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntity> other;

    public Integer getId() {
        return id;
    }

    public DayDietEntity setId(final Integer id) {
        this.id = id;
        return this;
    }

    public LocalDate getDay() {
        return day;
    }

    public DayDietEntity setDay(final LocalDate day) {
        this.day = day;
        return this;
    }

    public List<MealEntity> getBreakfast() {
        return breakfast;
    }

    public DayDietEntity setBreakfast(final List<MealEntity> breakfast) {
        this.breakfast = breakfast;
        return this;
    }

    public List<MealEntity> getSnack() {
        return snack;
    }

    public DayDietEntity setSnack(final List<MealEntity> snack) {
        this.snack = snack;
        return this;
    }

    public List<MealEntity> getLunch() {
        return lunch;
    }

    public DayDietEntity setLunch(final List<MealEntity> lunch) {
        this.lunch = lunch;
        return this;
    }

    public List<MealEntity> getAfternoonSnack() {
        return afternoonSnack;
    }

    public DayDietEntity setAfternoonSnack(final List<MealEntity> afternoonSnack) {
        this.afternoonSnack = afternoonSnack;
        return this;
    }

    public List<MealEntity> getDinner() {
        return dinner;
    }

    public DayDietEntity setDinner(final List<MealEntity> dinner) {
        this.dinner = dinner;
        return this;
    }

    public List<MealEntity> getOther() {
        return other;
    }

    public DayDietEntity setOther(final List<MealEntity> other) {
        this.other = other;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("day", day)
                .toString();
    }
}
