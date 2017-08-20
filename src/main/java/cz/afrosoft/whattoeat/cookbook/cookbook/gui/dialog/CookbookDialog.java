package cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookUpdateObject;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.CustomDialog;
import cz.afrosoft.whattoeat.core.gui.list.ListBinding;
import cz.afrosoft.whattoeat.core.gui.suggestion.ComboBoxSuggestion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Optional;

/**
 * Dialog for adding and editing of cookbooks. This dialog also allows to change relation between cookbook and authors.
 * <p>
 * This dialog must be modal because controller using it has only one instance of this dialog.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CookbookDialog extends CustomDialog<CookbookUpdateObject> {

    private static final String DIALOG_FXML = "/fxml/CookbookDialog.fxml";

    /**
     * Title message displayed when dialog is in add mode.
     */
    private static final String ADD_TITLE_KEY = "cz.afrosoft.whattoeat.cookbook.add.title";
    /**
     * Title message displayed when dialog is in edit mode.
     */
    private static final String EDIT_TITLE_KEY = "cz.afrosoft.whattoeat.cookbook.edit.title";

    @FXML
    private GridPane dialogContainer;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ComboBox<Author> authorField;
    @FXML
    private ListView<Author> authorList;

    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private AuthorService authorService;

    /**
     * Holds createOrUpdate object when creating or editing cookbook.
     */
    private CookbookUpdateObject cookbookUpdateObject;

    /**
     * Creates new dialog. This constructor must be used only by Spring, because dependencies must be autowired to created instance.
     */
    public CookbookDialog() {
        super(DIALOG_FXML);
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupAuthorFields();
        setupResultConverter();
    }

    private void setupAuthorFields() {
        ComboBoxSuggestion.initSuggestion(authorField, Author::getName);
        ListBinding.bindToComboBox(authorList, authorField, Author::getName);
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
     * Fills data from fields to createOrUpdate object. Precondition of this method is that {@link #cookbookUpdateObject} is not null.
     *
     * @return (NotNull)
     * @throws IllegalStateException If createOrUpdate object does not exist.
     */
    private CookbookUpdateObject fillUpdateObject() {
        if (cookbookUpdateObject == null) {
            throw new IllegalStateException("Cookbook createOrUpdate object cannot be null.");
        }

        cookbookUpdateObject.setName(nameField.getText());
        cookbookUpdateObject.setDescription(descriptionArea.getText());
        cookbookUpdateObject.setAuthors(new HashSet<>(authorList.getItems()));

        return cookbookUpdateObject;
    }

    /**
     * Shows dialog for adding cookbook. This is blocking call. It waits until user close dialog.
     *
     * @return (NotNull) Empty optional if user cancels dialog. Optional with cookbook createOrUpdate object if user submit dialog.
     */
    public Optional<CookbookUpdateObject> addCookbook() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        cookbookUpdateObject = cookbookService.getCreateObject();
        return showAndWait();
    }

    /**
     * Shows dialog for editing cookbook. This is blocking call. It waits until user close dialog.
     *
     * @param cookbook (NotNull) Cookbook to edit.
     * @return (NotNull) Empty optional if user cancels dialog. Optional with cookbook createOrUpdate object if user submit dialog.
     */
    public Optional<CookbookUpdateObject> editCookbook(final CookbookUpdateObject cookbook) {
        Validate.notNull(cookbook);
        setTitle(I18n.getText(EDIT_TITLE_KEY));
        prefillDialog(cookbook);
        cookbookUpdateObject = cookbook;
        return showAndWait();
    }

    /**
     * Fills dialog fields with data from cookbook.
     *
     * @param cookbook (NotNull)
     */
    private void prefillDialog(final Cookbook cookbook) {
        Validate.notNull(cookbook);
        nameField.setText(cookbook.getName());
        descriptionArea.setText(cookbook.getDescription());
        ListBinding.fillBoundedList(authorList, authorField, authorService.getAllAuthors(), cookbook.getAuthors());
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        nameField.setText(StringUtils.EMPTY);
        descriptionArea.setText(StringUtils.EMPTY);
        authorField.getItems().clear();
        authorField.getItems().addAll(authorService.getAllAuthors());
        authorList.getItems().clear();
    }
}
