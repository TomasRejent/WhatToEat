package cz.afrosoft.whattoeat.diet.list.logic.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.core.logic.model.IdEntity;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;

/**
 * Represents diet for certain time interval.
 *
 * @author Tomas Rejent
 */
public interface Diet extends IdEntity, Comparable<Diet> {

    /**
     * Gets name of diet.
     *
     * @return (NotBlank) Name of diet which serves as human readable identifier. However it may not be unique.
     */
    String getName();

    /**
     * Gets day when diet starts. From date is inclusive. May be equal to {@link #getTo()}, but cannot be after it.
     *
     * @return (NotNull)
     */
    LocalDate getFrom();

    /**
     * Gets day when diet ends. To date is inclusive. May be equal to {@link #getFrom()}, but cannot be before it.
     *
     * @return (NotNull)
     */
    LocalDate getTo();

    /**
     * Gets generator which was used to create this diet. If diet was created manually by user, then {@link GeneratorType#NONE} is returned.
     *
     * @return (NotNull)
     */
    GeneratorType getGeneratorType();

    /**
     * Gets description of diet. This contains more details about diet. May be empty.
     *
     * @return (NotNull)
     */
    Optional<String> getDescription();

    User getUser();

    /**
     * Gets all day diets for this diet. Number of items in set should match duration in days between from and to.
     *
     * @return (NotNull)
     */
    List<DayDietRef> getDayDiets();
}
