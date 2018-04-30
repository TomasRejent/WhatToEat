package cz.afrosoft.whattoeat.diet.generator.model;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

import java.util.List;

/**
 * Generator for creating Diets.
 *
 * @author tomas.rejent
 */
public interface Generator<T extends GeneratorParameters> {

    GeneratorType getType();

    GeneratorGui<T> getGui();

    List<DayDietEntity> generate(GeneratorParameters parameters);

}
