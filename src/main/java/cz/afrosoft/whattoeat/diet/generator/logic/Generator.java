package cz.afrosoft.whattoeat.diet.generator.logic;

import java.util.List;

import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

/**
 * Generator for creating Diets.
 *
 * @author tomas.rejent
 */
public interface Generator<T extends GeneratorParameters> {

    List<DayDietEntity> generate(T parameters);

    GeneratorType getType();

}
