package cz.afrosoft.whattoeat.diet.generator.service.impl;

import cz.afrosoft.whattoeat.diet.generator.model.Generator;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tomas Rejent
 */
@Service
class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public GeneratorGui<?> getGuiForGenerator(final GeneratorType generatorType) {
        Validate.notNull(generatorType);
        return getGeneratorByType(generatorType).getGui();
    }

    @Override
    public List<DayDietEntity> generate(final GeneratorType generatorType, final GeneratorParameters parameters) {
        Validate.notNull(generatorType);
        Validate.notNull(parameters);
        return getGeneratorByType(generatorType).generate(parameters);
    }

    /**
     * Gets generator by type.
     *
     * @param generatorType (NotNull) Type of generator to get.
     * @return (NotNull) Generator of specified type.
     */
    private Generator<?> getGeneratorByType(final GeneratorType generatorType) {
        Validate.notNull(generatorType);
        return applicationContext.getBean(generatorType.getGeneratorClass());
    }
}
