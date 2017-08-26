package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for removing content. Styled with cross icon.
 *
 * @author Tomas Rejent
 */
public class RemoveButton extends FxmlButton {

    private static final String FXML_PATH = "/component/RemoveButton.fxml";

    public RemoveButton() {
        super(FXML_PATH);
    }
}
