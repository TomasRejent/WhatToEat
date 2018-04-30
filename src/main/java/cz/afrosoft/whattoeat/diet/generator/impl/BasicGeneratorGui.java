package cz.afrosoft.whattoeat.diet.generator.impl;

import java.time.LocalDate;
import java.util.Optional;

import cz.afrosoft.whattoeat.diet.generator.model.GeneratorGui;
import javafx.scene.Node;

/**
 * @author Tomas Rejent
 */
public class BasicGeneratorGui implements GeneratorGui<BasicGeneratorParams> {

    private LocalDate from;
    private LocalDate to;

    @Override
    public void setInterval(final LocalDate from, final LocalDate to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public BasicGeneratorParams getParameters() {
        return new BasicGeneratorParams(from, to);
    }

    @Override
    public Optional<Node> getNode() {
        return Optional.empty();
    }
}
