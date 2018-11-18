package cz.afrosoft.whattoeat.diet.generator.impl.random;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorGui;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
public class RandomGeneratorGui extends BasicGeneratorGui {

    private static final String DESCRIPTION_KEY = "cz.afrosoft.whattoeat.diet.generator.random.description";

    @Override
    public Optional<Node> getNode() {
        Label description = new Label();
        description.setText(I18n.getText(DESCRIPTION_KEY));
        return Optional.of(description);
    }
}
