package cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog;

import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CookbookDialog extends CustomDialog<CookbookUpdateObject> implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookDialog.class);
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

    private CookbookUpdateObject cookbookUpdateObject;

    public CookbookDialog() {
        super(DIALOG_FXML);
        getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        setResizable(true);
        initModality(Modality.APPLICATION_MODAL);
        setupResultConverter();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setupAuthorFields();
    }

    public Optional<CookbookUpdateObject> addCookbook() {
        setTitle(I18n.getText(ADD_TITLE_KEY));
        clearDialog();
        cookbookUpdateObject = cookbookService.getCreateObject();
        return showAndWait();
    }

    /**
     * Clears all dialog fields.
     */
    private void clearDialog() {
        nameField.setText(StringUtils.EMPTY);
        descriptionArea.setText(StringUtils.EMPTY);
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

    private void setupAuthorFields() {
        authorField.getItems().addAll(authorService.getAllAuthors());
        ComboBoxSuggestion.initSuggestion(authorField, Author::getName);
        ListBinding.bindToComboBox(authorList, authorField, Author::getName);
    }

    private CookbookUpdateObject fillUpdateObject() {
        if (cookbookUpdateObject == null) {
            throw new IllegalStateException("Cookbook createOrUpdate object cannot be null.");
        }

        cookbookUpdateObject.setName(nameField.getText());
        cookbookUpdateObject.setDescription(descriptionArea.getText());

        return cookbookUpdateObject;
    }
}
