package cz.afrosoft.whattoeat.cookbook.cookbook.gui.controller;

import cz.afrosoft.whattoeat.cookbook.cookbook.gui.dialog.CookbookDialog;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Author;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.model.Cookbook;
import cz.afrosoft.whattoeat.cookbook.cookbook.logic.service.CookbookService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.CollectionCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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

/**
 * Controller for page {@link cz.afrosoft.whattoeat.core.gui.Page#COOKBOOKS}.
 *
 * @author Tomas Rejent
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CookbookController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookbookController.class);

    /**
     * Title for cookbook delete confirmation dialog.
     */
    private static final String DELETE_TITLE = "cz.afrosoft.whattoeat.cookbook.delete.title";
    /**
     * Message for cookbook delete confirmation dialog.
     */
    private static final String DELETE_CONFIRM = "cz.afrosoft.whattoeat.cookbook.delete.confirm";

    @FXML
    private BorderPane cookbookContainer;
    @FXML
    private TableView<Cookbook> cookbookTable;
    @FXML
    private TableColumn<Cookbook, String> nameColumn;
    @FXML
    private TableColumn<Cookbook, Collection<? extends Author>> authorColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private CookbookDialog cookbookDialog;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.debug("Initializing cookbook controller");
        setupColumnCellFactories();
        disableCookbookActionButtons(true);
        setupSelectionHandler();
        cookbookTable.getItems().addAll(cookbookService.getAllCookbooks());
    }

    private void setupColumnCellFactories() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Cookbook::getName));
        authorColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Cookbook::getAuthors, Collections.emptySet()));
        authorColumn.setCellFactory(column -> CollectionCell.newInstance(Author::getName));
    }

    /**
     * Sets state of cookbook action buttons. These buttons require cookbook to be selected.
     *
     * @param disabled True to disable buttons, false to enable.
     */
    private void disableCookbookActionButtons(final boolean disabled) {
        editButton.setDisable(disabled);
        deleteButton.setDisable(disabled);
    }

    /**
     * Setup listener for table selection. Enables or disables buttons which require author to be selected.
     */
    private void setupSelectionHandler() {
        cookbookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> disableCookbookActionButtons(newValue == null)
        );
    }

    /**
     * Gets last selected author.
     *
     * @return (NotNull) Empty optional if no author  is selected. Otherwise optional with selected author.
     */
    private Optional<Cookbook> getSelectedCookbook() {
        return Optional.ofNullable(cookbookTable.getSelectionModel().getSelectedItem());
    }

    /* Button actions */

    @FXML
    private void addCookbook(final ActionEvent event) {
        LOGGER.debug("Add cookbook action triggered.");
        cookbookDialog.addCookbook().ifPresent(
                cookbookUpdateObject -> cookbookTable.getItems().add(cookbookService.createOrUpdate(cookbookUpdateObject))
        );
    }

    @FXML
    private void editCookbook(final ActionEvent event) {
        LOGGER.debug("Edit cookbook action triggered.");
        getSelectedCookbook().ifPresent(//cookbook is selected
                (cookbook) -> cookbookDialog.editCookbook(cookbookService.getUpdateObject(cookbook)).ifPresent(//edit is confirmed
                        (cookbookUpdateObject) -> Collections.replaceAll(cookbookTable.getItems(), cookbook, cookbookService.createOrUpdate(cookbookUpdateObject)) //table is updated
                )
        );
    }

    @FXML
    private void deleteCookbook(final ActionEvent event) {
        LOGGER.debug("Delete cookbook action triggered.");
        getSelectedCookbook().ifPresent(cookbook -> {
            if (DialogUtils.showConfirmDialog(
                    I18n.getText(DELETE_TITLE), I18n.getText(DELETE_CONFIRM, cookbook.getName()))
                    ) {
                cookbookService.delete(cookbook);
                cookbookTable.getItems().remove(cookbook);
            }
        });
    }
}
