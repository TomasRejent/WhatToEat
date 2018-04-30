package cz.afrosoft.whattoeat.diet.generator.impl.none;

import java.util.Optional;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorGui;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class NoneGeneratorGui extends BasicGeneratorGui {

    private static final String DESCRIPTION_KEY = "cz.afrosoft.whattoeat.diet.generator.none.description";

    @Override
    public Optional<Node> getNode() {
        Label description = new Label();
        description.setText(I18n.getText(DESCRIPTION_KEY));
        return Optional.of(description);
    }
}
