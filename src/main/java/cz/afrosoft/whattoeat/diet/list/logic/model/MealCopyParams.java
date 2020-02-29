package cz.afrosoft.whattoeat.diet.list.logic.model;

/**
 * @author Tomas Rejent
 */
public class MealCopyParams {

    public static final float DEFAULT_SERVINGS = 1.0f;

    private boolean copyEnabled;
    private float servings = DEFAULT_SERVINGS;

    public MealCopyParams(final boolean copyEnabled, final float servings) {
        this.copyEnabled = copyEnabled;
        this.servings = servings;
    }

    public boolean isCopyEnabled() {
        return copyEnabled;
    }

    public MealCopyParams setCopyEnabled(final boolean copyEnabled) {
        this.copyEnabled = copyEnabled;
        return this;
    }

    public float getServings() {
        return servings;
    }

    public MealCopyParams setServings(final float servings) {
        this.servings = servings;
        return this;
    }
}
