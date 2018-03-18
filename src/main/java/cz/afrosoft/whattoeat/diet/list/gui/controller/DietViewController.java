package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.util.ConverterUtil;
import cz.afrosoft.whattoeat.diet.list.gui.dialog.DayDietDialog;
import cz.afrosoft.whattoeat.diet.list.logic.model.DayDiet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.model.Meal;
import cz.afrosoft.whattoeat.diet.list.logic.service.DayDietService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
    }

    public void showDiet(final Diet diet) {
        Validate.notNull(diet);

        dayDietTable.getItems().clear();
        dayDietTable.getItems().addAll(ConverterUtil.convertToList(diet.getDayDiets(), dayDietService::loadDayDiet));
    }

    private Optional<List<Meal>> getSelectedMeals() {
        Iterator<TablePosition> iterator = dayDietTable.getSelectionModel().getSelectedCells().iterator();
        if (iterator.hasNext()) {
            TablePosition firstSelected = iterator.next();
            if (firstSelected.getColumn() > 0) {
                TableColumn<DayDiet, List<Meal>> tableColumn = firstSelected.getTableColumn();
                List<Meal> cellData = tableColumn.getCellData(firstSelected.getRow());
                return Optional.ofNullable(cellData);
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

        getSelectedMeals().ifPresent(meals -> {
            dayDietDialog.editMeals(meals);//TODO add saving
        });
    }
}
