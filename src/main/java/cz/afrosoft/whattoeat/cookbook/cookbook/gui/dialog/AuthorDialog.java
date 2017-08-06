package cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorUpdateObject;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * Dialog for adding new authors and editing existing ones. This allows to edit everything except
 * assigned cookbooks. These are set in cookbook page. It is also used for viewing of authors because
 * author description may be too long to display in author table.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthorDialog extends CustomDialog<AuthorUpdateObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDialog.class);
    private static final String DIALOG_FXML = "/fxml/AuthorDialog.fxml";

    /**
     * Title message displayed when dialog is in view mode.
     */
    private static final String VIEW_TITLE_KEY = "cz.afrosoft.whattoeat.authors.view.title";
    /**
     * Title message displayed when dialog is in add mode.
     */
    private static final String ADD_TITLE_KEY = "cz.afrosoft.whattoeat.authors.add.title";
    /**
     * Title message displayed when dialog is in edit mode.
     */
    private static final String EDIT_TITLE_KEY = "cz.afrosoft.whattoeat.authors.edit.title";

    @FXML
    private GridPane dialogContainer;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea descriptionArea;

    @Autowired
    private AuthorService authorService;

    /**
     * Holds createOrUpdate object when creating or editing author.
     */
    private AuthorUpdateObject authorUpdateObject;

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public AuthorDialog() {
        super(DIALOG_FXML);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
    }

    /**
     * Shows data about author in dialog. Data are read only. This is blocking call. It waits until user close dialog.
     *
     * @param author (NotNull) Author to view.
     */
    public void viewAuthor(final Author author) {
        LOGGER.debug("Viewing author {}.", author);
        Validate.notNull(author);
        setTitle(I18n.getText(VIEW_TITLE_KEY));
        prefillDialog(author);
        setEditable(false);
        showAndWait();
    }

    /**
     * Shows dialog for adding author. This is blocking call. It waits until user close dialog.
     *
     * @return (NotNull) Empty optional if user cancels dialog. Optional with author createOrUpdate object if user submit dialog.
     */
    public Optional<AuthorUpdateObject> addAuthor() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        setEditable(true);
        authorUpdateObject = authorService.getCreateObject();
        return showAndWait();
    }

    /**
     * Shows dialog for editing author. This is blocking call. It waits until user close dialog.
     *
     * @param author (NotNull) Author to edit.
     * @return (NotNull) Empty optional if user cancels dialog. Optional with author createOrUpdate object if user submit dialog.
     */
    public Optional<AuthorUpdateObject> editAuthor(final AuthorUpdateObject author) {
        Validate.notNull(author, "Cannot edit null author.");
        setTitle(I18n.getText(EDIT_TITLE_KEY));
        prefillDialog(author);
        setEditable(true);
        authorUpdateObject = author;
        return showAndWait();
    }

    /**
     * Fills dialog fields with data from author.
     *
     * @param author (NotNull)
     */
    private void prefillDialog(final Author author) {
        Validate.notNull(author);
        nameField.setText(author.getName());
        emailField.setText(author.getEmail());
        descriptionArea.setText(author.getDescription());
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        nameField.setText(StringUtils.EMPTY);
        emailField.setText(StringUtils.EMPTY);
        descriptionArea.setText(StringUtils.EMPTY);
    }

    /**
     * Sets editability of dialog fields. Also replaces dialog buttons. In read only mode dialog has only close button.
     * In editable mode it has finish and cancel button.
     *
     * @param editable True if fields should be editable. False for read only.
     */
    private void setEditable(final boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        descriptionArea.setEditable(editable);
        getDialogPane().getButtonTypes().clear();
        if (editable) {
            getDialogPane().getButtonTypes().add(ButtonType.FINISH);
            getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        } else {
            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        }
    }

    /**
     * Creates and sets result converter which handles dialog button events.
     */
    private void setupResultConverter() {
        this.setResultConverter((buttonType) -> {
            if (ButtonType.FINISH.equals(buttonType)) {
                return fillUpdateObject();
            } else {
                return null;
            }
        });
    }

    /**
     * Fills data from fields to createOrUpdate object. Precondition of this method is that {@link #authorUpdateObject} is not null.
     *
     * @return (NotNull)
     * @throws IllegalStateException If createOrUpdate object does not exist.
     */
    private AuthorUpdateObject fillUpdateObject() {
        if (authorUpdateObject == null) {
            throw new IllegalStateException("Author createOrUpdate object cannot be null.");
        }

        authorUpdateObject.setName(nameField.getText());
        authorUpdateObject.setEmail(emailField.getText());
        authorUpdateObject.setDescription(descriptionArea.getText());

        return authorUpdateObject;
    }
}
