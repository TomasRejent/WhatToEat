package cz.afrosoft.whattoeat.cookbook.recipe.logic.model;

import cz.afrosoft.whattoeat.core.gui.Labeled;
import org.apache.commons.lang3.Validate;

import java.time.Duration;

/**
 * Express recipe preparation time in fuzzy manner.
 *
 * @author Tomas Rejent
 */
public enum PreparationTime implements Labeled {
    /**
     * Up to 3 minutes. Like pick something from shelve.
     */
    INSTANT(3, "cz.afrosoft.whattoeat.preparationTime.instant"),
    /**
     * Up to 10 minutes.
     */
    QUICK(10, "cz.afrosoft.whattoeat.preparationTime.quick"),
    /**
     * Up to 30 minutes.
     */
    SHORT(30, "cz.afrosoft.whattoeat.preparationTime.short"),
    /**
     * Up to 1 hour.
     */
    MEDIUM(60, "cz.afrosoft.whattoeat.preparationTime.medium"),
    /**
     * Up to 2 hours.
     */
    LONG(120, "cz.afrosoft.whattoeat.preparationTime.long"),
    /**
     * More than two hours.
     */
    FOREVER(Integer.MAX_VALUE, "cz.afrosoft.whattoeat.preparationTime.forever");

    /**
     * Maximum duration in minutes.
     */
    private int maximumDuration;

    private final String labelKey;

    PreparationTime(final int maximumDuration, final String labelKey) {
        Validate.notBlank(labelKey);
        Validate.isTrue(maximumDuration > 0);
        this.maximumDuration = maximumDuration;
        this.labelKey = labelKey;
    }

    /**
     * Converts specified duration to suitable preparation time.
     *
     * @param duration (NotNull) Duration to convert. Duration in minutes must not exceed {@link Integer#MAX_VALUE}.
     * @return (NotNull)
     * @throws IllegalArgumentException When duration is too long.
     */
    public static PreparationTime fromDuration(final Duration duration) {
        Validate.notNull(duration);

        for (PreparationTime preparationTime : PreparationTime.values()) {
            if (duration.toMinutes() <= preparationTime.maximumDuration) {
                return preparationTime;
            }
        }
        throw new IllegalArgumentException(
                String.format("Duration of recipe is out of bounds: %s minutes. Maximum allowed value is %s.",
                        duration.toMinutes(), Integer.MAX_VALUE));
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

}
