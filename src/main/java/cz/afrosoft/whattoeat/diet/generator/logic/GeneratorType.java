package cz.afrosoft.whattoeat.diet.generator.logic;

import cz.afrosoft.whattoeat.core.data.IdEnum;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

/**
 * Identifies generator of diets. Every generator has one unique entry in this enum.
 *
 * @author Tomas Rejent
 */
public enum GeneratorType implements IdEnum, Labeled {

    ;

    private final int id;
    private final String labelKey;

    GeneratorType(final int id, final String labelKey) {
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
