package cz.afrosoft.whattoeat.diet.generator.impl.nutrition;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Tomas Rejent
 */
public class NutritionCriteria {

    private final NutritionCriteriaType type;
    private final float targetAmount;
    private final float aboveTolerance;
    private final float belowTolerance;
    private final int priority;

    public NutritionCriteria(final NutritionCriteriaType type, final float targetAmount, final float aboveTolerance, final float belowTolerance, final int priority) {
        Validate.notNull(type);
        Validate.isTrue(targetAmount >= 0);
        Validate.isTrue(aboveTolerance >= 0);
        Validate.isTrue(belowTolerance >= 0);
        Validate.isTrue(priority >= 0);

        this.type = type;
        this.targetAmount = targetAmount;
        this.aboveTolerance = aboveTolerance;
        this.belowTolerance = belowTolerance;
        this.priority = priority;
    }

    public NutritionCriteriaType getType() {
        return type;
    }

    public float getTargetAmount() {
        return targetAmount;
    }

    public float getAboveTolerance() {
        return aboveTolerance;
    }

    public float getBelowTolerance() {
        return belowTolerance;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("targetAmount", targetAmount)
                .append("aboveTolerance", aboveTolerance)
                .append("belowTolerance", belowTolerance)
                .append("priority", priority)
                .toString();
    }
}
