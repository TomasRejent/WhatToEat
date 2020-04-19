package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.cookbook.recipe.data.RecipeFilter;
import cz.afrosoft.whattoeat.core.gui.component.IconButton;
import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.diet.generator.impl.BasicGeneratorParams;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorParameters;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.generator.service.GeneratorService;
import cz.afrosoft.whattoeat.diet.list.data.entity.DayDietEntity;
import cz.afrosoft.whattoeat.diet.list.gui.table.DateCell;
import cz.afrosoft.whattoeat.diet.list.logic.model.MealTime;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;

import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DayDietDialog;
import cz.afrosoft.whattoeat.diet.list.gui.table.MealsCell;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietViewController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DietViewController.class);

    @FXML
    private TableView<DayDiet> dayDietTable;
    @FXML
    private TableColumn<DayDiet, LocalDate> dayColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> breakfastColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> snackColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> lunchColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> afternoonSnackColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> dinnerColumn;
    @FXML
    private TableColumn<DayDiet, List<Meal>> otherColumn;
    @FXML
    private IconButton regenerateButton;
    @FXML
    private IconButton planNutritionFacts;

    @Autowired
    private DietService dietService;

    @Autowired
    private DayDietService dayDietService;

    @Autowired
    private DayDietDialog dayDietDialog;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private MenuController menuController;

    /**
     * Map which holds appropriate setter method of {@link DayDietUpdateObject} for meal columns, so when meals are edited setter can be easily retrieved for edited
     * column.
     */
    private Map<TableColumn<DayDiet, List<Meal>>, BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject>> columnEditSetterMap = new HashMap<>();

    /**
     * Currently displayed diet.
     */
    private Diet diet;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Switching to day diet page.");
        setupTableColumns();
        initListeners();
    }

    private void initListeners(){
        dayDietTable.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1){
                editMeals();
            }
        });
    }

    private void setupTableColumns() {
        dayColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getDay));
        breakfastColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getBreakfasts));
        snackColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getSnacks));
        lunchColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getLunch));
        afternoonSnackColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getAfternoonSnacks));
        dinnerColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getDinners));
        otherColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(DayDiet::getOthers));
        dayDietTable.getSelectionModel().setCellSelectionEnabled(true);

        dayColumn.setCellFactory(param -> new DateCell());
        breakfastColumn.setCellFactory(param -> new MealsCell(applicationContext));
        snackColumn.setCellFactory(param -> new MealsCell(applicationContext));
        lunchColumn.setCellFactory(param -> new MealsCell(applicationContext));
        afternoonSnackColumn.setCellFactory(param -> new MealsCell(applicationContext));
        dinnerColumn.setCellFactory(param -> new MealsCell(applicationContext));
        otherColumn.setCellFactory(param -> new MealsCell(applicationContext));

        // each meal column has associated setter method which is used for edit
        columnEditSetterMap.put(breakfastColumn, DayDietUpdateObject::setBreakfasts);
        columnEditSetterMap.put(snackColumn, DayDietUpdateObject::setSnacks);
        columnEditSetterMap.put(lunchColumn, DayDietUpdateObject::setLunch);
        columnEditSetterMap.put(afternoonSnackColumn, DayDietUpdateObject::setAfternoonSnacks);
        columnEditSetterMap.put(dinnerColumn, DayDietUpdateObject::setDinners);
        columnEditSetterMap.put(otherColumn, DayDietUpdateObject::setOthers);
    }

    public void showDiet(final Diet diet) {
        Validate.notNull(diet);

        this.diet = diet;
        dayDietTable.getItems().clear();
        dayDietTable.getItems().addAll(ConverterUtil.convertToList(diet.getDayDiets(), dayDietService::loadDayDiet));

        regenerateButton.setDisable(diet.getGeneratorType() != GeneratorType.RANDOM);
    }

    private Optional<EditSelection> getSelectedMeals() {
        Iterator<TablePosition> iterator = dayDietTable.getSelectionModel().getSelectedCells().iterator();
        if (iterator.hasNext()) {
            TablePosition firstSelected = iterator.next();
            if (firstSelected.getColumn() > 0) {
                TableColumn<DayDiet, List<Meal>> tableColumn = firstSelected.getTableColumn();
                List<Meal> cellData = tableColumn.getCellData(firstSelected.getRow());
                if (cellData != null) {
                    return Optional.of(new EditSelection(firstSelected.getRow(), dayDietTable.getSelectionModel().getSelectedItem(), cellData, columnEditSetterMap.get
                        (tableColumn)));
                }
            }
        }

        return Optional.empty();
    }

    /* Button actions */

    @FXML
    public void viewRecipes() {
        LOGGER.info("View recipes action triggered.");
    }

    @FXML
    public void editMeals() {
        LOGGER.info("Edit meals action triggerd.");

        getSelectedMeals().ifPresent(editSelection -> {
            dayDietDialog.editMeals(editSelection.getSelectedMeals()).ifPresent(updatedMeals -> {
                final DayDietUpdateObject updateObject = dayDietService.getUpdateObject(editSelection.getDayDiet());
                editSelection.getUpdateSetter().apply(updateObject, updatedMeals);
                final DayDiet updatedDiet = dayDietService.update(updateObject);
                Platform.runLater(() -> dayDietTable.getItems().set(editSelection.getRowIndex(), updatedDiet));
            });
        });
    }

    @FXML
    public void regenerate(){
        LOGGER.info("Regenerate diet action triggered.");

        if(diet == null){
            return;
        }

        GeneratorParameters params = new BasicGeneratorParams(diet.getFrom(), diet.getTo(), new RecipeFilter.Builder().build(), Set.of(MealTime.LUNCH), diet.getUser());
        List<DayDietEntity> newDayDiets = generatorService.generate(diet.getGeneratorType(), params);
        dietService.replaceDayDiets(diet, newDayDiets);
        showDiet(dietService.getById(diet.getId()));
    }

    private Optional<DayDiet> getSelectedDayDiet(){
        return Optional.ofNullable(dayDietTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void planNutritionFacts(){
        getSelectedDayDiet().ifPresent(dayDiet -> menuController.showNutritionFactsPlan(diet, dayDiet));
    }

    private static class EditSelection {

        private int rowIndex;
        private DayDiet dayDiet;
        private List<Meal> selectedMeals;
        private BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject> updateSetter;

        EditSelection(final int rowIndex, final DayDiet dayDiet, final List<Meal> selectedMeals, final BiFunction<DayDietUpdateObject, List<MealUpdateObject>,
            DayDietUpdateObject> updateSetter) {
            Validate.notNull(dayDiet);
            Validate.notNull(selectedMeals);
            Validate.notNull(updateSetter);

            this.rowIndex = rowIndex;
            this.dayDiet = dayDiet;
            this.selectedMeals = selectedMeals;
            this.updateSetter = updateSetter;
        }

        int getRowIndex() {
            return rowIndex;
        }

        DayDiet getDayDiet() {
            return dayDiet;
        }

        List<Meal> getSelectedMeals() {
            return selectedMeals;
        }

        BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject> getUpdateSetter() {
            return updateSetter;
        }

    }
}
