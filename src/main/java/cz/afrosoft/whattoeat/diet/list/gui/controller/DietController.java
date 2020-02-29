package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.IconButton;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.DetailBinding;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DietCopyDialog;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.shopping.gui.dialog.ShoppingListDialog;
import cz.afrosoft.whattoeat.diet.shopping.logic.model.ShoppingItems;
import cz.afrosoft.whattoeat.diet.shopping.logic.service.ShoppingListService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.MasterDetailPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private TableColumn<Diet, GeneratorType> generatorColumn;
    @FXML
    private MasterDetailPane detailPane;
    @FXML
    private TextArea detailArea;
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
    private ShoppingListService shoppingListService;
    @Autowired
    private DietCopyDialog dietCopyDialog;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Initializing diet controller");
        setupColumns();

        dietTable.getItems().addAll(dietService.getAllDiets());
        DetailBinding.bindDetail(detailPane, dietTable, detailArea, diet -> diet.getDescription().orElse(StringUtils.EMPTY));
    }

    private void setupColumns() {
        nameColumn.setCellValueFactory(CellValueFactory.newStringReadOnlyWrapper(Diet::getName));
        fromColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getFrom, null));
        toColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getTo, null));
        generatorColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(Diet::getGeneratorType, null));
        generatorColumn.setCellFactory(column -> new LabeledCell<>());
    }

    private Optional<Diet> getSelectedDiet() {
        return Optional.ofNullable(dietTable.getSelectionModel().getSelectedItem());
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
            dietCopyDialog.getDietCopyParams().ifPresent((dietCopyParams -> {
                dietService.copy(diet, dietCopyParams);
                dietTable.getItems().setAll(dietService.getAllDiets());
            }));
        });
    }

    @FXML
    public void exportShoppingList() {
        LOGGER.debug("Export shopping list action triggered.");
        getSelectedDiet().ifPresent(diet -> {
            ShoppingItems shoppingItems = shoppingListService.createShoppingItems(dietService.getDietMeals(diet));
            String shopingListText = shoppingListService.formatToSimpleText(shoppingItems);
            shoppingListDialog.showShoppingList(shopingListText);
        });
    }
}
