package cz.afrosoft.whattoeat.diet.gui.dto;

import cz.afrosoft.whattoeat.diet.logic.model.Meal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DTO containing all necessary information for displaying {@link cz.afrosoft.whattoeat.diet.logic.model.Meal} on front end.
 */
public class MealView {

    private Meal meal;
    private String recipeName;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MealView mealView = (MealView) o;

        return new EqualsBuilder()
                .append(meal, mealView.meal)
                .append(recipeName, mealView.recipeName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(meal)
                .append(recipeName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("meal", meal)
                .append("recipeName", recipeName)
                .toString();
    }
}
