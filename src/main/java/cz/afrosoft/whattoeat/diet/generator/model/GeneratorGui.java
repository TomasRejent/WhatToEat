package cz.afrosoft.whattoeat.diet.generator.model;

import javafx.scene.Node;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public interface GeneratorGui<T extends GeneratorParameters> {

    void setInterval(LocalDate from, LocalDate to);

    T getParameters();

    Optional<Node> getNode();
}
