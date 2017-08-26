package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for editing content. Styled with edit icon.
 *
 * @author Tomas Rejent
 */
public class EditButton extends FxmlButton {

    private static final String FXML_PATH = "/component/EditButton.fxml";

    public EditButton() {
        super(FXML_PATH);
    }
}
