package cz.afrosoft.whattoeat.diet.list.gui.controller;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DayDietDialog;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietUpdateObject;
import cz.afrosoft.whattoeat.diet.list.logic.service.MealUpdateObject;
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

    @Autowired
    private DayDietService dayDietService;

    @Autowired
    private DayDietDialog dayDietDialog;

    /**
     * Map which holds appropriate setter method of {@link DayDietUpdateObject} for meal columns, so when meals are edited setter can be easily retrieved for edited
     * column.
     */
    private Map<TableColumn<DayDiet, List<Meal>>, BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject>> columnEditSetterMap = new HashMap<>();

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Switching to day diet page.");
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

        dayDietTable.getItems().clear();
        dayDietTable.getItems().addAll(ConverterUtil.convertToList(diet.getDayDiets(), dayDietService::loadDayDiet));
    }

    private Optional<EditSelection> getSelectedMeals() {
        Iterator<TablePosition> iterator = dayDietTable.getSelectionModel().getSelectedCells().iterator();
        if (iterator.hasNext()) {
            TablePosition firstSelected = iterator.next();
            if (firstSelected.getColumn() > 0) {
                TableColumn<DayDiet, List<Meal>> tableColumn = firstSelected.getTableColumn();
                List<Meal> cellData = tableColumn.getCellData(firstSelected.getRow());
                if (cellData != null) {
                    return Optional.of(new EditSelection(dayDietTable.getSelectionModel().getSelectedItem(), cellData, columnEditSetterMap.get(tableColumn)));
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
                dayDietService.update(updateObject);
            });
        });
    }

    private static class EditSelection {

        private DayDiet dayDiet;

        private List<Meal> selectedMeals;

        private BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject> updateSetter;

        public EditSelection(final DayDiet dayDiet, final List<Meal> selectedMeals, final BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject> updateSetter) {
            Validate.notNull(dayDiet);
            Validate.notNull(selectedMeals);
            Validate.notNull(updateSetter);

            this.dayDiet = dayDiet;
            this.selectedMeals = selectedMeals;
            this.updateSetter = updateSetter;
        }

        public DayDiet getDayDiet() {
            return dayDiet;
        }

        public List<Meal> getSelectedMeals() {
            return selectedMeals;
        }

        public BiFunction<DayDietUpdateObject, List<MealUpdateObject>, DayDietUpdateObject> getUpdateSetter() {
            return updateSetter;
        }

    }
}
