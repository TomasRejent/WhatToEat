package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for deleting content. Styled with trash icon.
 *
 * @author Tomas Rejent
 */
public class DeleteButton extends FxmlButton {

    private static final String FXML_PATH = "/component/DeleteButton.fxml";

    public DeleteButton() {
        super(FXML_PATH);
    }
}
