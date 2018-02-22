package cz.afrosoft.whattoeat.diet.list.logic.service;

import cz.afrosoft.whattoeat.diet.generator.logic.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public interface DietUpdateObject {

    Optional<Integer> getId();

    Optional<String> getName();

    Optional<LocalDate> getFrom();

    Optional<LocalDate> getTo();

    Optional<GeneratorType> getGenerator();

    Optional<String> getDescription();

    DietUpdateObject setName(String name);

    DietUpdateObject setFrom(LocalDate from);

    DietUpdateObject setTo(LocalDate to);

    DietUpdateObject setDescription(String description);

    DietUpdateObject setGenerator(GeneratorType generator);

    DietUpdateObject setDayDiets(List<DayDietRef> dayDiets);
}
