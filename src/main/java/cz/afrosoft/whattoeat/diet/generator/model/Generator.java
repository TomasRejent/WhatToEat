package cz.afrosoft.whattoeat.diet.generator.model;

import java.util.List;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

/**
 * Generator for creating Diets.
 *
 * @author tomas.rejent
 */
public interface Generator<T extends GeneratorParameters> {

    GeneratorType getType();

    GeneratorGui<T> getGui();

    List<DayDietEntity> generate(T parameters);

}
