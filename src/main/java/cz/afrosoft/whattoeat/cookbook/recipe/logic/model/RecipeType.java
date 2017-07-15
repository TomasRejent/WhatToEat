package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.data.IdEnum;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

/**
 * Specifies type of recipe. Recipe can have several types at once. This is because boundaries between types cannot be
 * exactly defined. Recipe type does not explicitly restrict food generation, so there can be for example generator which allows
 * soup type recipes to be used for breakfast meals.
 *
 * @author Tomas Rejent
 */
public enum RecipeType implements IdEnum, Labeled {
    /**
     * Recipes which can be used to prepare breakfast.
     */
    BREAKFAST(1, "cz.afrosoft.whattoeat.recipeType.breakfast"),
    /**
     * Recipes which can be used to prepare snacks, small meals consumed at forenoon or afternoon.
     */
    SNACK(2, "cz.afrosoft.whattoeat.recipeType.snack"),
    /**
     * Recipes which can be used to prepare soups.
     */
    SOUP(3, "cz.afrosoft.whattoeat.recipeType.soup"),
    /**
     * Recipes which can be used to prepare main dish for meal of the day. This can be either lunch or dinner.
     */
    MAIN_DISH(4, "cz.afrosoft.whattoeat.recipeType.mainDish"),
    /**
     * Recipes which can be used to prepare side dish to accompany main dish.
     */
    SIDE_DISH(5, "cz.afrosoft.whattoeat.recipeType.sideDish"),
    /**
     * Recipes which can be used to prepare deserts, which are typically consumed after main dish.
     */
    DESSERT(6, "cz.afrosoft.whattoeat.recipeType.salad"),
    /**
     * Recipes which can be used to prepare salads. These can typically consist of vegetables or fruits.
     */
    SALAD(7, "cz.afrosoft.whattoeat.recipeType.salad");

    private final int id;
    private final String labelKey;

    RecipeType(final int id, final String labelKey) {
        Validate.notBlank(labelKey);
        this.id = id;
        this.labelKey = labelKey;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
