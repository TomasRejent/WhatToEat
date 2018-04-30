package cz.afrosoft.whattoeat.diet.generator.model;

import java.time.LocalDate;

/**
 * @author tomas.rejent
 */
public interface GeneratorParameters {

    LocalDate getFrom();

    LocalDate getTo();

}
