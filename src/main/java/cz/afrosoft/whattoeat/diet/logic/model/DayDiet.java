/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.diet.logic.model;

import java.io.Serializable;
import java.time.LocalDate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class DayDiet implements Serializable, Comparable<DayDiet> {

    private LocalDate day;

    private Meal breakfast;
    private Meal morningSnack;

    private Meal soup;
    private Meal lunch;
    private Meal sideDish;

    private Meal afternoonSnack;
    private Meal dinner;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getMorningSnack() {
        return morningSnack;
    }

    public void setMorningSnack(Meal morningSnack) {
        this.morningSnack = morningSnack;
    }

    public Meal getSoup() {
        return soup;
    }

    public void setSoup(Meal soup) {
        this.soup = soup;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getSideDish() {
        return sideDish;
    }

    public void setSideDish(Meal sideDish) {
        this.sideDish = sideDish;
    }

    public Meal getAfternoonSnack() {
        return afternoonSnack;
    }

    public void setAfternoonSnack(Meal afternoonSnack) {
        this.afternoonSnack = afternoonSnack;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }

    @Override
    public int compareTo(DayDiet comparedDiet) {
        if(comparedDiet == null){
            return -1;
        }

        if(comparedDiet.getDay() == null && this.day == null){
            return 0;
        }

        if(this.day == null){
            return 1;
        }

        return this.day.compareTo(comparedDiet.getDay());
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
