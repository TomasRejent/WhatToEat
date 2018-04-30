package cz.afrosoft.whattoeat.diet.generator.service;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;

import java.util.List;

/**
 * @author Tomas Rejent
 */
public interface GeneratorService {

    GeneratorGui<?> getGuiForGenerator(GeneratorType generatorType);

    List<DayDietEntity> generate(GeneratorType generatorType, GeneratorParameters parameters);

}
