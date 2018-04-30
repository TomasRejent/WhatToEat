package cz.afrosoft.whattoeat.diet.list.logic.service;

import java.time.LocalDate;
import java.util.Optional;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;

/**
 * @author Tomas Rejent
 */
public interface DietCreateObject {

    Optional<String> getName();

    Optional<LocalDate> getFrom();

    Optional<LocalDate> getTo();

    Optional<GeneratorType> getGenerator();

    Optional<String> getDescription();

    Optional<GeneratorParameters> getGeneratorParams();

    DietCreateObject setName(String name);

    DietCreateObject setFrom(LocalDate from);

    DietCreateObject setTo(LocalDate to);

    DietCreateObject setDescription(String description);

    DietCreateObject setGenerator(GeneratorType generator);

    DietCreateObject setGeneratorParams(GeneratorParameters params);
}
