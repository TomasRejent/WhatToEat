package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for adding content. Styled with plus icon.
 *
 * @author Tomas Rejent
 */
public class AddButton extends FxmlButton {

    private static final String FXML_PATH = "/component/AddButton.fxml";

    public AddButton() {
        super(FXML_PATH);
    }
}
