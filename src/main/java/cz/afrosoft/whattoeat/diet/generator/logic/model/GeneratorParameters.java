package cz.afrosoft.whattoeat.diet.generator.logic.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class GeneratorParameters implements Serializable{

    private String name;
    private LocalDate from;
    private LocalDate to;

    private Boolean breakfast;
    private Boolean morningSnack;
    private Boolean soup;
    private Boolean lunch;
    private Boolean sideDish;
    private Boolean afternoonSnack;
    private Boolean dinner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Boolean getMorningSnack() {
        return morningSnack;
    }

    public void setMorningSnack(Boolean morningSnack) {
        this.morningSnack = morningSnack;
    }

    public Boolean getSoup() {
        return soup;
    }

    public void setSoup(Boolean soup) {
        this.soup = soup;
    }

    public Boolean getLunch() {
        return lunch;
    }

    public void setLunch(Boolean lunch) {
        this.lunch = lunch;
    }

    public Boolean getSideDish() {
        return sideDish;
    }

    public void setSideDish(Boolean sideDish) {
        this.sideDish = sideDish;
    }

    public Boolean getAfternoonSnack() {
        return afternoonSnack;
    }

    public void setAfternoonSnack(Boolean afternoonSnack) {
        this.afternoonSnack = afternoonSnack;
    }

    public Boolean getDinner() {
        return dinner;
    }

    public void setDinner(Boolean dinner) {
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
