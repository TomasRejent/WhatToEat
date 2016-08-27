/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.afrosoft.whattoeat.logic.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Tomas Rejent
 */
public class DayDiet implements Serializable, Comparable<DayDiet> {

    private LocalDate day;

    private String breakfast;
    private int breakfastServings = 1;
    private String morningSnack;
    private int morningSnackServings = 1;

    private String soup;
    private int soupServings = 1;
    private String lunch;
    private int lunchServings = 1;
    private String sideDish;
    private int sideDishServings = 1;

    private String afternoonSnack;
    private int afternoonSnackServings = 1;
    private String dinner;
    private int dinnerServings = 1;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getMorningSnack() {
        return morningSnack;
    }

    public void setMorningSnack(String morningSnack) {
        this.morningSnack = morningSnack;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getSideDish() {
        return sideDish;
    }

    public void setSideDish(String sideDish) {
        this.sideDish = sideDish;
    }

    public String getAfternoonSnack() {
        return afternoonSnack;
    }

    public void setAfternoonSnack(String afternoonSnack) {
        this.afternoonSnack = afternoonSnack;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public int getBreakfastServings() {
        return breakfastServings;
    }

    public void setBreakfastServings(int breakfastServings) {
        this.breakfastServings = breakfastServings;
    }

    public int getMorningSnackServings() {
        return morningSnackServings;
    }

    public void setMorningSnackServings(int morningSnackServings) {
        this.morningSnackServings = morningSnackServings;
    }

    public int getSoupServings() {
        return soupServings;
    }

    public void setSoupServings(int soupServings) {
        this.soupServings = soupServings;
    }

    public int getLunchServings() {
        return lunchServings;
    }

    public void setLunchServings(int lunchServings) {
        this.lunchServings = lunchServings;
    }

    public int getSideDishServings() {
        return sideDishServings;
    }

    public void setSideDishServings(int sideDishServings) {
        this.sideDishServings = sideDishServings;
    }

    public int getAfternoonSnackServings() {
        return afternoonSnackServings;
    }

    public void setAfternoonSnackServings(int afternoonSnackServings) {
        this.afternoonSnackServings = afternoonSnackServings;
    }

    public int getDinnerServings() {
        return dinnerServings;
    }

    public void setDinnerServings(int dinnerServings) {
        this.dinnerServings = dinnerServings;
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
