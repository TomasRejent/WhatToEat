package cz.afrosoft.whattoeat.diet.generator.model;

import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.Node;

/**
 * @author Tomas Rejent
 */
public interface GeneratorGui<T extends GeneratorParameters> {

    void setInterval(LocalDate from, LocalDate to);

    T getParameters();

    Optional<Node> getNode();
}
