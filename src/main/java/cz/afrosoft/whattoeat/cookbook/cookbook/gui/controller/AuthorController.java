package cz.afrosoft.whattoeat.cookbook.cookbook.gui.controller;

import org.controlsfx.control.MasterDetailPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog.AuthorDialog;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.CookbookRef;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.AuthorService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.Page;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.CollectionCell;
import cz.afrosoft.whattoeat.core.gui.table.DetailBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Controller for author page {@link Page#AUTHORS}.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthorController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    /**
     * Title for author delete confirmation dialog.
     */
    private static final String DELETE_TITLE = "cz.afrosoft.whattoeat.authors.delete.title";
    /**
     * Message for author delete confirmation dialog.
     */
    private static final String DELETE_CONFIRM = "cz.afrosoft.whattoeat.authors.delete.confirm";

    @FXML
    private BorderPane authorContainer;
    @FXML
    private MasterDetailPane detailPane;
    @FXML
    private TextArea detailArea;
    @FXML
    private TableView<Author> authorTable;
    @FXML
    private TableColumn<Author, String> nameColumn;
    @FXML
    private TableColumn<Author, String> emailColumn;
    @FXML
    private TableColumn<Author, Collection<CookbookRef>> cookbookColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorDialog authorDialog;

    /**
     * Setup column factories and loads data into table. Setup detail panel.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.debug("Initializing authors controller.");
        setupColumnCellFactories();
        disableAuthorActionButtons(true);
        setupSelectionHandler();
        authorTable.getItems().addAll(authorService.getAllAuthors());
        DetailBinding.bindDetail(detailPane, authorTable, detailArea, Author::getDescription);
    }

    /**
     * Setup cell value factories for all columns.
     */
    private void setupColumnCellFactories() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Author::getName));
        emailColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Author::getEmail));
        cookbookColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Author::getCookbooks, Collections.emptySet()));
        cookbookColumn.setCellFactory(column -> CollectionCell.newInstance(CookbookRef::getName));
    }

    /**
     * Sets state of author action buttons. These buttons require author to be selected.
     *
     * @param disabled True to disable buttons, false to enable.
     */
    private void disableAuthorActionButtons(final boolean disabled) {
        editButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    /**
     * Setup listener for table selection. Enables or disables buttons which require author to be selected.
     */
    private void setupSelectionHandler() {
        authorTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> disableAuthorActionButtons(newValue == null)
        );
    }

    /**
     * Gets last selected author.
     *
     * @return (NotNull) Empty optional if no author  is selected. Otherwise optional with selected author.
     */
    private Optional<Author> getSelectedAuthor() {
        return Optional.ofNullable(authorTable.getSelectionModel().getSelectedItem());
    }

    /* Button actions. */

    /**
     * Handler for add button. Brings up dialog for adding author. If author is added then table is updated.
     *
     * @param actionEvent (Nullable)
     */
    @FXML
    private void addAuthor(final ActionEvent actionEvent) {
        LOGGER.debug("Add author action triggered.");
        authorDialog.addAuthor().ifPresent(
                authorUpdateObject -> authorTable.getItems().add(authorService.createOrUpdate(authorUpdateObject)));
    }

    /**
     * Handler for edit button. Brings up edit dialog. If changes are saved then table is updated.
     *
     * @param actionEvent (Nullable)
     */
    @FXML
    private void editAuthor(final ActionEvent actionEvent) {
        LOGGER.debug("Edit author action triggered.");
        getSelectedAuthor().ifPresent(((author) -> //author is selected
                authorDialog.editAuthor(author).ifPresent( //edit is confirmed
                        (authorUpdateObject) -> Collections.replaceAll(authorTable.getItems(), author, authorService.createOrUpdate(authorUpdateObject)) //table is updated
                )
        ));
    }

    /**
     * Handler for delete button. Brings up confirmation dialog. If delete is confirmed then table is updated.
     *
     * @param actionEvent (Nullable)
     */
    @FXML
    private void deleteAuthor(final ActionEvent actionEvent) {
        LOGGER.debug("Delete author action triggered.");
        getSelectedAuthor().ifPresent(author -> {
            if (DialogUtils.showConfirmDialog(
                    I18n.getText(DELETE_TITLE), I18n.getText(DELETE_CONFIRM, author.getName()))
                    ) {
                authorService.delete(author);
                authorTable.getItems().remove(author);
            }
        });
    }
}
