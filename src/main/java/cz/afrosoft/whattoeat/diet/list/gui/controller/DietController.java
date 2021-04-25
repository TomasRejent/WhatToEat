package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.cookbook.recipe.logic.model.RecipeType;
import cz.afrosoft.whattoeat.cookbook.user.lodic.model.User;
import cz.afrosoft.whattoeat.cookbook.user.lodic.service.UserService;
import cz.afrosoft.whattoeat.core.gui.I18n;
import cz.afrosoft.whattoeat.core.gui.component.DeleteButton;
import cz.afrosoft.whattoeat.core.gui.component.IconButton;
import cz.afrosoft.whattoeat.core.gui.component.ViewButton;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.core.gui.dialog.util.DialogUtils;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.impl.nutrition.NutritionCriteriaType;
import cz.afrosoft.whattoeat.diet.generator.impl.nutrition.NutritionGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DietCopyDialog;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietCreateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.shopping.gui.dialog.ShoppingListDialog;
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
import java.time.temporal.WeekFields;
import java.util.*;
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

    private static final int DIET_VIEW_LIMIT = 10;

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
    @Autowired
    private UserService userService;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Initializing diet controller");
        setupColumns();
        setupRowListeners();
        dietTable.getItems().addAll(dietService.getAllDiets().stream().sorted(Comparator.comparing(Diet::getFrom).reversed()).limit(DIET_VIEW_LIMIT).collect(Collectors.toList())); // TODO optimize to do limit in DB
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
    public void quickCreate(){
        DietCreateObject dietCreateObject = dietService.getCreateObject();

        // this is only for specific usage, it may be generalized in future by using settings for quick button
        Optional<User> user = userService.getAllUsers().stream().filter((user1 -> "Alex".equals(user1.getName()))).findFirst();
        user.ifPresent(alex -> {
            Optional<Diet> lastDiet = dietService.getAllDiets()
                    .stream()
                    .filter(diet -> diet.getUser().getId().equals(alex.getId()))
                    .max(Comparator.comparing(Diet::getTo));
            lastDiet.ifPresent(diet -> {
                LocalDate from = diet.getTo().plusDays(1);
                LocalDate to = from.plusDays(6);

                dietCreateObject
                        .setName(from.get(WeekFields.of(Locale.getDefault()).weekOfYear()) + ". týden " + alex.getName())
                        .setFrom(from)
                        .setTo(to)
                        .setUser(alex)
                        .setGeneratorParams(
                            new NutritionGeneratorParams(from, to, alex, null, Set.of(MealTime.values()),
                                NutritionCriteriaType.ENERGY.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.FAT.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.SATURATED_FAT.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.CARBOHYDRATE.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.SUGAR.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.PROTEIN.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.SALT.toDefaultNutritionCriteria(),
                                NutritionCriteriaType.FIBER.toDefaultNutritionCriteria(),
                                new RecipeFilter.Builder().setType(Set.of(RecipeType.BREAKFAST)).build(),
                                new RecipeFilter.Builder().setType(Set.of(RecipeType.SNACK)).build(),
                                new RecipeFilter.Builder().setType(Set.of(RecipeType.MAIN_DISH)).build(),
                                new RecipeFilter.Builder().setType(Set.of(RecipeType.SNACK)).build(),
                                new RecipeFilter.Builder().setType(Set.of(RecipeType.DINNER)).build()
                            ))
                        .setGenerator(GeneratorType.NUTRITION_SALVATION);

                dietService.create(dietCreateObject);
                dietTable.getItems().clear();
                dietTable.getItems().addAll(dietService.getAllDiets().stream().sorted(Comparator.comparing(Diet::getFrom).reversed()).limit(DIET_VIEW_LIMIT).collect(Collectors.toList())); // TODO optimize to do limit in DB
            });
        });
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
