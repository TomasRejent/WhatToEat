package cz.afrosoft.whattoeat.core.gui.component;

/**
 * Button for searching. Styled with magnifying glass icon.
 *
 * @author Tomas Rejent
 */
public class SearchButton extends FxmlButton {

    private static final String FXML_PATH = "/component/SearchButton.fxml";

    public SearchButton() {
        super(FXML_PATH);
    }
}
