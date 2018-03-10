package cz.afrosoft.whattoeat.diet.list.gui.controller;

import cz.afrosoft.whattoeat.core.gui.controller.MenuController;
import cz.afrosoft.whattoeat.core.gui.table.CellValueFactory;
import cz.afrosoft.whattoeat.core.gui.table.LabeledCell;
import cz.afrosoft.whattoeat.diet.generator.model.GeneratorType;
import cz.afrosoft.whattoeat.diet.list.logic.model.Diet;
import cz.afrosoft.whattoeat.diet.list.logic.service.DietService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @Autowired
    private MenuController menuController;
    @Autowired
    private DietService dietService;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        LOGGER.info("Initializing diet controller");
        setupColumns();

        dietTable.getItems().addAll(dietService.getAllDiets());
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

    }
}
