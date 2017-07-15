package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

/**
 * Express recipe preparation time in fuzzy manner.
 *
 * @author Tomas Rejent
 */
public enum PreparationTime implements Labeled {
    /**
     * Up to 3 minutes. Like pick something from shelve.
     */
    INSTANT("cz.afrosoft.whattoeat.preparationTime.instant"),
    /**
     * Up to 10 minutes.
     */
    QUICK("cz.afrosoft.whattoeat.preparationTime.quick"),
    /**
     * Up to 30 minutes.
     */
    SHORT("cz.afrosoft.whattoeat.preparationTime.short"),
    /**
     * Up to 1 hour.
     */
    MEDIUM("cz.afrosoft.whattoeat.preparationTime.medium"),
    /**
     * Up to 2 hours.
     */
    LONG("cz.afrosoft.whattoeat.preparationTime.long"),
    /**
     * More than two hours.
     */
    FOREVER("cz.afrosoft.whattoeat.preparationTime.forever");

    private final String labelKey;

    PreparationTime(final String labelKey) {
        Validate.notBlank(labelKey);
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

}
