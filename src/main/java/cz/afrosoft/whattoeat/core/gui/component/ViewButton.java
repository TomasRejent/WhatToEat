package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for viewing content. Styled with eye icon.
 *
 * @author Tomas Rejent
 */
public class ViewButton extends FxmlButton {

    private static final String FXML_PATH = "/component/ViewButton.fxml";

    public ViewButton() {
        super(FXML_PATH);
    }
}
