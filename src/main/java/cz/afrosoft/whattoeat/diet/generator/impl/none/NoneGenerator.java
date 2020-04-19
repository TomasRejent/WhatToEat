package cz.afrosoft.whattoeat.diet.generator.impl.none;

import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.model.Generator;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tomas.rejent
 */
@Component
public class NoneGenerator implements Generator<BasicGeneratorParams> {

    @Override
    public GeneratorGui<BasicGeneratorParams> getGui() {
        return new NoneGeneratorGui();
    }

    @Override
    public List<DayDietEntity> generate(final BasicGeneratorParams parameters) {
        Validate.notNull(parameters);

        List<DayDietEntity> dayDiets = new LinkedList<>();
        for (LocalDate day = parameters.getFrom(); !day.isAfter(parameters.getTo()); day = day.plusDays(1)) {
            DayDietEntity dayDiet = new DayDietEntity();
            dayDiet.setDay(day);
            dayDiets.add(dayDiet);
        }

        return dayDiets;
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.NONE;
    }
}
