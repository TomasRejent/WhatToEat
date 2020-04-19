package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.DeleteButton;
import cz.afrosoft.whattoeat.core.gui.component.IconButton;
import cz.afrosoft.whattoeat.core.gui.component.ViewButton;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DietCopyDialog;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.shopping.gui.dialog.ShoppingListDialog;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;
import cz.afrosoft.whattoeat.diet.shopping.logic.service.ShoppingListService;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.MasterDetailPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietController.class);

    private static final String DELETE_CONFIRM_HEADER = "cz.afrosoft.whattoeat.common.confirm.delete";
    private static final String DELETE_CONFIRM_TEXT = "cz.afrosoft.whattoeat.dietview.delete.confirm";

    @FXML
    private TableView<Diet> dietTable;
    @FXML
    private TableColumn<Diet, String> nameColumn;
    @FXML
    private TableColumn<Diet, LocalDate> fromColumn;
    @FXML
    private TableColumn<Diet, LocalDate> toColumn;
    @FXML
    private TableColumn<Diet, String> userColumn;
    @FXML
    private TableColumn<Diet, GeneratorType> generatorColumn;
    @FXML
    private MasterDetailPane detailPane;
    @FXML
    private TextArea detailArea;
    @FXML
    private ViewButton viewButton;
    @FXML
    private DeleteButton deleteButton;
    @FXML
    private IconButton copyButton;
    @FXML
    private IconButton shoppingListButton;

    @Autowired
    private ShoppingListDialog shoppingListDialog;
    @Autowired
    private MenuController menuController;
    @Autowired
    private DietService dietService;
    @Autowired
    private DietCopyDialog dietCopyDialog;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Initializing diet controller");
        setupColumns();
        setupRowListeners();

        dietTable.getItems().addAll(dietService.getAllDiets());
        viewButton.setDisable(true);
        deleteButton.setDisable(true);
        copyButton.setDisable(true);
        shoppingListButton.setDisable(true);
        getSelectedDiets().ifPresent(observableDietList -> observableDietList.addListener(new ListChangeListener<Diet>() {
            @Override
            public void onChanged(final Change<? extends Diet> change) {
                int selectionSize = change.getList().size();
                if(selectionSize == 0){
                    detailArea.setText("");
                    viewButton.setDisable(true);
                    deleteButton.setDisable(true);
                    copyButton.setDisable(true);
                    shoppingListButton.setDisable(true);
                } else if (selectionSize == 1){
                    getSelectedDiet().ifPresent(diet -> {
                        detailArea.setText(diet.getDescription().orElse(""));
                    });
                    viewButton.setDisable(false);
                    deleteButton.setDisable(false);
                    copyButton.setDisable(false);
                    shoppingListButton.setDisable(false);
                } else {
                    detailArea.setText("");
                    viewButton.setDisable(true);
                    deleteButton.setDisable(true);
                    copyButton.setDisable(true);
                    shoppingListButton.setDisable(false);
                }
            }
        }));
        dietTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setupRowListeners() {
        dietTable.setRowFactory(param -> {
            TableRow<Diet> row = new TableRow<>();
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    menuController.showDiet(row.getItem());
                }
            });
            return row;
        });
    }

    private void setupColumns() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Diet::getName));
        fromColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getFrom, null));
        toColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getTo, null));
        userColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper((diet -> diet.getUser() != null ? diet.getUser().getName() : " - ")));
        generatorColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getGeneratorType, null));
        generatorColumn.setCellFactory(column -> new LabeledCell<>());
        dietTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private Optional<Diet> getSelectedDiet() {
        return Optional.ofNullable(dietTable.getSelectionModel().getSelectedItem());
    }

    private Optional<ObservableList<Diet>> getSelectedDiets() {
        return Optional.ofNullable(dietTable.getSelectionModel().getSelectedItems());
    }

    /* Button actions. */

    @FXML
    public void viewDiet() {
        LOGGER.debug("View diet action triggered.");
        getSelectedDiet().ifPresent(diet -> {
            menuController.showDiet(diet);
        });
    }

    @FXML
    public void addDiet() {
        LOGGER.debug("Add diet action triggered.");
        menuController.showDietGenerator();
    }

    @FXML
    public void deleteDiet() {
        LOGGER.debug("Delete diet action triggered.");
        getSelectedDiet().ifPresent(diet -> {
            if(DialogUtils.showConfirmDialog(I18n.getText(DELETE_CONFIRM_HEADER), I18n.getText(DELETE_CONFIRM_TEXT, diet.getName()))){
                dietService.delete(diet);
                dietTable.getItems().remove(diet);
            }
        });
    }

    @FXML
    public void showCopyDialog() {
        getSelectedDiet().ifPresent((diet) -> {
            dietCopyDialog.getDietCopyParams(diet).ifPresent((dietCopyParams -> {
                dietService.copy(diet, dietCopyParams);
                dietTable.getItems().setAll(dietService.getAllDiets());
            }));
        });
    }

    @FXML
    public void exportShoppingList() {
        LOGGER.debug("Export shopping list action triggered.");
        getSelectedDiets().ifPresent(diets -> shoppingListDialog.showShoppingList(diets));
    }
}
