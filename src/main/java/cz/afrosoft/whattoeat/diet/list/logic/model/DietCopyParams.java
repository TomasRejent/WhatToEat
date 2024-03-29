package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;

import java.time.LocalDate;

/**
 * @author Tomas Rejent
 */
public class DietCopyParams {

    private String dietName;
    private User user;
    private LocalDate startDate;
    private MealCopyParams breakfastsParams;
    private MealCopyParams snacksParams;
    private MealCopyParams lunchParams;
    private MealCopyParams afternoonSnacksParams;
    private MealCopyParams dinnersParams;
    private MealCopyParams othersParams;

    public String getDietName() {
        return dietName;
    }

    public DietCopyParams setDietName(final String dietName) {
        this.dietName = dietName;
        return this;
    }

    public MealCopyParams getBreakfastsParams() {
        return breakfastsParams;
    }

    public DietCopyParams setBreakfastsParams(final MealCopyParams breakfastsParams) {
        this.breakfastsParams = breakfastsParams;
        return this;
    }

    public MealCopyParams getSnacksParams() {
        return snacksParams;
    }

    public DietCopyParams setSnacksParams(final MealCopyParams snacksParams) {
        this.snacksParams = snacksParams;
        return this;
    }

    public MealCopyParams getLunchParams() {
        return lunchParams;
    }

    public DietCopyParams setLunchParams(final MealCopyParams lunchParams) {
        this.lunchParams = lunchParams;
        return this;
    }

    public MealCopyParams getAfternoonSnacksParams() {
        return afternoonSnacksParams;
    }

    public DietCopyParams setAfternoonSnacksParams(final MealCopyParams afternoonSnacksParams) {
        this.afternoonSnacksParams = afternoonSnacksParams;
        return this;
    }

    public MealCopyParams getDinnersParams() {
        return dinnersParams;
    }

    public DietCopyParams setDinnersParams(final MealCopyParams dinnersParams) {
        this.dinnersParams = dinnersParams;
        return this;
    }

    public MealCopyParams getOthersParams() {
        return othersParams;
    }

    public DietCopyParams setOthersParams(final MealCopyParams othersParams) {
        this.othersParams = othersParams;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public DietCopyParams setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public DietCopyParams setUser(final User user) {
        this.user = user;
        return this;
    }
}
