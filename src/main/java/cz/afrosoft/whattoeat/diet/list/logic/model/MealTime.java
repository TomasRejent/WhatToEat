package cz.afrosoft.whattoeat.diet.list.logic.model;

import cz.afrosoft.whattoeat.core.gui.Labeled;

/**
 * @author Tomas Rejent
 */
public enum MealTime implements Labeled {
    BREAKFAST("cz.afrosoft.whattoeat.dietview.table.breakfasts"),
    MORNING_SNACK("cz.afrosoft.whattoeat.dietview.table.morningSnacks"),
    LUNCH("cz.afrosoft.whattoeat.dietview.table.lunch"),
    AFTERNOON_SNACK("cz.afrosoft.whattoeat.dietview.table.afternoonSnack"),
    DINNER("cz.afrosoft.whattoeat.dietview.table.dinners");

    private String labelKey;

    MealTime(final String labelKey) {
        this.labelKey = labelKey;
    }


    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
