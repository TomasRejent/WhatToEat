package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.data.IdEnum;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

/**
 * Indicates taste of recipe. Recipe can have only one taste.
 *
 * @author Tomas Rejent
 */
public enum Taste implements IdEnum, Labeled {
    /**
     * Sweet taste. Typically meals consisting of sugar or honey.
     */
    SWEET(1, "cz.afrosoft.whattoeat.taste.sweet"),
    /**
     * Salty taste. Typical for meat meals.
     */
    SALTY(2, "cz.afrosoft.whattoeat.taste.salty"),
    /**
     * Neutral taste. For example bread, which can be consumed with both sweet or salty additions.
     */
    NEUTRAL(3, "cz.afrosoft.whattoeat.taste.neutral");

    private final int id;
    private final String labelKey;

    Taste(final int id, final String labelKey) {
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
