package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.cookbook.ingredient.logic.service.NutritionFactsService;
import cz.afrosoft.whattoeat.core.gui.component.IconButton;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.FloatCell;
import cz.afrosoft.whattoeat.diet.generator.model.MealNutritionFacts;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DayDietDialog;
import cz.afrosoft.whattoeat.diet.list.gui.table.DateCell;
import cz.afrosoft.whattoeat.diet.list.gui.table.MealsCell;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDietRef;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Tomas Rejent
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DietNutritionPlanController implements Initializable {

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
    private TableView<MealNutritionFacts> nutritionTable;
    @FXML
    private TableColumn<MealNutritionFacts, String> mealColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> energyColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> fatColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> saturatedFatColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> carbohydrateColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> sugarColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> proteinColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> saltColumn;
    @FXML
    private TableColumn<MealNutritionFacts, Float> fibreColumn;
    @FXML
    private IconButton previousDayButton;
    @FXML
    private IconButton nextDayButton;

    @Autowired
    private DietService dietService;

    @Autowired
    private DayDietService dayDietService;

    @Autowired
    private DayDietDialog dayDietDialog;

    @Autowired
    private NutritionFactsService nutritionFactsService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Currently displayed diet.
     */
    private Diet diet;
    /**
     * Currently displayed dayDiet.
     */
    private DayDiet dayDiet;

    /**
     * Map which holds appropriate setter method of {@link DayDietUpdateObject} for meal columns, so when meals are edited setter can be easily retrieved for edited
     * column.
     */
    private Map<TableColumn<DayDiet, List<Meal>>, BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject>> columnEditSetterMap = new HashMap<>();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setupTableColumns();
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

        mealColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getMealName));
        energyColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getEnergy));
        fatColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getFat));
        saturatedFatColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getSaturatedFat));
        carbohydrateColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getCarbohydrate));
        sugarColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getSugar));
        proteinColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getProtein));
        saltColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getSalt));
        fibreColumn.setCellValueFactory(CellValueFactory.newReadOnlyWrapper(MealNutritionFacts::getFibre));

        energyColumn.setCellFactory(param -> new FloatCell<>(0));
        fatColumn.setCellFactory(param -> new FloatCell<>(1));
        saturatedFatColumn.setCellFactory(param -> new FloatCell<>(1));
        carbohydrateColumn.setCellFactory(param -> new FloatCell<>(1));
        sugarColumn.setCellFactory(param -> new FloatCell<>(1));
        proteinColumn.setCellFactory(param -> new FloatCell<>(1));
        saltColumn.setCellFactory(param -> new FloatCell<>(6));
        fibreColumn.setCellFactory(param -> new FloatCell<>(3));

    }

    public void planDay(final Diet diet, final DayDiet dayDiet){
        Validate.notNull(diet);
        Validate.notNull(dayDiet);

        this.diet = diet;
        this.dayDiet = dayDiet;

        dayDietTable.getItems().clear();
        dayDietTable.getItems().addAll(dayDiet);
        updateNutritionTable(dayDiet);
        updatePositionButtons(diet, dayDiet);
    }

    private void updatePositionButtons(Diet diet, DayDiet dayDiet){
        int index = indexOfDayDiet(diet, dayDiet);
        if(index == 0){
            previousDayButton.setDisable(true);
        }else{
            previousDayButton.setDisable(false);
        }
        if(index == diet.getDayDiets().size() - 1){
            nextDayButton.setDisable(true);
        }else{
            nextDayButton.setDisable(false);
        }
    }

    private void updateNutritionTable(DayDiet dayDiet){
        nutritionTable.getItems().setAll(nutritionFactsService.getDayDietNutritionFacts(dayDiet));
    }

    private Optional<DietNutritionPlanController.EditSelection> getSelectedMeals() {
        Iterator<TablePosition> iterator = dayDietTable.getSelectionModel().getSelectedCells().iterator();
        if (iterator.hasNext()) {
            TablePosition firstSelected = iterator.next();
            if (firstSelected.getColumn() > 0) {
                TableColumn<DayDiet, List<Meal>> tableColumn = firstSelected.getTableColumn();
                List<Meal> cellData = tableColumn.getCellData(firstSelected.getRow());
                if (cellData != null) {
                    return Optional.of(new DietNutritionPlanController.EditSelection(firstSelected.getRow(), dayDietTable.getSelectionModel().getSelectedItem(), cellData, columnEditSetterMap.get
                            (tableColumn)));
                }
            }
        }

        return Optional.empty();
    }

    @FXML
    public void editMeals() {
        getSelectedMeals().ifPresent(editSelection -> {
            dayDietDialog.editMeals(editSelection.getSelectedMeals()).ifPresent(updatedMeals -> {
                final DayDietUpdateObject updateObject = dayDietService.getUpdateObject(editSelection.getDayDiet());
                editSelection.getUpdateSetter().apply(updateObject, updatedMeals);
                final DayDiet updatedDiet = dayDietService.update(updateObject);
                Platform.runLater(
                        () -> {
                            dayDietTable.getItems().set(editSelection.getRowIndex(), updatedDiet);
                            updateNutritionTable(updatedDiet);
                            this.dayDiet = updatedDiet;
                        }
                );
            });
        });
    }

    @FXML
    public void nextDay(){
        int index = indexOfDayDiet(diet, dayDiet);
        if(index + 1 < diet.getDayDiets().size()){
            planDay(diet, dayDietService.loadDayDiet(diet.getDayDiets().get(index+1)));
        }
    }

    @FXML
    public void previousDay(){
        int index = indexOfDayDiet(diet, dayDiet);
        if(index > 0){
            planDay(diet, dayDietService.loadDayDiet(diet.getDayDiets().get(index-1)));
        }
    }

    private int indexOfDayDiet(Diet diet, DayDiet dayDiet){
        List<DayDietRef> dayDiets = diet.getDayDiets();
        for(int i = 0; i < dayDiets.size() ; i++){
            DayDietRef checkedDayDiet = dayDiets.get(i);
            if(checkedDayDiet.getId().equals(dayDiet.getId())){
                return i;
            }
        }
        return -1;
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
