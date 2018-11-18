package cz.afrosoft.whattoeat.diet.generator.model;

import cz.afrosoft.whattoeat.diet.generator.impl.random.RandomGenerator;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import cz.afrosoft.whattoeat.core.data.IdEnum;
import cz.afrosoft.whattoeat.core.gui.Labeled;
import cz.afrosoft.whattoeat.diet.generator.impl.none.NoneGenerator;

/**
 * Identifies generator of diets. Every generator has one unique entry in this enum.
 *
 * @author Tomas Rejent
 */
public enum GeneratorType implements IdEnum, Labeled {
    NONE(0, "cz.afrosoft.whattoeat.common.none", NoneGenerator.class),
    RANDOM(1, "cz.afrosoft.whattoeat.diet.generator.random.name", RandomGenerator.class);

    private final int id;
    private final String labelKey;
    private final Class<? extends Generator<?>> generatorClass;

    GeneratorType(final int id, final String labelKey, final Class<? extends Generator<?>> generatorClass) {
        Validate.notBlank(labelKey);
        Validate.notNull(generatorClass);
        this.id = id;
        this.labelKey = labelKey;
        this.generatorClass = generatorClass;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

    /**
     * @return (NotNull) Class implementing this generator type.
     */
    public Class<? extends Generator<?>> getGeneratorClass() {
        return generatorClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("labelKey", labelKey)
            .append("generatorClass", generatorClass)
            .toString();
    }
}
